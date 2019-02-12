package br.zero.txtask.parser.matcherfactory;

import static br.zero.matchers.FunctionalFeatureMatcherFactory.feature;

import java.util.function.Function;

import br.zero.matchers.FunctionalFeatureMatcherFactory;
import br.zero.txtask.model.Status;
import br.zero.txtask.model.Task;
import br.zero.txtask.model.TaskContainer;

public class TaskMatcherFactory {

    private Function<TaskContainer, Task> itemExtractor;

    public TaskMatcherFactory(Function<TaskContainer, Task> itemExtractor) {
        this.itemExtractor = itemExtractor;
    }

    public FunctionalFeatureMatcherFactory<? super TaskContainer, String> title() {
        return feature((TaskContainer container) -> itemExtractor.apply(container).getTitle()).description("task's title").name("title");
    }

    public TagMatcherFactory tag(int index) {
        return new TagMatcherFactory(c -> itemExtractor.apply(c).getTags().get(index));
    }

    public FunctionalFeatureMatcherFactory<? super TaskContainer, Integer> tagCount() {
        return feature((TaskContainer container) -> itemExtractor.apply(container).getTags().size()).description("tags's list size").name("size");
    }

    public TaskMatcherFactory task(int index) {
        return new TaskMatcherFactory(c -> itemExtractor.apply(c).getTasks().get(index));
    }

    public FunctionalFeatureMatcherFactory<? super TaskContainer, Integer> taskCount() {
        return feature((TaskContainer container) -> itemExtractor.apply(container).getTasks().size()).description("tasks's list size").name("size");
    }

    public FunctionalFeatureMatcherFactory<? super TaskContainer, Status> status() {
        return feature((TaskContainer container) -> itemExtractor.apply(container).getStatus()).description("task's status").name("status");
    }

    public FunctionalFeatureMatcherFactory<? super TaskContainer, String> comment() {
        return feature((TaskContainer container) -> itemExtractor.apply(container).getComment()).description("task's comment").name("comment");
    }
}
