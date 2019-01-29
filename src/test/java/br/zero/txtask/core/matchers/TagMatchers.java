package br.zero.txtask.core.matchers;

import static br.zero.txtask.core.matchers.StringFormatter.s;
import static br.zero.txtask.core.matchers.StringFormatter.ordinal;

import org.hamcrest.Matcher;

import br.zero.txtask.core.matchers.TaskMatchers.TaskFeatureMatcher;
import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;

public class TagMatchers {
    private int taskIndex;
    private int tagIndex;

    public TagMatchers(int taskIndex, int tagIndex) {
        this.taskIndex = taskIndex;
        this.tagIndex = tagIndex;
    }

    protected static abstract class TagFeatureMatcher<U> extends TaskFeatureMatcher<U> {
        private int tagIndex;

        public TagFeatureMatcher(Matcher<U> subMatcher, String featureName, int taskIndex, int tagIndex) {
            super(subMatcher, s("%s of %s tag").format(featureName, ordinal(tagIndex), ordinal(taskIndex)), taskIndex);
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
