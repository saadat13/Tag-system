package com.example.tagsystemapplication;

import java.util.ArrayList;

public  class SystemObject {
    private int uId;
    private String title;
    private ArrayList<MyTag> tags = new ArrayList<>();

    public SystemObject(int uId, String title, ArrayList<MyTag> tags) {
        this.uId = uId;
        this.title = title;
        this.tags.addAll(tags);
    }

    public void addTag(MyTag tag){
        tags.add(tag);
    }

    public ArrayList<MyTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<MyTag> tags) {
        this.tags = tags;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
