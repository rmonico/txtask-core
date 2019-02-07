package br.zero.txtask.core.parser;

import java.io.IOException;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class InternalTaskListParser implements ElementParser<TaskList> {

    private TaskList taskList;

    @Override
    public TaskList parse(ParserReader reader) throws ParserException, IOException {
        return this.taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

}
