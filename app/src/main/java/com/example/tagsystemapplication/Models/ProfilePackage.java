package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class ProfilePackage {
    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("has_next")
    private boolean hasNext;

    @SerializedName("profiles")
    private RealmList<Profile> profiles;

    public ProfilePackage(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public RealmList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(RealmList<Profile> profiles) {
        this.profiles = profiles;
    }
}
