package br.zero.txtask.core.matchers;

import br.zero.txtask.core.matchers.TaskMatchers.TaskFeatureMatcher;
import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;
import org.hamcrest.Matcher;

import static br.zero.txtask.core.matchers.Utilities.toStrWithOrdinal;
import static java.lang.String.format;

public class TagMatchers {
    private int taskIndex;
    private int tagIndex;

    public TagMatchers(int taskIndex, int tagIndex) {
        this.taskIndex = taskIndex;
        this.tagIndex = tagIndex;
    }


//    protected abstract class TaskFeatureMatcher<U> extends FeatureMatcher<TaskList, U> {
//
//        public TaskFeatureMatcher(Matcher<U> subMatcher, String featureName) {
//            super(subMatcher, format("%s of task %s", featureName, toStrWithOrdinal(TaskMatchers.this.itemIndex)), format("task's %s" , featureName));
//        }
//
//        @Override
//        protected U featureValueOf(TaskList actual) {
//            return this.featureOfTask(actual.getTasks().get(TaskMatchers.this.itemIndex));
//
//        }
//
//        protected abstract U featureOfTask(Task task);
//    }
//

    protected static abstract class TagFeatureMatcher<U> extends TaskFeatureMatcher<U> {
        private int tagIndex;

        public TagFeatureMatcher(Matcher<U> subMatcher, String featureName, int taskIndex, int tagIndex) {
            super(subMatcher, format("%s of %s tag", featureName, toStrWithOrdinal(tagIndex), toStrWithOrdinal(taskIndex)), taskIndex);
            this.tagIndex = tagIndex;
        }

        @Override
        protected U featureOfTask(Task task) {
            return this.featureOfTag(task.getTags().get(tagIndex));
        }

        protected abstract U featureOfTag(Tag tag);
    }

    public Matcher<TaskList> name(Matcher<String> tagNameMatcher) {
        return new TagFeatureMatcher<String>(tagNameMatcher, "name", taskIndex, tagIndex) {
            protected String featureOfTag(Tag tag) {
                return tag.getName();
            }

        };
    }
}
