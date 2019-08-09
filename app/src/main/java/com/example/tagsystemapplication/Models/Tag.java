package com.example.tagsystemapplication.Models;

import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String title;
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