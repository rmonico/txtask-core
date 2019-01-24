package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.TaskList;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReaderParserTests {

    @Test
    public void should_not_parse_a_empty_buffer() {
        Parser parser = new ReaderParser(new StringReader(""));

        assertThrows(ParserException.class, parser::parse, "Reader is empty");
    }

    @Test
    public void should_parse_list_title_and_return_a_empty_list() throws ParserException {
        Parser parser = new ReaderParser(new StringReader(":: List title"));

        TaskList list = parser.parse();

        assertThat(list.getTitle(), is("List title"));
    }
}
