package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Process extends RealmObject implements Serializable {

    @SerializedName("id")
    @PrimaryKey
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("number_of_profiles")
    private int numberOfProfiles;
    @SerializedName("tag_method")
    private String tagMethod;
    @SerializedName("other_details")
    private String otherDetails;
    @SerializedName("profiles")
    private RealmList<ProfilePackage> profilePackages;

    public Process() {}

    public RealmList<ProfilePackage> getProfilePackages() {
        return profilePackages;
    }



    public void setProfiles(RealmList<ProfilePackage> profiles) {
        this.profilePackages = profiles;
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
