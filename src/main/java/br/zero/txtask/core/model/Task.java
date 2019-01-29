package br.zero.txtask.core.model;

import static br.zero.txtask.core.model.Status.OPEN;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private List<Tag> tags;
    private Status status;

    public Task() {
        tags = new ArrayList<>();
        status = OPEN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}
