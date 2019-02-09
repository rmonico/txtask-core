package br.zero.txtask.parser.scope.garbage;

import java.io.IOException;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.scope.ElementParser;
import br.zero.txtask.parser.reader.ParserReader;

import static br.zero.java.StringFormatter.s;

public class GarbageParser implements ElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws ParserException, IOException {
        String token = reader.consume().eol().go();

        throw new ParserException(s("Invalid token: '%s'").format(token));
    }

}
