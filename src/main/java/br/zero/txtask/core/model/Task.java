package br.zero.txtask.core.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private List<Tag> tags;

    public Task() {
        tags = new ArrayList<>();
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

}
