package com.example.tagsystemapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject implements Cloneable {

//    @SerializedName("id")
////    @PrimaryKey
//    private int id;


    @SerializedName("title")
    private String title;

    @SerializedName("is_checked")
    private boolean isChecked;


    @LinkingObjects("realmTags1")
    private final RealmResults<Process> fromRealmTags1 = null;


    @LinkingObjects("realmTags2")
    private final RealmResults<Profile> fromRealmTags2 = null;


    public Tag(){}

    public Tag(boolean isChecked , String title){
//        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
    }

    public static List<Tag> cloneList(List<Tag> list) {
        List<Tag> clone = new ArrayList<Tag>(list.size());
        for (Tag item : list) {
            clone.add(new Tag(item.isChecked, item.getTitle()));
        }
        return clone;
    }


//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public RealmResults<Process> getFromRealmTags1() {
        return fromRealmTags1;
    }

    public RealmResults<Profile> getFromRealmTags2() {
        return fromRealmTags2;
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
        return String.format("title: %s", title);
    }

//    @Override
//    public int hashCode() {
//        return this.id;
//    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Tag)) return false;
        Tag other = (Tag)obj;
        return other.title.equals(this.title);
    }
}