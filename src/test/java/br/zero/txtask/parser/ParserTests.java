package br.zero.txtask.parser;

import br.zero.txtask.model.TaskList;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.model.Status.DONE;
import static br.zero.txtask.model.Status.OPEN;
import static br.zero.txtask.parser.internal.Constants.TAG_MARK;
import static br.zero.txtask.parser.matcherfactory.TaskListMatcherFactory.*;
import static java.time.Duration.ofSeconds;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class ParserTests {

    @Test
    public void should_parse_list_title() throws ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new StringReader(":: List title"));

        assertThat(list, title().should(is("List title")));
    }

    @Test
    public void should_not_parse_a_empty_buffer() {
        TaskListParser parser = TaskListParserFactory.create();

        ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new StringReader("")));

        assertThat(exception.getMessage(), is("List is empty"));
    }

    @Test
    public void should_always_start_with_list_title() {
        TaskListParser parser = TaskListParserFactory.create();

        ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new StringReader("- Task on first line")));

        assertThat(exception.getMessage(), is("List title must start with ':: '; found: '- Task on first line'"));
    }

    @Test
    public void should_stop_parsing_on_invalid_token() {
        TaskListParser parser = TaskListParserFactory.create();

        assertTimeoutPreemptively(ofSeconds(3), () -> {
            ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new FileReader("src/test/resources/should_stop_parsing_on_invalid_token.txk")), "Invalid token: '__invalid__token__'");

            assertThat(exception.getMessage(), is("Invalid token; found: '__invalid_token__'"));
        });
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

    @Test
    public void should_not_allow_task_title_with_tag_mark() {
        TaskListParser parser = TaskListParserFactory.create();

        StringBuilder sb = new StringBuilder();
        sb.append(s(":: Title%n").format());
        sb.append(s("- Valid task%n").format());
        sb.append(s("- Invalid%stask%n").format(TAG_MARK));

        ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new StringReader(sb.toString())));

        assertThat(exception.getMessage(), is(s("Task title cant have TAG_MARK ('%s')").format(TAG_MARK)));
    }

    @Test
    public void should_not_allow_tag_name_with_invalid_values() {
        TaskListParser parser = TaskListParserFactory.create();

        ParserException exception = assertThrows(ParserException.class, () -> parser.parse(new FileReader("src/test/resources/should_not_allow_tag_name_with_invalid_values_1.txk")));

        assertThat(exception.getMessage(), is("Tags cant have uppercase letters"));

        exception = assertThrows(ParserException.class, () -> parser.parse(new FileReader("src/test/resources/should_not_allow_tag_name_with_invalid_values_2.txk")));

        assertThat(exception.getMessage(), is("Tags can have only a-z, 0-9 or _ characters"));

        exception = assertThrows(ParserException.class, () -> parser.parse(new FileReader("src/test/resources/should_not_allow_tag_name_with_invalid_values_3.txk")));

        assertThat(exception.getMessage(), is("Tags must start with a-z, or _ characters"));
    }

    @Test
    public void should_parse_single_line_comments_on_root_tasks() throws FileNotFoundException, ParserException {
        TaskListParser parser = TaskListParserFactory.create();

        TaskList list = parser.parse(new FileReader("src/test/resources/should_parse_single_line_comments_on_root_tasks.txk"));

        assertThat(list, task(0).title().should(is("First task with comment")));
        assertThat(list, task(0).comment().should(is("Comment contents")));
    }
}
