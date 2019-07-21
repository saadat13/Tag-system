package com.example.tagsystemapplication;

import java.util.ArrayList;

public class ImageObject extends SystemObject{

    private String imageUrl;

    public ImageObject(int uId, String title, String imageUrl, ArrayList<MyTag> tags) {
        super(uId, title, tags);
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return imageUrl;
    }

    public void setUrl(String mUrl) {
        this.imageUrl = mUrl;
    }

}