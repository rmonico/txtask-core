package br.zero.txtask.parser.scope.task.status;

import br.zero.txtask.model.Status;
import br.zero.txtask.parser.scope.SingleScope;
import br.zero.txtask.parser.reader.ParserReader;
import br.zero.txtask.parser.scope.task.Constants;

import java.io.IOException;

public class TaskStatusScope extends SingleScope<Status> {

    public TaskStatusScope() {
        super(new TaskStatusParser());
    }

    @Override
    protected boolean matches(ParserReader reader) throws IOException {
        return reader.followed().byAnyOf(Constants.TASK_STATUSES_ARRAY).which() > -1;
    }
}
