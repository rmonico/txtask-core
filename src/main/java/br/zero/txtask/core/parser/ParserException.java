package br.zero.txtask.core.parser;

import static java.lang.String.format;

public class ParserException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -1771024495164039959L;

    public ParserException(Throwable e) {
        super(e);
    }

    public ParserException(String message, Object... objects) {
        super(format(message, objects));
    }
}
