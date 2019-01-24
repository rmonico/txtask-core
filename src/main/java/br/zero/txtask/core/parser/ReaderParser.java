package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.TaskList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import static java.util.Optional.ofNullable;

public class ReaderParser implements Parser {

    private final BufferedReader reader;

    public ReaderParser(Reader r) {
        reader = new BufferedReader(r);
    }

    @Override
    public TaskList parse() throws ParserException {
        TaskList taskList = new TaskList();

        try {
            final String line = reader.readLine();

            if (ofNullable(line).orElse("").isEmpty())
                throw new ParserException("Reader is empty");

            if (line.matches("^:: .+$"))
                taskList.setTitle(line.substring(3));

        } catch (IOException e) {
            throw new ParserException(e);
        }

        return taskList;
    }
}
