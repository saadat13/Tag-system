package com.example.tagsystemapplication;

public class ImageObject {
    private int uId;
    private String title;
    private String imageUrl;
    private String[] tags;

    public ImageObject(int uId, String title, String imageUrl, String[] tags) {
        this.uId = uId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.tags = tags;
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

    public String getUrl() {
        return imageUrl;
    }

    public void setUrl(String mUrl) {
        this.imageUrl = mUrl;
    }

}