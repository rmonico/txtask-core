package br.zero.txtask.core.matchers;

import org.hamcrest.Matcher;

public class TaskListAssertions {

    private int itemIndex;

    public TaskListAssertions(int listIndex) {
        this.itemIndex = listIndex;
    }

    public static TaskListMatcher size(Matcher<Integer> sizeMatcher) {
        TaskListMatcher matcher = new TaskListMatcher();

        matcher.setSizeMatcher(sizeMatcher);

        return matcher;
    }

    public static TaskListMatcher listTitle(Matcher<String> titleMatcher) {
        TaskListMatcher matcher = new TaskListMatcher();

        matcher.setTitleMatcher(titleMatcher);

        return matcher;
    }

    public static TaskListAssertions task(int listIndex) {
        return new TaskListAssertions(listIndex);
    }

    public TaskItemMatcher title(Matcher<String> titleMatcher) {
        TaskItemMatcher itemMatcher = new TaskItemMatcher();

        itemMatcher.setItemIndex(this.itemIndex);

        itemMatcher.setTitleMatcher(titleMatcher);

        return itemMatcher;
    }

}
