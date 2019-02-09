package br.zero.txtask.core.parser.element.task;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.core.parser.element.taggroup.Constants.TAG_PREFIX;
import static br.zero.txtask.core.parser.element.task.Constants.TASK_STATUSES;
import static br.zero.txtask.core.parser.element.task.Constants.TASK_STATUSES_ARRAY;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import br.zero.txtask.core.model.Status;
import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.element.taggroup.TagGroupParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TaskParser implements ElementParser<Task> {

    @Override
    public Task parse(ParserReader reader) throws IOException {
        Status status = parseStatus(reader);

        String taskTitle = parseTitle(reader);

        Task task = new Task();

        task.setStatus(status);

        task.setTitle(taskTitle);

        TagGroupParser tagParser = new TagGroupParser(TAG_PREFIX);

        List<Tag> tagList = tagParser.parse(reader);

        task.getTags().addAll(tagList);

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

}
