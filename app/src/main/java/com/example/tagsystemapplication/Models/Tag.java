package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject implements Serializable {

    @SerializedName("id")
    @PrimaryKey
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("is_checked")
    private boolean isChecked;

    public Tag(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("tag %d, title: %s", id, title);
    }
}