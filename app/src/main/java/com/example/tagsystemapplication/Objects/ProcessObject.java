package com.example.tagsystemapplication.Objects;

import com.example.tagsystemapplication.Constants.TAGGING_METHOD;

public class ProcessObject {
    private String title;
    private TAGGING_METHOD taggingMethod;
    private String comments;
    private int numberOfProfiles;

    public ProcessObject(String title, String comments, int numberOfProfiles, TAGGING_METHOD taggingMethod){
        this.title = title;
        this.comments = comments;
        this.taggingMethod = taggingMethod;
        this.numberOfProfiles = numberOfProfiles;
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
        return "Details:\n" + "Tagging method: " +
                ((taggingMethod == TAGGING_METHOD.SINGLE_MODE)? "Single mode" : "Batch mode")
                + "\n" + "Number of profiles: " + numberOfProfiles + "\n" + comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
