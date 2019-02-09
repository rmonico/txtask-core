package br.zero.txtask.core.parser.element.blankline;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class EmptyLineParser implements ElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws IOException {
        String line = reader.consume().next(1).go();

        assert "\n".equals(line) : "Trying to parse with BlankLineParser on non-blank line";

        return line;
    }
}