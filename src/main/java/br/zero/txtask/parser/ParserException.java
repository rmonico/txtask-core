package br.zero.txtask.parser;

import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.java.StringFormatter.s;

public class ParserException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -1771024495164039959L;

    public ParserException(Throwable e) {
        super(e);
    }

    public ParserException(String message) throws IOException {
        super(message);
    }

    public static void error(String message, ParserReader reader) throws IOException, ParserException {
        String exceptionMessage = message + s("; found: '%s'").format(reader.get().eol().go());

        throw new ParserException(exceptionMessage);
    }
}
