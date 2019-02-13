package br.zero.txtask.parser;

public class TaskListParserFactory {

    public static TaskListParser create() {
        return new TaskListParserImpl();
    }
}
