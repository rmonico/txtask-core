package br.zero.txtask.core.parser.element.garbage;

import java.io.IOException;

import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class GarbageScope extends AbstractScope<String> {

    public GarbageScope() {
        setMatcher(new GarbageMatcher());
        setParser(new GarbageParser());
    }

    @Override
    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        return this;
    }

}
