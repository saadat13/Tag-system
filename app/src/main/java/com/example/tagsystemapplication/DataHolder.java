package com.example.tagsystemapplication;

import android.content.Context;

import com.example.tagsystemapplication.Objects.Process;
import com.example.tagsystemapplication.Objects.Profile;
import com.example.tagsystemapplication.Objects.ProfileRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
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

    private static ArrayList<Process> processes;
    public static ArrayList<Profile> profiles;
    public static ArrayList<Profile> taggedProfiles = new ArrayList<>();


    private static final DataHolder holder = new DataHolder();

    public static void initProcess(Context context){
        InputStream raw =  context.getResources().openRawResource(R.raw.process);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        JsonArray data = new JsonParser().parse(rd).getAsJsonObject().getAsJsonArray("processes");
        Type listType = new TypeToken<ArrayList<Process>>(){}.getType();
        processes = new Gson().fromJson(data, listType);
    }

    public static void initProfiles(Context context, int n){
        switch (n){
            case 0:
                InputStream raw =  context.getResources().openRawResource(R.raw.s1);
                Reader rd = new BufferedReader(new InputStreamReader(raw));
                ProfileRequest req = new Gson().fromJson(rd, ProfileRequest.class);
                profiles  = req.getProfiles();
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    public static ArrayList<Profile> getProfiles(Context context, int n){
        if(profiles == null)
            initProfiles(context, n);
        return profiles;
    }


    public static ArrayList<Process> getProcesses(Context context){
        if(processes == null)
            initProcess(context);
        return processes;
    }


}
