package br.zero.txtask.parser.scope.task.status;

import br.zero.txtask.model.Status;
import br.zero.txtask.parser.scope.ElementParser;
import br.zero.txtask.parser.reader.ParserReader;
import br.zero.txtask.parser.scope.task.Constants;

import java.io.IOException;
import java.util.Map;

import static br.zero.java.StringFormatter.s;

public class TaskStatusParser implements ElementParser<Status> {

    @Override
    public Status parse(ParserReader reader) throws IOException {
        int statusIndex = reader.followed().byAnyOf(Constants.TASK_STATUSES_ARRAY).which();

        String statusString = reader.consume().next(Constants.TASK_STATUSES_ARRAY[statusIndex].length()).go();

        Status status = getTaskStatusByPrefix(statusString);

        assert status != null : s("Task status not found: '%s'").format(statusString);

        return status;
    }

    private Status getTaskStatusByPrefix(String prefix) {
        for (Map.Entry<Status, String> entry : Constants.TASK_STATUSES.entrySet())
            if (entry.getValue().equals(prefix))
                return entry.getKey();

        return null;
    }

}
