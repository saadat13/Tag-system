package com.example.tagsystemapplication.Objects;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.table.DatabaseTable;

public class Process {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    public String title;

    @SerializedName("other_details")
    public String otherDetails;

    @SerializedName("number_of_profiles")
    public int numberOfProfiles;

    @SerializedName("tagging_method")
    public String tagMethod;


    public Process(String title, String otherDetails, int numberOfProfiles, String tagging_method){
        this.title = title;
        this.otherDetails = otherDetails;
        this.numberOfProfiles = numberOfProfiles;
        this.tagMethod = tagging_method;
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

    public String getTaggingMethod() {
        return tagMethod;
    }

    public void setTaggingMethod(String taggingMethod) {
        this.tagMethod = taggingMethod;
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

}
