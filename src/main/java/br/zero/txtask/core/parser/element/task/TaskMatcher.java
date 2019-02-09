package br.zero.txtask.core.parser.element.task;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.core.parser.element.task.Constants.TASK_STATUSES_ARRAY;

public class TaskMatcher implements ElementMatcher {

    @Override
    public boolean matchs(ParserReader reader) throws IOException {
        return reader.followed().byAnyOf(TASK_STATUSES_ARRAY).which() > -1;
    }

}
