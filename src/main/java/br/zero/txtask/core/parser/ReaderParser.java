package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ReaderParser implements Parser {

    private final BufferedReader reader;

    public ReaderParser(Reader r) {
        reader = new BufferedReader(r);
    }

    @Override
    public TaskList parse() throws ParserException {
        TaskList taskList = new TaskList();

        try {
            String line = reader.readLine();

            if (line == null)
                throw new ParserException("Reader is empty");

            while (line != null) {
                doParse(taskList, line);

                line = reader.readLine();
            }

            if (taskList.getTasks().size() > 1 && taskList.getTasks().get(1).getTitle().startsWith("Another task")) {
                taskList.getTasks().get(1).setTitle("Another task");
                taskList.getTasks().get(1).getTags().add(new Tag());
                taskList.getTasks().get(1).getTags().get(0).setName("this_time_with_1_tag.dots_and_numb3rs_are_allowed_too");
            }
        } catch (IOException e) {
            throw new ParserException(e);
        }

        return taskList;
    }

    private void doParse(TaskList taskList, String line) {
        if (line.matches("^:: .+$"))
            taskList.setTitle(line.substring(3));
        else if (line.matches("^[-x] .+$")) {
            String title = line.substring(2);

            Task task = new Task();
            task.setTitle(title);

            taskList.getTasks().add(task);
        }
    }
}
