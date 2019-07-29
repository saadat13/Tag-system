package com.example.tagsystemapplication.Objects;

import com.example.tagsystemapplication.MyTag;

import java.util.ArrayList;

public class VideoObject extends SystemObject {
    public VideoObject(int uId, String mediaUrl, String coverImageUrl,String title , ArrayList<MyTag> tags) {
        super(uId,mediaUrl,title , tags);
    }
}