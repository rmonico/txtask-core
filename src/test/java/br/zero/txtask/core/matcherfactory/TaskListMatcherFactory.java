package br.zero.txtask.core.matchers;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import br.zero.txtask.core.model.TaskList;

public class TaskListMatchers {

    public static Matcher<TaskList> title(Matcher<String> titleMatcher) {
        return new FeatureMatcher<TaskList, String>(titleMatcher, "list's title", "list's title") {
            @Override
            protected String featureValueOf(TaskList actual) {
                return actual.getTitle();
            }
        };
    }

    public static Matcher<TaskList> taskCount(Matcher<Integer> taskCountMatcher) {
        return new FeatureMatcher<TaskList, Integer>(taskCountMatcher, "task count", "task count") {

            @Override
            protected Integer featureValueOf(TaskList actual) {
                return actual.getTasks().size();
            }
        };
    }

    public static TaskMatchers task(int listIndex) {
        return new TaskMatchers(listIndex);
    }
}
