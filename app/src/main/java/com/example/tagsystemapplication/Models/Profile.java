package com.example.tagsystemapplication.Models;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.ConnectException;
import java.util.List;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject implements Serializable {


    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("is_multi_content")
    private boolean isMultiContent;

    @SerializedName("is_tagged")
    private boolean isTagged;

    @SerializedName("is_valid")
    private boolean isValidated;

    @SerializedName("status")
    private String status;

    @SerializedName("expire_date")
    private String expireDate;

    @Ignore
    @SerializedName("content")
    private List<Content> contents;

    @Ignore
    @SerializedName("tag")
    private List<Tag> tags;

    // these two fields is for database not for retrofit

    private RealmList<Content> realmContents;
    private RealmList<Tag> realmTags;

    public Profile(){}


    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public RealmList<Content> getRealmContents() {
        return realmContents;
    }

    public void setRealmContents(RealmList<Content> realmContents) {
        this.realmContents = realmContents;
    }

    public RealmList<Tag> getRealmTags() {
        return realmTags;
    }

    public void setRealmTags(RealmList<Tag> realmTags) {
        this.realmTags = realmTags;
    }



    public boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }


    public void setContents(RealmList<Content> contents) {
        this.contents = contents;
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
