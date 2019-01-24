package br.zero.txtask.core.model;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private String title;
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
