package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.ParserException.error;
import static br.zero.txtask.parser.internal.Constants.TAG_MARK;

class TaskTitleParser {

    private static final TaskTitleParser instance = new TaskTitleParser();

    static TaskTitleParser taskTitleParser() {
        return instance;
    }

    String parse(ParserReader reader) throws ParserException, IOException {
        if (!this.matches(reader))
            error("Cant parse task title", reader);

        String title = reader.consume().until(" " + TAG_MARK).or().eol().go();

        if (reader.followed().by(" ").go())
            reader.consume().next(1).go();

        return title;
    }

    private boolean matches(ParserReader reader) throws IOException {
        return !reader.followed().by(TAG_MARK).byEol().go();
    }

}
