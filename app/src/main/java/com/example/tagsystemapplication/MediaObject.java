package com.example.tagsystemapplication;

import java.util.ArrayList;
import java.util.List;

public class MediaObject {
    private int uId;
    private String title;
    private String mediaUrl;
    private String mediaCoverImgUrl;
    private String[] tags;

    public MediaObject(int uId, String title, String mediaUrl,String coverImageUrl ,String[] tags) {
        this.uId = uId;
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.tags = tags;
        this.mediaCoverImgUrl = coverImageUrl;
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
        return mediaUrl;
    }

    public void setUrl(String mUrl) {
        this.mediaUrl = mUrl;
    }

    public String getCoverUrl() {
        return mediaCoverImgUrl;
    }

    public void setCoverUrl(String mCoverUrl) {
        this.mediaCoverImgUrl = mCoverUrl;
    }
}