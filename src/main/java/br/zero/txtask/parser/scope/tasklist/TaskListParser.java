package br.zero.txtask.parser.scope.tasklist;

import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.reader.ParserReader;
import br.zero.txtask.parser.scope.ElementParser;

public class TaskListParser implements ElementParser<TaskList> {

    private TaskList taskList;

    @Override
    public TaskList parse(ParserReader reader) {
        return this.taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

}
