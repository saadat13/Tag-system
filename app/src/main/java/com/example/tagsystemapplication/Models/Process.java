package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Process extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("tag_method")
    private String tagMethod;

    @SerializedName("number_of_profiles")
    private int numberOfProfiles;

    @SerializedName("expert_user")
    private String expertUser;

    @Ignore
    @SerializedName("tags")
    private List<Tag> tags;


    private RealmList<Tag> realmTags1;
    private RealmList<Profile> realmProfiles;


    public Process() {}

    public RealmList<Profile> getRealmProfiles() {
        return realmProfiles;
    }

    public void setRealmProfiles(RealmList<Profile> realmProfiles) {
        this.realmProfiles = realmProfiles;
    }

    public String getExpertUser() {
        return expertUser;
    }

    public void setExpertUser(String expertUser) {
        this.expertUser = expertUser;
    }

    public List<Tag> getTags() {
//        if(tags == null){
//            setTags(getRealmTags());
//        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        setRealmTags(new RealmList<>(tags.toArray(new Tag[0])));
//        realm.commitTransaction();
    }

    public RealmList<Tag> getRealmTags() {
        return realmTags1;
    }

    public void setRealmTags(RealmList<Tag> realmTags) {
        this.realmTags1 = realmTags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfProfiles() {
        return numberOfProfiles;
    }

    public void setNumberOfProfiles(int numberOfProfiles) {
        this.numberOfProfiles = numberOfProfiles;
    }

    public String getTagMethod() {
        return tagMethod;
    }

    public void setTagMethod(String tagMethod) {
        this.tagMethod = tagMethod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int hashCode() {
        return this.id;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Process)) return false;
        Process other = (Process)obj;
        return this.id == other.id;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("process %d, title: %s", id, title);
    }

}
