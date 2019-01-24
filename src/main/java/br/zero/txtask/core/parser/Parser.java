package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.TaskList;

public interface Parser {

    TaskList parse() throws ParserException;

}
