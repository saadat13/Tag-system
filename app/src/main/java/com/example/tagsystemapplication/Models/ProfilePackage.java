package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class ProfilePackage extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("process")
    private int processId;

    @SerializedName("has_next")
    private boolean hasNext;

    @SerializedName("is_tagged")
    private boolean isTagged;

    @SerializedName("is_valid")
    private boolean isValidated;

    @SerializedName("status")
    private String status;

    @SerializedName("expire_date")
    private String expireDate;

    // this field is for retrofit to use
    @Ignore // ignore realm
    @SerializedName("profiles")
    private List<Profile> profiles;

    // for realm to use this
    private RealmList<Profile> realmProfiles = null;

    @LinkingObjects("realmProfilePackages")
    private final RealmResults<Process> fromProfilePackages = null;



    public boolean isTagged() {
        return isTagged;
    }

    public void setTagged(boolean tagged) {
        isTagged = tagged;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
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


    public List<Profile> getProfiles() {
        return (realmProfiles != null)? Realm.getDefaultInstance().copyFromRealm(realmProfiles): profiles;
    }

    public void setProfiles(List<Profile> profiles) {
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
