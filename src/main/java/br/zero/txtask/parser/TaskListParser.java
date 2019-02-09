package br.zero.txtask.parser;

import java.io.Reader;

import br.zero.txtask.model.TaskList;

public interface TaskListParser {

    TaskList parse(Reader source) throws ParserException;

}
