package br.zero.txtask.core.parser.element.blankline;

import java.io.IOException;

import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class EmptyLineScope extends AbstractScope<String> {

    public EmptyLineScope() {
        setMatcher(new EmptyLineMatcher());
        setParser(new EmptyLineParser());
    }

    @Override
    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        return this;
    }

    @Override
    public boolean done() {
        return true;
    }
}
