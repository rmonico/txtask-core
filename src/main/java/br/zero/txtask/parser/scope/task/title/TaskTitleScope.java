package br.zero.txtask.parser.scope.task.title;

import br.zero.txtask.parser.scope.SingleScope;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.scope.tag.Constants.TAG_PREFIX;

public class TaskTitleScope extends SingleScope<String> {

    public TaskTitleScope() {
        super(new TaskTitleParser());
    }

    @Override
    protected boolean matches(ParserReader reader) throws IOException {
        return !reader.followed().byAnyOf(TAG_PREFIX, "\n").go();
    }
}
