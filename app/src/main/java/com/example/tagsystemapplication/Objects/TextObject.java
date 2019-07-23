package com.example.tagsystemapplication.Objects;

import com.example.tagsystemapplication.MyTag;

import java.util.ArrayList;

public class TextObject extends SystemObject {
    private String strContent;


    public TextObject(int uId, String title, String strContent, ArrayList<MyTag> tags) {
        super(uId, title, tags);
        this.strContent = strContent;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }




}