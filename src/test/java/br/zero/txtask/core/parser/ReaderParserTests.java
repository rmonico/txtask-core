package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.TaskList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import static br.zero.txtask.core.matchers.TaskListMatchers.*;
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
    public void should_parse_list_title() throws ParserException {
        Parser parser = new ReaderParser(new StringReader(":: List title"));

        TaskList list = parser.parse();

        assertThat(list, title(is("List title")));
    }

    @Test
    @Disabled("Future")
    public void should_not_have_more_than_one_title_per_reader() {

    }

    @Test
    public void should_parse_list_title_and_its_tasks() throws IOException, ParserException {
        Parser parser = new ReaderParser(new FileReader("src/test/resources/should_parse_list_title_and_its_tasks.txk"));

        TaskList list = parser.parse();

        assertThat(list, title(is("A simple task list")));

        assertThat(list, taskCount(is(3)));
        assertThat(list, task(0).title(is("First task")));
        assertThat(list, task(1).title(is("Second task")));
        assertThat(list, task(2).title(is("Third task")));
    }

}
