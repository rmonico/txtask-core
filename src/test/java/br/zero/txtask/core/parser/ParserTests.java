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

public class ParserTests {

    @Test
    public void should_not_parse_a_empty_buffer() {
        Parser parser = new TaskListParser(new StringReader(""));

        assertThrows(ParserException.class, parser::parse, "Reader is empty");
    }

    @Test
    public void should_parse_list_title() throws ParserException {
        Parser parser = new TaskListParser(new StringReader(":: List title"));

        TaskList list = parser.parse();

        assertThat(list, title(is("List title")));
    }

    @Test
    @Disabled("Future")
    public void should_not_have_more_than_one_title_per_reader() {

    }

    @Test
    public void should_parse_list_title_and_its_tasks() throws IOException, ParserException {
        Parser parser = new TaskListParser(new FileReader("src/test/resources/should_parse_list_title_and_its_tasks.txk"));

        TaskList list = parser.parse();

        assertThat(list, title(is("A simple task list")));

        assertThat(list, taskCount(is(3)));
        assertThat(list, task(0).title(is("First task")));
        assertThat(list, task(1).title(is("Second task")));
        assertThat(list, task(2).title(is("Third task")));
    }

    @Test
    public void should_parse_task_tags() throws FileNotFoundException, ParserException {
        Parser parser = new TaskListParser(new FileReader("src/test/resources/should_parse_task_tags.txk"));

        TaskList list = parser.parse();

        assertThat(list, title(is("A list to test tags on tasks")));

        assertThat(list, taskCount(is(2)));
        assertThat(list, task(0).title(is("Task with no tags")));
        assertThat(list, task(1).title(is("Another task")));
        assertThat(list, task(1).tagCount(is(2)));
        assertThat(list, task(1).tag(0).name(is("this_time_with_1_tag.dots_and_numb3rs_are_allowed_too")));
        assertThat(list, task(1).tag(1).name(is("another_tag")));
    }
}
