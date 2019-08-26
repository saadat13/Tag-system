package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutputTag implements Serializable {

    @SerializedName("tag_title")
    @Expose
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