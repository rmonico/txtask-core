package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.parser.ParserException.error;
import static java.lang.System.lineSeparator;

class ConstantParser {

    private static final ConstantParser instance = new ConstantParser();

    static ConstantParser constantParser() {
        return instance;
    }

    void parseUntilNextNonEmptyLine(ParserReader reader) throws IOException, ParserException {
        String lineSeparator = lineSeparator();

        while (lineSeparator.equals(reader.get().next(lineSeparator.length()).go())) {
            parse(reader, lineSeparator);
        }
    }

    void parse(ParserReader reader, String value) throws ParserException, IOException {
        if (!this.matches(reader, value))
            error(s("Cant parse constant '%s'").format(value), reader);

        String line = reader.consume().next(value.length()).go();

        assert value.equals(line) : "Trying to parse with ConstantParser a different value";
    }

    private boolean matches(ParserReader reader, String value) throws IOException {
        return reader.followed().by(value).go();
    }

}
