package br.zero.txtask.core.matchers;

import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TaskItemMatcher extends BaseMatcher<TaskList> {
    private Integer itemIndex;
    private Matcher<String> titleMatcher;

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public void setTitleMatcher(Matcher<String> titleMatcher) {
        this.titleMatcher = titleMatcher;
    }

    private String analyse(Object actual) {
        if (actual == null)
            return "null";
        else if (!(actual instanceof TaskList))
            return "wrong class";

        if (itemIndex == null)
            return "no item specified";

        TaskList actualList = (TaskList) actual;

        if (itemIndex > actualList.getTasks().size() - 1)
            return "item doesnt exist";

        if (titleMatcher != null) {
            Task actualTask = actualList.getTasks().get(itemIndex);

            if (!titleMatcher.matches(actualTask.getTitle()))
                return "contents wrong";
            else
                return "ok";
        } else
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

        if (itemIndex != null)
            if (titleMatcher != null) {
                description.appendText(String.format(" where the description of %d%s item ", this.itemIndex + 1, this.itemIndexSuffix()));
                description.appendDescriptionOf(titleMatcher);
            } else
                description.appendText(String.format(" containing at least %d items", this.itemIndex + 1));
        else
            description.appendText(", no item specified");
    }

    private String itemIndexSuffix() {
        switch (this.itemIndex) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        String result = analyse(item);

        if ("null".equals(result))
            description.appendText("a null instance");
        else if ("wrong class".equals(result))
            description.appendText(String.format("a instance of '%s'", item.getClass().toString()));
        else {
            TaskList taskList = (TaskList) item;

            if ("no item specified".equals(result) || this.titleMatcher == null)
                description.appendText("any TaskList instance");
            else {
                description.appendText("a TaskList instance");
                if ("item doesnt exist".equals(result))
                    description.appendText(String.format(" with at least %d items", this.itemIndex + 1));
                else {
                    Task task = taskList.getTasks().get(this.itemIndex);

                    description.appendText(String.format(" where the description of %d%s item is '%s'", this.itemIndex + 1, this.itemIndexSuffix(), task.getTitle()));
                }
            }
        }
    }
}
