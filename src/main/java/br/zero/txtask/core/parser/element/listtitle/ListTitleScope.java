package br.zero.txtask.core.parser.element.listtitle;

import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class ListTitleScope extends AbstractScope<String> {

    public ListTitleScope() {
        setMatcher(new ListTitleMatcher());
        setParser(new ListTitleParser());
    }

    @Override
    public Scope<?> findScope(ParserReader reader) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean done() {
        return true;
    }
}
