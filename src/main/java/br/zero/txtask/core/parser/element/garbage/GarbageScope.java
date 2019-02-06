package br.zero.txtask.core.parser.element.garbage;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class GarbageMatcher implements ElementMatcher<String> {

    private ElementParser<String> parser;

    public GarbageMatcher() {
        parser = new GarbageParser();
    }

    @Override
    public boolean matchs(ParserReader reader,
            ParserContext context) throws ParserException, IOException {
        return true;
    }

    @Override
    public ElementParser<String> getParser() {
        return this.parser;
    }

}
