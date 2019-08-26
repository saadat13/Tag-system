package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.SkipCallbackExecutor;

public class Output implements Serializable {
    @SerializedName("process_id")
    private int processId;

    @SerializedName("profile_package_id")
    private int profilePackageId;

    @SerializedName("profile_id")
    private int profileId;

    @SerializedName("tags")
    @Expose
    private List<OutputTag> tags;

    public Output(){}

    public Output(int processId, int profilePackageId, int profileId, ArrayList<OutputTag> tags){
        this.processId = processId;
        this.profilePackageId = profilePackageId;
        this.profileId = profileId;
        this.tags = tags;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getProfilePackageId() {
        return profilePackageId;
    }

    public void setProfilePackageId(int profilePackageId) {
        this.profilePackageId = profilePackageId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public List<OutputTag> getTags() {
        return tags;
    }

    public void setTags(List<OutputTag> tags) {
        this.tags = tags;
    }
}
