package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Status;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.Map;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.parser.ParserException.error;
import static br.zero.txtask.parser.internal.Constants.TASK_STATUSES;
import static br.zero.txtask.parser.internal.Constants.TASK_STATUSES_ARRAY;

class TaskStatusParser {

    private static final TaskStatusParser instance = new TaskStatusParser();

    static TaskStatusParser taskStatusParser() {
        return instance;
    }

    Status parse(ParserReader reader) throws IOException, ParserException {
        if (!this.matches(reader))
            error("Cant parse task status", reader);

        int statusIndex = reader.followed().byAnyOf(TASK_STATUSES_ARRAY).which();

        String statusString = reader.consume().next(TASK_STATUSES_ARRAY[statusIndex].length()).go();

        Status status = getTaskStatusByPrefix(statusString);

        assert status != null : s("Task status not found: '%s'").format(statusString);

        return status;
    }

    boolean matches(ParserReader reader) throws IOException {
        return reader.followed().byAnyOf(TASK_STATUSES_ARRAY).which() > -1;
    }

    private Status getTaskStatusByPrefix(String prefix) {
        for (Map.Entry<Status, String> entry : TASK_STATUSES.entrySet())
            if (entry.getValue().equals(prefix))
                return entry.getKey();

        return null;
    }

}
