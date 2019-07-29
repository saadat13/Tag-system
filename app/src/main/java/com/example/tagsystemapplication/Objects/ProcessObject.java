package com.example.tagsystemapplication.Objects;
import com.example.tagsystemapplication.Constants;

import java.util.ArrayList;

public class ProcessObject {
    private String title;
    private String comments;
    private ArrayList<Profile> profiles;
    private Constants.TAGGING_METHOD taggingMethod;

    public ProcessObject(String title, String comments, ArrayList<Profile> profiles, Constants.TAGGING_METHOD tagging_method){
        this.title = title;
        this.comments = comments;
        this.profiles = profiles;
        this.taggingMethod = tagging_method;
    }

    public Constants.TAGGING_METHOD getTaggingMethod() {
        return taggingMethod;
    }

    public void setTaggingMethod(Constants.TAGGING_METHOD taggingMethod) {
        this.taggingMethod = taggingMethod;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public String getDetails(){
        return "Details:\n" + "Number of profiles: " + profiles.size() + "\n" + comments;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
