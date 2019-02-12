package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.ParserException.error;
import static br.zero.txtask.parser.internal.Constants.TAG_PREFIX;

class TaskTitleParser {

    private static final TaskTitleParser parser = new TaskTitleParser();

    static String parse(ParserReader reader) throws IOException, ParserException {
        return parser.internalParse(reader);
    }

    private String internalParse(ParserReader reader) throws ParserException, IOException {
        if (!this.matches(reader))
            error("Cant parse task title", reader);

        return reader.consume().until(TAG_PREFIX).or().eol().go();
    }

    private boolean matches(ParserReader reader) throws IOException {
        return !reader.followed().byAnyOf(TAG_PREFIX, "\n").go();
    }

}
