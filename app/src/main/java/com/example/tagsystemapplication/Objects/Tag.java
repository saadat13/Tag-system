package com.example.tagsystemapplication.Objects;

public class Tag{
    private String title;
    private boolean isChecked;

    public Tag(String title)
    {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}