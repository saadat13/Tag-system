package com.example.tagsystemapplication.Objects;

import com.example.tagsystemapplication.MyTag;

import java.util.ArrayList;
import java.util.List;

public class SystemObject{
    protected int uId;
    protected String url;
    protected String title;
    protected ArrayList<MyTag> tags = new ArrayList<>();

    public SystemObject(int uId,String url ,String title, ArrayList<MyTag> tags) {
        this.uId = uId;
        this.url = url;
        this.title = title;
        this.tags.addAll(tags);
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTags(ArrayList<MyTag> tags) {
        this.tags = tags;
    }

    public void addTag(MyTag tag){
        tags.add(tag);
    }

    public List<MyTag> getTags() {
        return tags;
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
