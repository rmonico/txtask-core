package br.zero.txtask.parser.scope.linebreak;

import java.io.IOException;

import br.zero.txtask.parser.scope.ElementParser;
import br.zero.txtask.parser.reader.ParserReader;

public class LineBreakParser implements ElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws IOException {
        String line = reader.consume().next(1).go();

        assert "\n".equals(line) : "Trying to parse with BlankLineParser on non-blank line";

        return line;
    }
}