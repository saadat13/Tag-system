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
    @SerializedName("tags")
    private List<Tag> tags;


    private boolean isTagged;


    // this field is for database not for retrofit

    private RealmList<Content> realmContents;
    private RealmList<Tag> realmTags2;


    @LinkingObjects("realmProfiles")
    private final RealmResults<Process> fromRealmProfiles=  null;

    public boolean isTagged() {
        return isTagged;
    }

    public Profile(){}


    public void setTagged(boolean tagged) {
        isTagged = tagged;
    }

    public List<Tag> getTags() {
        if(tags == null){
            setTags(getRealmTags2());
        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        setRealmTags2(new RealmList<>(tags.toArray(new Tag[0])));
//        realm.commitTransaction();
    }


    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public RealmList<Content> getRealmContents() {
        return realmContents;
    }

    public void setRealmContents(RealmList<Content> realmContents) {
        this.realmContents = realmContents;
    }

    public RealmList<Tag> getRealmTags2() {
        return realmTags2;
    }

    public void setRealmTags2(RealmList<Tag> realmTags2) {
        this.realmTags2 = realmTags2;
    }

    public RealmResults<Process> getFromRealmProfiles() {
        return fromRealmProfiles;
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
