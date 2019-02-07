package br.zero.txtask.core.parser;

import java.io.IOException;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.element.blankline.EmptyLineScope;
import br.zero.txtask.core.parser.element.garbage.GarbageScope;
import br.zero.txtask.core.parser.element.listtitle.ListTitleScope;
import br.zero.txtask.core.parser.element.taggroup.TagGroupScope;
import br.zero.txtask.core.parser.element.task.RootTaskScope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TaskListScope extends AbstractScope<TaskList> {

    private TaskList taskList;

    public TaskListScope() {
        super(null, new InternalTaskListParser(), null);

        InternalTaskListParser parser = (InternalTaskListParser) getParser();
        this.taskList = new TaskList();
        parser.setTaskList(this.taskList);
    }

    private Scope<String> createListTitleDescription() {
        return new ListTitleScope(this::setListTitle);
    }

    private Scope<Task> createRootTaskDescription() {
        return new RootTaskScope(this::addRootTask);
    }

    private Scope<Tag> createTagGroupDescription() {
        return new TagGroupScope(this::addImplicitTag);
    }

    private Scope<String> createEmptyLineDescription() {
        return new EmptyLineScope(this::addEmptyLine);
    }

    private Scope<String> createGarbageLineDescription() {
        return new GarbageScope(this::addGarbageLine);
    }

    public Scope<?>[] getPossibleMatchers(ParserReader reader) {
        if (reader.position() == 0) {
            return new Scope<?>[] { createListTitleDescription() };
        } else {
            return new Scope<?>[] { createRootTaskDescription(), createTagGroupDescription(), createEmptyLineDescription(), createGarbageLineDescription() };
        }
    }

    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        for (Scope<?> description : getPossibleMatchers(reader))
            if (description.getMatcher().matchs(reader)) {
                return description;
            }

        if (reader.position() == 0)
            throw new ParserException("List title must start with ':: '");

        assert false : "No scope identified";

        return null;
    }

    public void setListTitle(String title) {
        this.taskList.setTitle(title);
    }

    public void addRootTask(Task task) {
        this.taskList.getTasks().add(task);
    }

    public void addImplicitTag(Tag tag) {
    }

    public void addEmptyLine(String emptyLine) {
    }

    public void addGarbageLine(String garbage) {
    }

}
