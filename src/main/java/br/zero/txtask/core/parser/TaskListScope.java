package br.zero.txtask.core.parser;

import static br.zero.txtask.core.parser.ScopeBuilder.newScope;

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
        super();

        InternalTaskListParser parser = new InternalTaskListParser();
        setParser(parser);

        this.taskList = new TaskList();
        parser.setTaskList(this.taskList);
    }

    private Scope<String> newListTitleScope() {
        return newScope(ListTitleScope::new).parent(this).consumer(this::setListTitle).make();
    }

    private Scope<Task> newRootTaskScope() {
        return newScope(RootTaskScope::new).parent(this).consumer(this::addRootTask).make();
    }

    private Scope<Tag> newTagGroupScope() {
        return newScope(TagGroupScope::new).parent(this).consumer(this::addImplicitTag).make();
    }

    private Scope<String> newEmptyLineScope() {
        return newScope(EmptyLineScope::new).parent(this).consumer(this::addEmptyLine).make();
    }

    private Scope<String> newGarbageLineScope() {
        return newScope(GarbageScope::new).parent(this).consumer(this::addGarbageLine).make();
    }

    public Scope<?>[] getPossibleMatchers(ParserReader reader) {
        if (reader.position() == 0) {
            return new Scope<?>[] { newListTitleScope() };
        } else {
            return new Scope<?>[] { newRootTaskScope(), newTagGroupScope(), newEmptyLineScope(), newGarbageLineScope() };
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
