package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ProfilePackage extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("process")
    private int processId;

    @SerializedName("has_next")
    private boolean hasNext;

    // this field is for retrofit to use
    @Ignore // ignore realm
    @SerializedName("profiles")
    private ArrayList<Profile> profiles;

    // for realm to use this
    private RealmList<Profile> realmProfiles;

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    public RealmList<Profile> getRealmProfiles() {
        return realmProfiles;
    }

    public void setRealmProfiles(RealmList<Profile> realmProfiles) {
        this.realmProfiles = realmProfiles;
    }

    public ProfilePackage(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }


    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public boolean isHasNext() {
        return hasNext;
    }


    @NonNull
    @Override
    public String toString() {
        return String.format("profile package %d", id);
    }
}
