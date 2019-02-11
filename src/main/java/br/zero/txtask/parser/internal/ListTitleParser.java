package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.java.StringFormatter.s;

public class ListTitleParser {

    public static String parse(ParserReader reader) throws IOException, ParserException {
        ListTitleParser parser = new ListTitleParser();

        return parser.internalParse(reader);
    }

    private String internalParse(ParserReader reader) throws IOException, ParserException {
        if (!this.matches(reader))
            throw new ParserException(s("List title must start with '%s'").format(Constants.LIST_TITLE_PREFIX));

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
