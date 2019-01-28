package br.zero.txtask.core.parser;

import static br.zero.txtask.core.matchers.TaskListMatchers.task;
import static br.zero.txtask.core.matchers.TaskListMatchers.taskCount;
import static br.zero.txtask.core.matchers.TaskListMatchers.title;
import static java.time.Duration.ofSeconds;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import br.zero.txtask.core.model.TaskList;

public class ParserTests {

    @Test
    public void should_not_parse_a_empty_buffer() {
        TaskListParser parser = new TaskListParser();

        ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new StringReader("")));

        assertThat(exception.getMessage(), is("List is empty"));
    }

    @Test
    public void should_parse_list_title() throws ParserException {
        TaskListParser parser = new TaskListParser();

        TaskList list = parser.parse(new StringReader(":: List title"));

        assertThat(list, title(is("List title")));
    }

    @Test
    public void should_parse_list_title_and_its_tasks() throws IOException, ParserException {
        TaskListParser parser = new TaskListParser();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_list_title_and_its_tasks.txk"));

        assertThat(list, title(is("A simple task list")));

        assertThat(list, task(0).title(is("First task")));
        assertThat(list, task(1).title(is("Second task")));
        assertThat(list, task(2).title(is("Third task")));
        assertThat(list, taskCount(is(3)));
    }

    @Test
    public void should_parse_task_tags() throws FileNotFoundException, ParserException {
        TaskListParser parser = new TaskListParser();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_task_tags.txk"));

        assertThat(list, title(is("A list to test tags on tasks")));

        assertThat(list, task(0).title(is("Task with no tags")));

        assertThat(list, task(1).title(is("Another task")));
        assertThat(list, task(1).tag(0).name(is("this_time_with_1_tag.dots_and_numb3rs_are_allowed_too")));
        assertThat(list, task(1).tag(1).name(is("another_tag")));
        assertThat(list, task(1).tagCount(is(2)));

        assertThat(list, taskCount(is(2)));
    }

    @Test
    public void should_stop_parsing_on_invalid_token() throws FileNotFoundException, ParserException {
        TaskListParser parser = new TaskListParser();

        assertTimeoutPreemptively(ofSeconds(3), () -> {
            assertThrows(ParserException.class, () -> {
                parser.parse(new FileReader("src/test/resources/should_stop_parsing_on_invalid_token.txk"));
            }, "Invalid token: '__invalid__token__'");
        });
    }

    @Test
    public void should_parse_tag_group() throws FileNotFoundException, ParserException {
        TaskListParser parser = new TaskListParser();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_tag_group.txk"));
        assertThat(list, title(is("Task list with tag groups")));

        assertThat(list, task(0).title(is("Task with implicit tags")));
        assertThat(list, task(0).tag(0).name(is("explicit_tag")));
        assertThat(list, task(0).tag(1).name(is("tag")));
        assertThat(list, task(0).tag(2).name(is("another_tag")));
        assertThat(list, task(0).tag(3).name(is("one_more_tag")));
        assertThat(list, task(0).tagCount(is(4)));

        assertThat(list, task(1).title(is("Another task with implicit tags")));
        assertThat(list, task(1).tag(0).name(is("explicit_another_tag")));
        assertThat(list, task(1).tag(1).name(is("tag")));
        assertThat(list, task(1).tag(2).name(is("another_tag")));
        assertThat(list, task(1).tag(3).name(is("one_more_tag")));
        assertThat(list, task(1).tagCount(is(4)));

        assertThat(list, task(2).title(is("Task with just one implicit tag")));
        assertThat(list, task(2).tag(0).name(is("explicit_tag")));
        assertThat(list, task(2).tag(1).name(is("one_more_tag")));
        assertThat(list, task(2).tagCount(is(2)));

        assertThat(list, taskCount(is(3)));
    }

    @Test
    public void should_not_parse_list_with_invalid_header() throws ParserException {
        TaskListParser parser = new TaskListParser();

        ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new StringReader("== Invalid list header, should be :: ")));

        assertThat(exception.getMessage(), is("List must start with ':: '"));
    }

}
