package com.example.tagsystemapplication.Models;

import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Process extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String title;
    private int numberOfProfiles;
    private String tagMethod;
    private String otherDetails;
    private RealmList<Profile> profiles;

    public RealmList<Profile> getProfiles() {
        return profiles;
    }

    public Process() {}


    public void setProfiles(RealmList<Profile> profiles) {
        this.profiles = profiles;
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

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("process %d, title: %s", id, title);
    }
}
