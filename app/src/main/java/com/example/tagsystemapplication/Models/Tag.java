package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("is_valid")
    private boolean isChecked;
    @SerializedName("percent")
    private int percent;


    @LinkingObjects("realmTags")
    private final RealmResults<Profile> fromRealmTags = null;


    @Ignore
    @SerializedName("users")
    private List<User> users;

    private RealmList<User> realmUsers;

    int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Tag(){}


    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public List<User> getUsers() {
        return (realmUsers !=null)? new ArrayList<>(realmUsers) : users;
    }



    public void setUsers(List<User> users) {
        this.users = users;
    }

    public RealmList<User> getRealmUsers() {
        return realmUsers;
    }

    public void setRealmUsers(RealmList<User> realmUsers) {
        this.realmUsers = realmUsers;
    }

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