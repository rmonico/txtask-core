package br.zero.txtask.core.parser;

import static java.lang.String.format;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;

public class TaskListParser implements Parser<TaskList> {

    private static final String LIST_TITLE_PREFIX = ":: ";
    private static final String[] TASK_STATUSES = { "- ", "x " };
    private static final String TAG_MARK = "#";
    private static final String TAG_PREFIX = format(" %s", TAG_MARK);

    private static final String IMPLICIT_TAG_MARK = format("#%s", TAG_MARK);
    private static final String IMPLICIT_TAG_PREFIX = format(" %s", IMPLICIT_TAG_MARK);
    private static final String IMPLICIT_TAG_INITIAL_PREFIX = format("\n%s", IMPLICIT_TAG_MARK);

    private static final String IMPLICIT_TAG_REMOVAL_MARK = "==";
    private static final String IMPLICIT_TAG_REMOVAL_PREFIX = format(" %s", IMPLICIT_TAG_REMOVAL_MARK);
    private static final String IMPLICIT_TAG_REMOVAL_INITIAL_PREFIX = format("\n%s", IMPLICIT_TAG_REMOVAL_MARK);

    public TaskList parse(Reader source) throws ParserException {
        ParserReader reader = new ParserReader(source);

        return this.doParse(reader);
    }

    @Override
    public TaskList doParse(ParserReader reader) throws ParserException {
        try {
            if (reader.finished())
                throw new ParserException("List is empty");

            return internalParse(reader);
        } catch (IOException e) {
            throw new ParserException(e);
        }
    }

    private TaskList internalParse(ParserReader reader) throws ParserException, IOException {
        TaskList taskList = new TaskList();

        parseTitle(reader, taskList);

        List<Tag> implicitTags = new ArrayList<>();

        while (!reader.finished()) {
            // TODO Refactor
            int statusIndex = 0;
            if ((statusIndex = reader.followed().byAnyOf(TASK_STATUSES).which()) > -1) {
                String statusFound = TASK_STATUSES[statusIndex];

                reader.consume().next(statusFound.length()).go();

                Task task = new Task();

                String taskTitle = reader.consume().until(TAG_PREFIX).or().eol().go();

                task.setTitle(taskTitle);

                taskList.getTasks().add(task);

                parseTags(reader, tag -> task.getTags().add(tag), TAG_PREFIX);

                task.getTags().addAll(implicitTags);
            } else if (reader.followed().by(IMPLICIT_TAG_INITIAL_PREFIX).go()) {
                parseTags(reader, tag -> implicitTags.add(tag), IMPLICIT_TAG_INITIAL_PREFIX, IMPLICIT_TAG_PREFIX);
            } else if (reader.followed().by(IMPLICIT_TAG_REMOVAL_INITIAL_PREFIX).go()) {
                parseTags(reader, tag -> implicitTags.remove(tag), IMPLICIT_TAG_REMOVAL_INITIAL_PREFIX, IMPLICIT_TAG_REMOVAL_PREFIX);
            } else if (reader.followed().byEol().go()) {
                reader.consume().next(1).go();
            } else {
                String nextToken = reader.consume().until(" ").or().eol().go();

                throw new ParserException("Invalid token: '%s'", nextToken);
            }
        }

        return taskList;
    }

    private void parseTitle(ParserReader reader,
            TaskList taskList) throws IOException, ParserException {
        if (reader.followed().by(LIST_TITLE_PREFIX).go()) {
            reader.consume().next(LIST_TITLE_PREFIX.length()).go();
            String title = reader.consume().eol().go();

            taskList.setTitle(title);
        } else
            throw new ParserException("List must start with ':: '");
    }

    private void parseTags(ParserReader reader,
            Consumer<Tag> onTagFound,
            String... prefixes) throws IOException {

        int prefixIndex;
        while ((prefixIndex = reader.followed().byAnyOf(prefixes).which()) > -1) {
            reader.consume().next(prefixes[prefixIndex].length()).go();

            Tag tag = new Tag();

            String tagName = reader.consume().until(prefixes).or().eol().go();

            tag.setName(tagName);

            onTagFound.accept(tag);
        }
    }

}
