package com.example.tagsystemapplication;

public class TextObject {
    private int uId;
    private String title;
    private String strContent;
    private String[] tags;

    public TextObject(int uId, String title, String strContent, String[] tags) {
        this.uId = uId;
        this.title = title;
        this.strContent = strContent;
        this.tags = tags;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int getId() {
        return uId;
    }

    public void setId(int uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }


}