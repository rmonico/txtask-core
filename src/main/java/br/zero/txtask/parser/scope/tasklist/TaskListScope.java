package br.zero.txtask.parser.scope.tasklist;

import br.zero.txtask.model.Tag;
import br.zero.txtask.model.Task;
import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.scope.AbstractScope;
import br.zero.txtask.parser.scope.Scope;
import br.zero.txtask.parser.scope.linebreak.LineBreakScope;
import br.zero.txtask.parser.scope.garbage.GarbageScope;
import br.zero.txtask.parser.scope.tasklist.title.ListTitleScope;
import br.zero.txtask.parser.scope.tag.TagGroupScope;
import br.zero.txtask.parser.scope.task.TaskScope;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static br.zero.txtask.parser.scope.ScopeBuilder.newScope;
import static br.zero.txtask.parser.scope.tag.Constants.*;

public class TaskListScope extends AbstractScope<TaskList> {

    private TaskList taskList;
    private Scope<?>[] remainingLineScopes;
    private List<Tag> implicitTags;

    public TaskListScope() {
        TaskListParser parser = new TaskListParser();
        setParser(parser);

        this.taskList = new TaskList();
        parser.setTaskList(this.taskList);

        remainingLineScopes = new Scope<?>[]{newEmptyLineScope(), newTagInsertionGroupScope(), newTagRemovalGroupScope(), newRootTaskScope(), newGarbageLineScope()};

        this.implicitTags = new ArrayList<>();
    }

    private Scope<Task> newRootTaskScope() {
        return newScope(TaskScope::new).parent(this).consumer(this::addRootTask).make();
    }

    private Scope<List<Tag>> newTagInsertionGroupScope() {
        return newTagGroupScope(this::addImplicitTags, IMPLICIT_TAG_INSERTION_MARK, IMPLICIT_TAG_INSERTION_PREFIX);
    }

    private Scope<List<Tag>> newTagRemovalGroupScope() {
        return newTagGroupScope(this::removeImplicitTags, IMPLICIT_TAG_REMOVAL_MARK, IMPLICIT_TAG_REMOVAL_PREFIX);
    }

    private Scope<List<Tag>> newTagGroupScope(Consumer<List<Tag>> consumer, String... prefixes) {
        return newScope(() -> new TagGroupScope(prefixes)).parent(this).consumer(consumer).make();
    }

    private Scope<String> newEmptyLineScope() {
        return newScope(LineBreakScope::new).parent(this).consumer(this::addEmptyLine).make();
    }

    private Scope<String> newGarbageLineScope() {
        return newScope(GarbageScope::new).parent(this).consumer(this::addGarbageLine).make();
    }

    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        if (reader.position() == 0)
            return findScopeForFirstLine(reader);

        for (Scope<?> scope : this.remainingLineScopes) {
            Scope<?> singleScope = scope.findScope(reader);

            if (singleScope != null)
                return singleScope;
        }

        assert false : "No scope identified";

        return null;
    }

    private Scope<?> findScopeForFirstLine(ParserReader reader) throws IOException, ParserException {
        Scope<?> scope = newScope(ListTitleScope::new).parent(this).consumer(this::setListTitle).make();

        Scope<?> singleScope = scope.findScope(reader);

        if (singleScope == null)
            throw new ParserException("List title must start with ':: '");

        return singleScope;
    }

    public void setListTitle(String title) {
        this.taskList.setTitle(title);
    }

    public void addRootTask(Task task) {
        this.taskList.getTasks().add(task);

        List<Tag> tags = task.getTags();

        tags.addAll(implicitTags);
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
