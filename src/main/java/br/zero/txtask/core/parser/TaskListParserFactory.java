package br.zero.txtask.core.parser;

public class TaskListParserFactory {

    public static TaskListParser create() {
        return new TaskListParserImpl();
    }
}
