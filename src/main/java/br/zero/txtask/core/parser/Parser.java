package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.TaskList;

import java.io.Reader;

public interface Parser {

    TaskList parse(Reader source) throws ParserException;

}
