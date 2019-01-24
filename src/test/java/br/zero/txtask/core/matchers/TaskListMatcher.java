package br.zero.txtask.core.matchers;

import br.zero.txtask.core.model.TaskList;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TaskListMatcher extends BaseMatcher<TaskList> {

    private Matcher<String> titleMatcher;
    private Matcher<Integer> sizeMatcher;

    public void setTitleMatcher(Matcher<String> titleMatcher) {
        this.titleMatcher = titleMatcher;
    }

    public void setSizeMatcher(Matcher<Integer> sizeMatcher) {
        this.sizeMatcher = sizeMatcher;
    }

    private String analyse(Object actual) {
        if (actual == null)
            return "null";

        if (!(actual instanceof TaskList))
            return "wrong class";

        TaskList actualList = (TaskList) actual;

        if (titleMatcher != null)
            if (!titleMatcher.matches(actualList.getTitle()))
                return "wrong title";
            else
                return "ok";
        else if (sizeMatcher != null)
            if (!sizeMatcher.matches(actualList.getTasks().size()))
                return "wrong size";
            else
                return "ok";
        else
            return "no matcher";
    }

    @Override
    public boolean matches(Object actual) {
        String result = analyse(actual);

        return "ok".equals(result);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a TaskList instance");

        if (this.titleMatcher != null) {
            description.appendText(" which title ");
            description.appendDescriptionOf(this.titleMatcher);
        } else if (this.sizeMatcher != null) {
            description.appendText(" which size ");
            description.appendDescriptionOf(this.sizeMatcher);
        }
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        String result = analyse(item);

        description.appendText(String.format("Result: '%s'", result));

        if ("null".equals(result))
            description.appendText("a null reference");
        else if ("wrong class".equals(result)) {
            description.appendText("a instance of ");
            description.appendText(item.getClass().toString());
        } else {
            TaskList taskListItem = ((TaskList) item);
            String itemTitle = taskListItem.getTitle();
            int listSize = taskListItem.getTasks().size();

            if ("wrong title".equals(result))
                description.appendText(String.format("a TaskList which title is '%s'", itemTitle));
            else if ("wrong size".equals(result))
                description.appendText(String.format("a TaskList which size is %d", listSize));
            else
                description.appendText(String.format("a instance of TaskList with title '%s' and size of %d", itemTitle, listSize));
        }
    }
}