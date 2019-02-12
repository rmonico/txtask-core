package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Task;
import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.internal.ConstantParser.parseUntilNextNonEmptyLine;

class TaskListParser {

    private static final TaskListParser instance = new TaskListParser();

    static TaskList parse(ParserReader reader) throws IOException, ParserException {
        return instance.internalParse(reader);
    }

    private TaskList internalParse(ParserReader reader) throws IOException, ParserException {
        TaskList taskList = new TaskList();

        taskList.setTitle(ListTitleParser.parse(reader));

        if (reader.finished())
            return taskList;

        parseUntilNextNonEmptyLine(reader);

        while (!reader.finished()) {
            Task task = TaskParser.parse(reader);

            taskList.getTasks().add(task);
        }

        return taskList;
    }

}
