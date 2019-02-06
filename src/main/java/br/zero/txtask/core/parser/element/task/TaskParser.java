package br.zero.txtask.core.parser.element.task;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.core.parser.element.abstracttag.Constants.TAG_PREFIX;
import static br.zero.txtask.core.parser.element.task.Constants.TASK_STATUSES;
import static br.zero.txtask.core.parser.element.task.Constants.TASK_STATUSES_ARRAY;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.function.Consumer;

import br.zero.txtask.core.model.Status;
import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.AbstractElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TaskParser extends AbstractElementParser<Task> {

    @Override
    public Task parse(ParserReader reader) throws ParserException, IOException {
        Status status = parseStatus(reader);

        String taskTitle = parseTitle(reader);

        Task task = new Task();

        task.setStatus(status);

        task.setTitle(taskTitle);

        parseTags(reader, tag -> task.getTags().add(tag), TAG_PREFIX);

        return task;
    }

    private Status parseStatus(ParserReader reader) throws IOException {
        int statusIndex = reader.followed().byAnyOf(TASK_STATUSES_ARRAY).which();

        String statusString = reader.consume().next(TASK_STATUSES_ARRAY[statusIndex].length()).go();

        Status status = getTaskStatusByPrefix(statusString);

        assert status != null : s("Task status not found: '%s'").format(statusString);

        return status;
    }

    private Status getTaskStatusByPrefix(String prefix) {
        for (Entry<Status, String> entry : TASK_STATUSES.entrySet())
            if (entry.getValue().equals(prefix))
                return entry.getKey();

        return null;
    }

    private String parseTitle(ParserReader reader) throws IOException {
        String taskTitle = reader.consume().until(TAG_PREFIX).or().eol().go();

        return taskTitle;
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

    @Override
    protected void internalPut(TaskList taskList,
            Task task) {
        taskList.getTasks().add(task);

        task.getTags().addAll(context.implicitTags());
    }

}
