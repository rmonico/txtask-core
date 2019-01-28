package br.zero.txtask.core.matchers;

import static br.zero.txtask.core.matchers.Utilities.toStrWithOrdinal;
import static java.lang.String.format;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;

public class TaskMatchers {

    private int itemIndex;

    public TaskMatchers(int listIndex) {
        this.itemIndex = listIndex;
    }

    protected abstract static class TaskFeatureMatcher<U> extends FeatureMatcher<TaskList, U> {

        private int taskIndex;

        public TaskFeatureMatcher(Matcher<U> subMatcher, String featureName, int taskIndex) {
            super(subMatcher, format("%s of %s task", featureName, toStrWithOrdinal(taskIndex)), featureName);
            this.taskIndex = taskIndex;
        }

        @Override
        protected U featureValueOf(TaskList actual) {
            return this.featureOfTask(actual.getTasks().get(this.taskIndex));

        }

        protected abstract U featureOfTask(Task task);
    }

    public Matcher<TaskList> title(Matcher<String> titleMatcher) {
        return new TaskFeatureMatcher<String>(titleMatcher, "title", this.itemIndex) {

            @Override
            protected String featureOfTask(Task actual) {
                return actual.getTitle();
            }
        };
    }

    public Matcher<TaskList> tagCount(Matcher<Integer> tagCountMatcher) {
        return new TaskFeatureMatcher<Integer>(tagCountMatcher, "tag count", this.itemIndex) {

            @Override
            protected Integer featureOfTask(Task task) {
                return task.getTags().size();
            }
        };
    }

    public TagMatchers tag(int tagIndex) {
        return new TagMatchers(this.itemIndex, tagIndex);
    }
}
