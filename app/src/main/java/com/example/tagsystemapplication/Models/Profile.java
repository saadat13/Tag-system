package com.example.tagsystemapplication.Models;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject{


    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("is_multi_content")
    private boolean isMultiContent;

    @Ignore
    @SerializedName("content")
    private List<Content> contents;

    @Ignore
    @SerializedName("tag")
    private List<Tag> tags;

    @LinkingObjects("realmProfiles")
    private final RealmResults<ProfilePackage> fromProfiles = null;


    // these two fields is for database not for retrofit

    private RealmList<Content> realmContents;
    private RealmList<Tag> realmTags;

    public Profile(){}



    public List<Content> getContents() {
        return (realmContents != null)? new ArrayList<>(realmContents): contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Tag> getTags() {
        return (realmTags != null)? new ArrayList<>(realmTags): tags;
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
