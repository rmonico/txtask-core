package br.zero.txtask.parser;

import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.io.Reader;

import static br.zero.txtask.parser.internal.TaskListParser.listParser;

public class TaskListParserImpl implements TaskListParser {

    @Override
    public TaskList parse(Reader source) throws ParserException {
        ParserReader parserReader = new ParserReader(source);

        try {
            if (parserReader.finished())
                throw new ParserException("List is empty");

            return listParser().parse(parserReader);
        } catch (IOException e) {
            throw new ParserException(e);
        }
    }

}
