package br.zero.txtask.parser.internal;

import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.reader.ParserReader;

public class TaskListParser {

    public static TaskList parse(ParserReader reader) {
        TaskListParser parser = new TaskListParser();

        return parser.internalParse(reader);
    }

    private TaskList internalParse(ParserReader reader) {
        return null;
    }

}
