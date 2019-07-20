package com.example.tagsystemapplication;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public enum ROLE{EXPERT, FULL_EXPERT, MANAGER, UNDEFINDED}
    public enum TYPE{IMAGE, VIDEO, TEXT}

    public static List<SpinnerItem> getSpinnerTags(String[] tags){
        List<SpinnerItem> items = new ArrayList<>();
        items.add(new SpinnerItem("Select tag:"));
        for(int i=0; i<tags.length; i++){
            items.add(new SpinnerItem(tags[i]));
        }
        return items;
    }

}
