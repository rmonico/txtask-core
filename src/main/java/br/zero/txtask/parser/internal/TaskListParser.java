package br.zero.txtask.parser.internal;

import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

public class TaskListParser {

    public static TaskList parse(ParserReader reader) throws IOException, ParserException {
        TaskListParser parser = new TaskListParser();

        return parser.internalParse(reader);
    }

    private TaskList internalParse(ParserReader reader) throws IOException, ParserException {
        TaskList taskList = new TaskList();

        taskList.setTitle(ListTitleParser.parse(reader));

        return taskList;
    }

}
