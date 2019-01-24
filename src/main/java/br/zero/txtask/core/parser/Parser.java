package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.Task;

import java.util.List;

public interface Parser {

    List<Task> parse() throws ParserException;

}
