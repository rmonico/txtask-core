package br.zero.txtask.core.parser;

import static br.zero.txtask.core.parser.ElementDescription.createDescription;

import java.io.IOException;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.element.blankline.EmptyLineParser;
import br.zero.txtask.core.parser.element.blankline.EmptyLineScope;
import br.zero.txtask.core.parser.element.garbage.GarbageParser;
import br.zero.txtask.core.parser.element.garbage.GarbageScope;
import br.zero.txtask.core.parser.element.listtitle.ListTitleMatcher;
import br.zero.txtask.core.parser.element.listtitle.ListTitleParser;
import br.zero.txtask.core.parser.element.taggroup.TagGroupParser;
import br.zero.txtask.core.parser.element.taggroup.TagGroupScope;
import br.zero.txtask.core.parser.element.task.TaskParser;
import br.zero.txtask.core.parser.element.task.TaskScope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TaskListScope {

    private TaskList taskList;

    public TaskListScope() {
        this.taskList = new TaskList();
    }

    private ElementDescription<String> createListTitleDescription() {
        return createDescription(new ListTitleMatcher(), new ListTitleParser(), this::setListTitle);
    }

    private ElementDescription<Task> createRootTaskDescription() {
        return createDescription(new TaskScope(), new TaskParser(), this::addRootTask);
    }

    private ElementDescription<Tag> createTagGroupDescription() {
        return createDescription(new TagGroupScope(), new TagGroupParser(), this::addImplicitTag);
    }

    private ElementDescription<String> createEmptyLineDescription() {
        return createDescription(new EmptyLineScope(), new EmptyLineParser(), this::addEmptyLine);
    }

    private ElementDescription<String> createGarbageLineDescription() {
        return createDescription(new GarbageScope(), new GarbageParser(), this::addGarbageLine);
    }

    public ElementDescription<?>[] getPossibleMatchers(ParserReader reader) {
        if (reader.position() == 0) {
            return new ElementDescription<?>[] { createListTitleDescription() };
        } else {
            return new ElementDescription<?>[] { createRootTaskDescription(), createTagGroupDescription(), createEmptyLineDescription(), createGarbageLineDescription() };
        }
    }

    public ElementDescription<?> findParser(ParserReader reader) throws ParserException, IOException {
        for (ElementDescription<?> description : getPossibleMatchers(reader))
            if (description.matcher.matchs(reader)) {
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

    public TaskList getTaskList() {
        return this.taskList;
    }

}
