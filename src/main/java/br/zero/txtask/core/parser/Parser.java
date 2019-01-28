package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.Element;

public interface Parser<E extends Element> {

    E parse(Reader source) throws ParserException;

}
