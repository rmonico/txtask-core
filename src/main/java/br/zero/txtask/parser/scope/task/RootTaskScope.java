package br.zero.txtask.parser.scope.task;

import br.zero.txtask.model.Status;
import br.zero.txtask.model.Tag;
import br.zero.txtask.model.Task;
import br.zero.txtask.parser.scope.AbstractScope;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.scope.Scope;
import br.zero.txtask.parser.scope.ElementParser;
import br.zero.txtask.parser.scope.tag.TagGroupScope;
import br.zero.txtask.parser.reader.ParserReader;
import br.zero.txtask.parser.scope.ScopeBuilder;
import br.zero.txtask.parser.scope.task.status.TaskStatusScope;
import br.zero.txtask.parser.scope.task.title.TaskTitleScope;

import java.io.IOException;
import java.util.List;

import static br.zero.txtask.parser.scope.ScopeBuilder.newScope;
import static br.zero.txtask.parser.scope.tag.Constants.TAG_PREFIX;

public class RootTaskScope extends AbstractScope<Task> implements ElementParser<Task> {

    private Task task;
    private String step;

    public RootTaskScope() {
        this.setParser(this);
        this.step = "idle";
    }

    @Override
    public Task parse(ParserReader reader) {
        step = "idle";

        return this.task;
    }
    @Override
    public Scope<?> findScope(ParserReader reader) throws IOException, ParserException {
        if (stepIs("idle")) {
            this.step = "parsing status";
            this.task = new Task();

            Scope<Status> scope = newScope(TaskStatusScope::new).parent(this).consumer(this.task::setStatus).make();

            return scope.findScope(reader);
        } else if (stepIs("parsing status")) {
            step = "parsing title";
            Scope<String> scope = newScope(TaskTitleScope::new).parent(this).consumer(this.task::setTitle).make();

            return scope.findScope(reader);
        } else if (stepIs("parsing title") && !reader.followed().byEol().go()) {
            step = "parsing tags";
            Scope<List<Tag>> scope = newScope(() -> new TagGroupScope(TAG_PREFIX)).parent(this).consumer(this.task.getTags()::addAll).make();

            return scope.findScope(reader);
        } else {
            step = "consuming task";

            return this;
        }
    }

    private boolean stepIs(String stepName) {
        return stepName.equals(this.step);
    }

    @Override
    public boolean done() {
        return stepIs("consuming task") || stepIs("idle");
    }

}
