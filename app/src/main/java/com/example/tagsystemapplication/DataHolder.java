package com.example.tagsystemapplication;

import com.example.tagsystemapplication.Objects.ImageObject;
import com.example.tagsystemapplication.Objects.ProcessObject;
import com.example.tagsystemapplication.Objects.Profile;
import com.example.tagsystemapplication.Objects.SystemObject;
import com.example.tagsystemapplication.Objects.TextObject;

import java.util.ArrayList;

public class DataHolder {

    public static int currentProfileIndex = 0;
    public static int currentItemIndex = 0;
    public static int currentProcessIndex=0;


    static String sample = "Once the player has been prepared, playback can be controlled by calling methods on the player. For example setPlayWhenReady starts and pauses playback, the various seekTo methods seek within the media,setRepeatMode controls if and how media is looped, setShuffleModeEnabled controls playlist shuffling, and setPlaybackParameters adjusts playback speed and pitch.\n" +
            "\n" +
            "If the player is bound to a PlayerView or PlayerControlView, then user interaction with these components will cause corresponding methods on the player to be invoked.";

    static String link = "http://icons.iconarchive.com/icons/paomedia/small-n-flat/256/sign-check-icon.png";
    static String vlink = "https://s5.mihanvideo.com/user_contents/videos/icl0tmmwf0ahqzsdz0evt9aociakjfvgswx/37OPwvWlgvDyrE8FhTuo_240p.mp4";

    private static ArrayList<ProcessObject> processes;
    public static ArrayList<Profile> taggedProfiles;

    private static final DataHolder holder = new DataHolder();

    public static void initData(){
        ArrayList<Profile> profiles = new ArrayList<>();
        taggedProfiles = new ArrayList<>();
        processes = new ArrayList<>();
        ArrayList<SystemObject> objects = new ArrayList<>();
        ArrayList<SystemObject> objects1 = new ArrayList<>();
        ArrayList<MyTag> tags = new ArrayList<>();
        ArrayList<MyTag> tags1 = new ArrayList<>();

        for(int i=0; i <10; i++)
            tags.add(new MyTag("tag"));
        for(int i=0; i <10; i++)
            tags1.add(new MyTag("tag1"));
        objects.add(new ImageObject(1, link, "image title 1", tags));
        objects1.add(new TextObject(2, "", "text title 2",sample, tags1));
        profiles.add(new Profile(objects));
        profiles.add(new Profile(objects1));
        processes.add(new ProcessObject("new process 1" ,"other details....", profiles, Constants.TAGGING_METHOD.SINGLE_MODE));
        processes.add(new ProcessObject("new process 2" ,"other details....", profiles, Constants.TAGGING_METHOD.SINGLE_MODE));
        processes.add(new ProcessObject("new process 3" ,"other details....", profiles, Constants.TAGGING_METHOD.SINGLE_MODE));
    }

    public static ArrayList<ProcessObject> getProcesses(){
        if(processes == null)
            initData();
        return processes;
    }


}
