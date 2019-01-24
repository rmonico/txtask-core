package br.zero.txtask.core.parser;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReaderParserTests {

    @Test
    public void should_not_parse_a_empty_buffer() {
        final Parser parser = new ReaderParser(new StringReader(""));

        assertThrows(ParserException.class, parser::parse);
    }
}
