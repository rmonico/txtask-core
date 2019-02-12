package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.parser.ParserException.error;

class ListTitleParser {

    private static final ListTitleParser instance = new ListTitleParser();

    static ListTitleParser listTitleParser() {
        return instance;
    }

    String parse(ParserReader reader) throws IOException, ParserException {
        if (!this.matches(reader))
            error(s("List title must start with '%s'").format(Constants.LIST_TITLE_PREFIX), reader);

        reader.consume().next(Constants.LIST_TITLE_PREFIX.length()).go();

        String title = reader.consume().eol().go();

        String lineBreak = reader.consume().next(1).go();

        assert "\n".equals(lineBreak) || "".equals(lineBreak) : "After list title should have a line break or EOF";

        return title;
    }

    private boolean matches(ParserReader reader) throws IOException {
        return reader.followed().by(Constants.LIST_TITLE_PREFIX).go();
    }

}
