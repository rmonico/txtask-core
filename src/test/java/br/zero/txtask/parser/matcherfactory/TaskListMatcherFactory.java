package br.zero.txtask.parser.matcherfactory;

import static br.zero.matchers.FunctionalFeatureMatcherFactory.feature;

import br.zero.matchers.FunctionalFeatureMatcherFactory;
import br.zero.txtask.model.TaskContainer;
import br.zero.txtask.model.TaskList;

public class TaskListMatcherFactory {

    public static FunctionalFeatureMatcherFactory<? super TaskList, String> title() {
        return feature(TaskList::getTitle).description("list's title").name("title");
    }

    public static FunctionalFeatureMatcherFactory<? super TaskList, String> comment() {
        return feature(TaskList::getComment).description("list title's comment").name("comment");
    }

    public static TaskMatcherFactory task(int index) {
        return new TaskMatcherFactory((TaskContainer container) -> container.getTasks().get(index));
    }

    public static FunctionalFeatureMatcherFactory<? super TaskList, Integer> taskCount() {
        return feature((TaskList taskList) -> taskList.getTasks().size()).description("task list's size").name("task list's size");
    }

}
