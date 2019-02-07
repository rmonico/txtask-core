package br.zero.txtask.core.parser.element.listtitle;

import static br.zero.txtask.core.parser.element.listtitle.Constants.LIST_TITLE_PREFIX;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class ListTitleMatcher implements ElementMatcher<String> {

    private ElementParser<String> parser;

    public ListTitleMatcher() {
        this.parser = new ListTitleParser();
    }

    @Override
    public boolean matchs(ParserReader reader) throws ParserException, IOException {
        return reader.followed().by(LIST_TITLE_PREFIX).go();
    }

    @Override
    public ElementParser<String> getParser() {
        return this.parser;
    }

}
