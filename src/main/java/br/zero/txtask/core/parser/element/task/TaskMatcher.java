package br.zero.txtask.core.parser.element.task;

import static br.zero.txtask.core.parser.element.task.Constants.TASK_STATUSES_ARRAY;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TaskMatcher implements ElementMatcher {

    @Override
    public boolean matchs(ParserReader reader) throws ParserException, IOException {
        return reader.followed().byAnyOf(TASK_STATUSES_ARRAY).which() > -1;
    }

}
