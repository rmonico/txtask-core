package br.zero.txtask.model;

import java.util.ArrayList;
import java.util.List;

public class TaskList implements TaskContainer {

    private String title;
    private List<Task> tasks;
    private String comment;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
