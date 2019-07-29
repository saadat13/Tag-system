package com.example.tagsystemapplication.Objects;

import com.example.tagsystemapplication.MyTag;

import java.util.ArrayList;

public class ImageObject extends SystemObject {

    public ImageObject(int uId,String url ,String title, ArrayList<MyTag> tags) {
        super(uId,url ,title, tags);
    }
}