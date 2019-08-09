package com.example.tagsystemapplication.Models;

import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Content extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String url;
    private String title;
    private String type;

    public Content(){}

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return String.format("content %d, title: %s", id, title);
    }
}
