package br.zero.txtask.parser.internal;

import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.ParserException.error;
import static br.zero.txtask.parser.internal.ConstantParser.constantParser;
import static br.zero.txtask.parser.internal.ListTitleParser.listTitleParser;
import static br.zero.txtask.parser.internal.TaskParser.taskParser;

class TaskListParser {

    private static final TaskListParser instance = new TaskListParser();

    static TaskListParser listParser() {
        return instance;
    }

    TaskList parse(ParserReader reader) throws IOException, ParserException {
        TaskList taskList = new TaskList();

        taskList.setTitle(listTitleParser().parse(reader));

        if (reader.finished())
            return taskList;

        constantParser().parseUntilNextNonEmptyLine(reader);

        while (!reader.finished()) {
            int initialPosition = reader.position();

            taskParser().parse(reader, taskList.getTasks()::add);

            if (initialPosition == reader.position())
                error("Invalid token", reader);
        }

        return taskList;
    }

}
