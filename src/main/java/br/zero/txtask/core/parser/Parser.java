package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.Element;

import java.io.Reader;

public interface Parser<E extends Element> {

    E parse(Reader source) throws ParserException;

}
