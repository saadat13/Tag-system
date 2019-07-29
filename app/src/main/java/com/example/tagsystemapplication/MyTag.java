package com.example.tagsystemapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTag{
    private String title;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public MyTag(String title)

    {
        this.title = title;
    }

    public MyTag(Parcel in) {
        title = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}