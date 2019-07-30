package com.example.tagsystemapplication.Objects;

import java.util.ArrayList;

public class ProfileRequest {
    private boolean hasNext;
    private ArrayList<Profile> profiles;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }
}
