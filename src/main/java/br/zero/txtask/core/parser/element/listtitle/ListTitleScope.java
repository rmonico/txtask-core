package br.zero.txtask.core.parser.element.listtitle;

import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.core.parser.element.listtitle.Constants.LIST_TITLE_PREFIX;

public class ListTitleScope extends AbstractScope<String> {

    public ListTitleScope() {
        setParser(new ListTitleParser());
    }

    private boolean matches(ParserReader reader) throws IOException {
        return reader.followed().by(LIST_TITLE_PREFIX).go();
    }

    @Override
    public Scope<?> findScope(ParserReader reader) throws IOException {
        if (this.matches(reader))
            return this;
        else
            return null;
    }

    @Override
    public boolean done() {
        return true;
    }
}
