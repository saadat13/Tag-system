package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Process extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("number_of_profiles")
    private int numberOfProfiles;
    @SerializedName("tagging_method")
    private String tagMethod;
    @SerializedName("details")
    private String otherDetails;

    private RealmList<ProfilePackage> realmProfilePackages = null;

    @Ignore
    private List<ProfilePackage> profilePackages;


    // including handling on database mode and server mode
    public List<ProfilePackage> getProfilePackages() {
        return (realmProfilePackages != null)? new ArrayList<>(realmProfilePackages): profilePackages;
    }

    public void setProfilePackages(List<ProfilePackage> profilePackages) {
        this.profilePackages = profilePackages;
    }

    public Process() {}


    public RealmList<ProfilePackage> getRealmProfilePackages() {
        return realmProfilePackages;
    }

    public void setRealmProfilePackages(RealmList<ProfilePackage> profiles) {
        this.realmProfilePackages = profiles;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Process)) return false;
        Process other = (Process)obj;
        return other.id == this.id &&
                other.numberOfProfiles == this.numberOfProfiles &&
                other.otherDetails.equals(this.otherDetails) &&
                other.tagMethod.equals(this.tagMethod) &&
                other.title.equals(this.title);
    }
}
