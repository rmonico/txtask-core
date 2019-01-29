package br.zero.txtask.core.parser;

import java.io.Reader;

import br.zero.txtask.core.model.TaskList;

public interface TaskListParser {

    TaskList parse(Reader source) throws ParserException;

}
