package br.zero.txtask.core.parser.element;

import java.io.IOException;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.reader.ParserReader;

public interface ElementParser<ELEMENTTYPE> {

    ELEMENTTYPE parse(ParserReader reader) throws ParserException, IOException;

    void put(TaskList taskList,
            ParserContext context,
            Object element) throws ParserException;

}
