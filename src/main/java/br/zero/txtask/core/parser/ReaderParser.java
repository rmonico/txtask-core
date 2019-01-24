package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class ReaderParser implements Parser {

    private final BufferedReader reader;

    public ReaderParser(final Reader r) {
        reader = new BufferedReader(r);
    }

    @Override
    public List<Task> parse() throws ParserException {
        try {
            final String line = reader.readLine();

            if (ofNullable(line).orElse("").isEmpty())
                throw new ParserException("Reader is empty");
        } catch (IOException e) {
            throw new ParserException(e);
        }

        return null;
    }
}
