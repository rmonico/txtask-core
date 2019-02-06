package br.zero.txtask.core.parser.element.garbage;

import java.io.IOException;

import br.zero.java.StringFormatter;
import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.AbstractElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class GarbageParser extends AbstractElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws ParserException, IOException {
        String token = reader.consume().eol().go();

        throw new ParserException(StringFormatter.s("Invalid token: '%s'").format(token));
    }

    @Override
    protected void internalPut(TaskList taskList,
            String token) {
        // TODO Save this data to rebuild original file exactly has it was
    }

}
