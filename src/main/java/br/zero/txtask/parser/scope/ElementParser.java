package br.zero.txtask.parser.scope;

import java.io.IOException;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

public interface ElementParser<ELEMENTTYPE> {

    ELEMENTTYPE parse(ParserReader reader) throws ParserException, IOException;

}
