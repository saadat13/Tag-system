package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

public class OutputTag {

    @SerializedName("title")
    private String tagTitle;

    public OutputTag(){}

    public String getName() {
        return tagTitle;
    }

    public void setName(String name) {
        this.tagTitle = name;
    }

    public OutputTag(String name){
        this.tagTitle = name;
    }
}