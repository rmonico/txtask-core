package br.zero.txtask.core.parser.element.listtitle;

import java.io.IOException;
import java.util.function.Consumer;

import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class ListTitleScope extends AbstractScope<String> {

    public ListTitleScope(Consumer<String> destination) {
        super(new ListTitleMatcher(), new ListTitleParser(), destination);
    }

    @Override
    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
