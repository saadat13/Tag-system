package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

public class User extends RealmObject {
    @SerializedName("username")
    private String username;

    @LinkingObjects("realmUsers")
    private final RealmResults<Tag> fromUsers = null;


    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return username;
    }
}
