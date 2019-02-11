package br.zero.txtask.parser;

import br.zero.txtask.parser.internal.TaskListParserImpl;

public class TaskListParserFactory {

    public static TaskListParser create() {
        return new TaskListParserImpl();
    }
}
