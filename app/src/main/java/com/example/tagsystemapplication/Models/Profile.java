package com.example.tagsystemapplication.Models;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject implements Serializable {


    @SerializedName("id")
    @PrimaryKey
    private int id;
    @SerializedName("is_multi_content")
    private boolean isMultiContent;
    @SerializedName("is_tagged")
    private boolean isTagged;
    @SerializedName("is_validated")
    private boolean isValidated;
    @SerializedName("contents")
    private RealmList<Content> contents;
    @SerializedName("tags")
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
