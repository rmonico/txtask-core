package br.zero.txtask.parser.scope.tasklist.title;

import br.zero.txtask.parser.scope.SingleScope;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

public class ListTitleScope extends SingleScope<String> {

    public ListTitleScope() {
        super(new ListTitleParser());
    }

    protected boolean matches(ParserReader reader) throws IOException {
        return reader.followed().by(Constants.LIST_TITLE_PREFIX).go();
    }

}
