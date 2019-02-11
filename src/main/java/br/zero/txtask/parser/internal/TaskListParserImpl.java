package br.zero.txtask.parser.internal;

import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.TaskListParser;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.io.Reader;

public class TaskListParserImpl implements TaskListParser {

    @Override
    public TaskList parse(Reader source) throws ParserException {
        ParserReader parserReader = new ParserReader(source);
        return this.doParse(parserReader);
    }

    private TaskList doParse(ParserReader reader) throws ParserException {
        try {
            if (reader.finished())
                throw new ParserException("List is empty");

            return internalParse(reader);
        } catch (IOException e) {
            throw new ParserException(e);
        }
    }

    private TaskList internalParse(ParserReader reader) {
        return br.zero.txtask.parser.internal.TaskListParser.parse(reader);
    }

}
