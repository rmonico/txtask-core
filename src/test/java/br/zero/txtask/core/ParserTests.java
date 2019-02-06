package br.zero.txtask.core;

import static br.zero.txtask.core.matcherfactory.TaskListMatcherFactory.task;
import static br.zero.txtask.core.matcherfactory.TaskListMatcherFactory.taskCount;
import static br.zero.txtask.core.matcherfactory.TaskListMatcherFactory.title;
import static br.zero.txtask.core.model.Status.DONE;
import static br.zero.txtask.core.model.Status.OPEN;
import static java.time.Duration.ofSeconds;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.TaskListParser;
import br.zero.txtask.core.parser.TaskListParserFactory;

public class ParserTests {

    @Test
    public void should_not_parse_a_empty_buffer() {
        TaskListParser parser = TaskListParserFactory.create();

        ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new StringReader("")));

        assertThat(exception.getMessage(), is("List is empty"));
    }

    @Test
    public void should_parse_list_title() throws ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new StringReader(":: List title"));

        assertThat(list, title().should(is("List title")));
    }

    @Test
    public void should_parse_list_title_and_its_tasks() throws IOException, ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_list_title_and_its_tasks.txk"));

        assertThat(list, title().should(is("A simple task list")));

        assertThat(list, task(0).title().should(is("First task")));
        assertThat(list, task(1).title().should(is("Second task")));
        assertThat(list, task(2).title().should(is("Third task")));
        assertThat(list, taskCount().should(is(3)));
    }

    @Test
    public void should_parse_task_tags() throws FileNotFoundException, ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_task_tags.txk"));

        assertThat(list, title().should(is("A list to test tags on tasks")));

        assertThat(list, task(0).title().should(is("Task with no tags")));

        assertThat(list, task(1).title().should(is("Another task")));
        assertThat(list, task(1).tag(0).name().should(is("this_time_with_1_tag.dots_and_numb3rs_are_allowed_too")));
        assertThat(list, task(1).tag(1).name().should(is("another_tag")));
        assertThat(list, task(1).tagCount().should(is(2)));

        assertThat(list, taskCount().should(is(2)));
    }

    @Test
    public void should_stop_parsing_on_invalid_token() throws FileNotFoundException, ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        assertTimeoutPreemptively(ofSeconds(3), () -> {
            assertThrows(ParserException.class, () -> {
                parser.parse(new FileReader("src/test/resources/should_stop_parsing_on_invalid_token.txk"));
            }, "Invalid token: '__invalid__token__'");
        });
    }

    @Test
    @Disabled
    public void should_parse_tag_group() throws FileNotFoundException, ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_tag_group.txk"));
        assertThat(list, title().should(is("Task list with tag groups")));

        assertThat(list, task(0).title().should(is("Task with implicit tags")));
        assertThat(list, task(0).tag(0).name().should(is("explicit_tag")));
        assertThat(list, task(0).tag(1).name().should(is("tag")));
        assertThat(list, task(0).tag(2).name().should(is("another_tag")));
        assertThat(list, task(0).tag(3).name().should(is("one_more_tag")));
        assertThat(list, task(0).tagCount().should(is(4)));

        assertThat(list, task(1).title().should(is("Another task with implicit tags")));
        assertThat(list, task(1).tag(0).name().should(is("explicit_another_tag")));
        assertThat(list, task(1).tag(1).name().should(is("tag")));
        assertThat(list, task(1).tag(2).name().should(is("another_tag")));
        assertThat(list, task(1).tag(3).name().should(is("one_more_tag")));
        assertThat(list, task(1).tagCount().should(is(4)));

        assertThat(list, task(2).title().should(is("Task with just one implicit tag")));
        assertThat(list, task(2).tag(0).name().should(is("explicit_tag")));
        assertThat(list, task(2).tag(1).name().should(is("one_more_tag")));
        assertThat(list, task(2).tagCount().should(is(2)));

        assertThat(list, taskCount().should(is(3)));
    }

    @Test
    public void should_parse_task_statuses() throws FileNotFoundException, ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_task_statuses.txk"));

        assertThat(list, task(0).title().should(is("Open task")));
        assertThat(list, task(0).status().should(is(OPEN)));

        assertThat(list, task(1).title().should(is("Done task")));
        assertThat(list, task(1).status().should(is(DONE)));

        assertThat(list, taskCount().should(is(2)));
    }

    @Test
    @Disabled
    public void should_parse_subtasks() throws FileNotFoundException, ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_subtasks.txk"));

        assertThat(list, task(0).title().should(is("Parent task")));

        assertThat(list.getTasks(), notNullValue());
        assertThat(list.getTasks().get(0), notNullValue());
        assertThat(list.getTasks().get(0).getTitle(), is("Parent task"));
        assertThat(list.getTasks().size(), is(1));

        assertThat(list.getTasks().get(0).getTasks(), notNullValue());
        assertThat(list.getTasks().get(0).getTasks().get(0), notNullValue());
        assertThat(list.getTasks().get(0).getTasks().get(0).getTitle(), is("Sub task"));
        assertThat(list.getTasks().get(0).getTasks().size(), is(1));

        assertThat(list.getTasks().get(0).getTasks().get(0).getTasks(), notNullValue());
        assertThat(list.getTasks().get(0).getTasks().get(0).getTasks().get(0).getTitle(), is("Sub sub task"));
        assertThat(list.getTasks().get(0).getTasks().get(0).getTasks().size(), is(1));
    }

}
