package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Output {
    @SerializedName("process_id")
    private int processId;

    @SerializedName("profile_id")
    private int profileId;


    @SerializedName("tags")
    private List<OutputTag> tags;

    public Output(){}

    public Output(int processId, int profileId, ArrayList<OutputTag> tags){
        this.processId = processId;
        this.profileId = profileId;
        this.tags = tags;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
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
