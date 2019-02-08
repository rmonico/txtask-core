package br.zero.txtask.core.parser.element.task;

import java.io.IOException;

import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class RootTaskScope extends AbstractScope<Task> {

    public RootTaskScope() {
        setMatcher(new TaskMatcher());
        setParser(new TaskParser());
    }

    @Override
    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        return null;
    }

    @Override
    public boolean done() {
        return true;
    }
}
