package br.zero.txtask.core.parser;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.element.blankline.EmptyLineScope;
import br.zero.txtask.core.parser.element.garbage.GarbageScope;
import br.zero.txtask.core.parser.element.listtitle.ListTitleScope;
import br.zero.txtask.core.parser.element.taggroup.TagGroupScope;
import br.zero.txtask.core.parser.element.task.RootTaskScope;
import br.zero.txtask.core.parser.reader.ParserReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static br.zero.txtask.core.parser.ScopeBuilder.newScope;
import static br.zero.txtask.core.parser.element.taggroup.Constants.*;

public class TaskListScope extends AbstractScope<TaskList> {

    private TaskList taskList;
    private Scope<?>[] remainingLineScopes;
    private List<Tag> implicitTags;

    public TaskListScope() {
        super();

        InternalTaskListParser parser = new InternalTaskListParser();
        setParser(parser);

        this.taskList = new TaskList();
        parser.setTaskList(this.taskList);

        remainingLineScopes = new Scope<?>[]{newRootTaskScope(), newTagInsertionGroupScope(), newTagRemovalGroupScope(), newEmptyLineScope(), newGarbageLineScope()};

        this.implicitTags = new ArrayList<>();
    }

    private Scope<Task> newRootTaskScope() {
        return newScope(RootTaskScope::new).parent(this).consumer(this::addRootTask).make();
    }

    private Scope<List<Tag>> newTagInsertionGroupScope() {
        return newTagGroupScope(this::addImplicitTags, IMPLICIT_TAG_INSERTION_INITIAL_PREFIX, IMPLICIT_TAG_INSERTION_PREFIX);
    }

    private Scope<List<Tag>> newTagRemovalGroupScope() {
        return newTagGroupScope(this::removeImplicitTags, IMPLICIT_TAG_REMOVAL_INITIAL_PREFIX, IMPLICIT_TAG_REMOVAL_PREFIX);
    }

    private Scope<List<Tag>> newTagGroupScope(Consumer<List<Tag>> consumer, String... prefixes) {
        return newScope(() -> new TagGroupScope(prefixes)).parent(this).consumer(consumer).make();
    }

    private Scope<String> newEmptyLineScope() {
        return newScope(EmptyLineScope::new).parent(this).consumer(this::addEmptyLine).make();
    }

    private Scope<String> newGarbageLineScope() {
        return newScope(GarbageScope::new).parent(this).consumer(this::addGarbageLine).make();
    }

    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        if (reader.position() == 0)
            return findScopeForFirstLine(reader);

        for (Scope<?> scope : this.remainingLineScopes)
            if (scope.getMatcher().matchs(reader)) {
                return scope.findScope(reader);
            }

        assert false : "No scope identified";

        return null;
    }

    private Scope<?> findScopeForFirstLine(ParserReader reader) throws IOException, ParserException {
        Scope<?> scope = newScope(ListTitleScope::new).parent(this).consumer(this::setListTitle).make();

        scope = scope.findScope(reader);

        if (scope == null)
            throw new ParserException("List title must start with ':: '");

        return scope;
    }

    public void setListTitle(String title) {
        this.taskList.setTitle(title);
    }

    public void addRootTask(Task task) {
        this.taskList.getTasks().add(task);

        task.getTags().addAll(implicitTags);
    }

    public void addImplicitTags(List<Tag> tagList) {
        this.implicitTags.addAll(tagList);
    }

    public void removeImplicitTags(List<Tag> tagList) {
        this.implicitTags.removeAll(tagList);
    }

    public void addEmptyLine(String emptyLine) {
    }

    public void addGarbageLine(String garbage) {
    }

}
