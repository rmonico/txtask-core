package br.zero.txtask.core.parser.element.blankline;

import java.io.IOException;
import java.util.function.Consumer;

import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class EmptyLineScope extends AbstractScope<String> {

    public EmptyLineScope(Consumer<String> destination) {
        super(new EmptyLineMatcher(), new EmptyLineParser(), destination);
    }

    @Override
    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
