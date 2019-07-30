package com.example.tagsystemapplication.Objects;

import java.util.ArrayList;

public class Profile{
    private int id;
    private ArrayList<Content> contents;
    private ArrayList<Tag> tags;
    private boolean isMultiContent;
    private boolean isTagged;
    private boolean isValid;

    public Profile(ArrayList<Content> contents, ArrayList<Tag> tags) {
        this.contents = contents;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isTagged() {
        return isTagged;
    }

    public void setTagged(boolean tagged) {
        isTagged = tagged;
    }

    public boolean isMultiContent() {
        return isMultiContent;
    }

    public void setMultiContent(boolean multiContent) {
        isMultiContent = multiContent;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }

}
