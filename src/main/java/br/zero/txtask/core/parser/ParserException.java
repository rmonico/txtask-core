package br.zero.txtask.core.parser;

import java.io.IOException;

public class ParserException extends Exception {
    public ParserException(Throwable e) {
        super(e);
    }

    public ParserException(final String message) {
        super(message);
    }
}
