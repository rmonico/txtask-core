package br.zero.txtask.core.parser.element.task;

import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class RootTaskScope extends AbstractScope<Task> {

    public RootTaskScope() {
        setMatcher(new TaskMatcher());
        setParser(new TaskParser());
    }

    @Override
    public Scope<?> findScope(ParserReader reader) {
        return getParentScope();
    }

    @Override
    public boolean done() {
        return true;
    }
}
