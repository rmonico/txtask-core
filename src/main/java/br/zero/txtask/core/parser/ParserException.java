package br.zero.txtask.core.parser;

public class ParserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1771024495164039959L;

	public ParserException(Throwable e) {
		super(e);
	}

	public ParserException(final String message) {
		super(message);
	}
}
