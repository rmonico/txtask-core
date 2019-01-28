package br.zero.txtask.core.parser;

import java.io.IOException;
import java.io.Reader;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;

public class TaskListParser implements Parser<TaskList> {

    private static final String LIST_TITLE_PREFIX = ":: ";
    private static final String[] TASK_STATUSES = { "- ", "x " };
    private static final String TAG_PREFIX = " #";

    public TaskList parse(Reader source) throws ParserException {
        ParserReader reader = new ParserReader(source);

        return this.doParse(reader);
    }

    @Override
    public TaskList doParse(ParserReader reader) throws ParserException {
        try {
            return internalParse(reader);
        } catch (IOException e) {
            throw new ParserException(e);
        }
    }

    private TaskList internalParse(ParserReader reader) throws ParserException, IOException {
        TaskList taskList = new TaskList();

        if (reader.finished())
            throw new ParserException("Reader is empty");

        if (reader.followed().by(LIST_TITLE_PREFIX).go()) {
            reader.consume().next(LIST_TITLE_PREFIX.length()).go();
            String title = reader.consume().eol().go();

            taskList.setTitle(title);
        } else if (reader.finished())
            throw new ParserException("Empty list");
        else
            // TODO Test this
            throw new ParserException("List must start with a title");

        while (!reader.finished()) {
            int statusIndex = 0;
            if ((statusIndex = reader.followed().byAnyOf(TASK_STATUSES).which()) > -1) {
                String statusFound = TASK_STATUSES[statusIndex];

                reader.consume().next(statusFound.length()).go();

                Task task = new Task();

                String taskTitle = reader.consume().until(TAG_PREFIX).or().eol().go();

                task.setTitle(taskTitle);

                taskList.getTasks().add(task);

                while (reader.followed().by(TAG_PREFIX).go()) {
                    reader.consume().next(TAG_PREFIX.length()).go();

                    Tag tag = new Tag();

                    String tagName = reader.consume().until(TAG_PREFIX).or().eol().go();

                    tag.setName(tagName);

                    task.getTags().add(tag);
                }
            } else if (reader.followed().byEol().go()) {
                reader.consume().next(1).go();
            }
        }

        return taskList;
    }

}
