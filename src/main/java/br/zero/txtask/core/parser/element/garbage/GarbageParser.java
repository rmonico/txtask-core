package br.zero.txtask.core.parser.element.garbage;

import java.io.IOException;

import br.zero.java.StringFormatter;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class GarbageParser implements ElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws ParserException, IOException {
        String token = reader.consume().eol().go();

        throw new ParserException(StringFormatter.s("Invalid token: '%s'").format(token));
    }

}
