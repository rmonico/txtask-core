package br.zero.txtask.core.parser.element;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.reader.ParserReader;

public interface ElementMatcher<ELEMENTTYPE> {

    public boolean matchs(ParserReader reader) throws ParserException, IOException;

    public ElementParser<ELEMENTTYPE> getParser();
}
