package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Output {
    @SerializedName("id")
    private int id;
    @SerializedName("tags")
    private ArrayList<OutputTag> tags;

    public Output(){
        tags = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<OutputTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<OutputTag> tags) {
        this.tags = tags;
    }
}
