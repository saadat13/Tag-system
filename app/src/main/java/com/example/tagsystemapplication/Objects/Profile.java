package com.example.tagsystemapplication.Objects;

import java.util.ArrayList;

public class Profile{
    private ArrayList<SystemObject> contents;
    private boolean isMultiContent;
    private boolean hasNext;
    private boolean isTagged = false;

    public Profile(ArrayList<SystemObject> contents) {
        this.contents = contents;
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

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public ArrayList<SystemObject> getContents() {
        return contents;
    }

    public void setContents(ArrayList<SystemObject> contents) {
        this.contents = contents;
    }

}
