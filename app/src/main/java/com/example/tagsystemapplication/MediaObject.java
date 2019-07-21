package com.example.tagsystemapplication;

import java.util.ArrayList;

public class MediaObject extends SystemObject{
    private String mediaUrl;
    private String mediaCoverImgUrl;

    public MediaObject(int uId, String title, String mediaUrl,String coverImageUrl ,ArrayList<MyTag> tags) {
        super(uId, title, tags);
        this.mediaUrl = mediaUrl;
        this.mediaCoverImgUrl = coverImageUrl;
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