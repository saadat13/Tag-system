package com.example.tagsystemapplication.Objects;


public class Content {
    private int id;
    private String url;
    private String title;
    private String type;

    public Content(int id, String url , String title, String type) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.type = type;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
