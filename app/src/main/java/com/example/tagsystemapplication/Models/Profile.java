package com.example.tagsystemapplication.Models;



import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject implements Serializable {


    @PrimaryKey
    private int id;
    private boolean isMultiContent;
    private boolean isTagged;
    private boolean isValidated;
    private RealmList<Content> contents;
    private RealmList<Tag>     tags;


    public Profile(){}


    public RealmList<Content> getContents() {
        return contents;
    }

    public void setContents(RealmList<Content> contents) {
        this.contents = contents;
    }

    public RealmList<Tag> getTags() {
        return tags;
    }

    public void setTags(RealmList<Tag> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTagged() {
        return isTagged;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
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

    @NonNull
    @Override
    public String toString() {
        return String.format("profile %d", id);
    }
}
