package br.zero.txtask.core.parser.element.blankline;

import java.io.IOException;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class EmptyLineParser implements ElementParser<String> {
    @Override
    public void put(TaskList taskList,
            Object element) throws ParserException {
    }

    @Override
    public String parse(ParserReader reader) throws ParserException, IOException {
        String line = reader.consume().next(1).go();

        assert "\n".equals(line) : "Trying to parse with BlankLineParser on non-blank line";

        return line;
    }
}