package com.example.tagsystemapplication;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Repositories.ContentRepository;
import com.example.tagsystemapplication.Repositories.ProcessRepository;
import com.example.tagsystemapplication.Repositories.ProfileRepository;
import com.example.tagsystemapplication.Repositories.TagRepository;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;
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
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataHolder extends Observable{

    public static int currentProfileIndex = 0;
    public static int currentItemIndex = 0;
    public static int currentProcessIndex=0;


    public static RealmResults<Process> processes;
    public  static List<Profile> profiles;
    public static List<Profile> taggedProfiles = new ArrayList<>();

    public static String TOKEN = "";
    public static HashMap<String, String> HEADER = new HashMap<>();

    /**
     * A method for loading process objects from server and save them into database
     * @param context
     */
    public static void loadProcesses(Context context){
        //TODO first expire time of process must be checked
        // TODO if it not expired then data should be loaded from local database
        // TODO else database should be erased and then data should be retrieved from server and
        //TODO then saved into data base

        ProcessRepository prc = new ProcessRepository();
        RealmResults<Process> processesFromDB = prc.findAll();
        //if(processesFromDB == null) {
            //get data from server
            API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
            Call<RealmList<Process>> call = apiInterface.getProcesses(HEADER);
            call.enqueue(new Callback<RealmList<Process>>() {
                @Override
                public void onResponse(Call<RealmList<Process>> call, Response<RealmList<Process>> response) {
                    if(response.isSuccessful()){
                        Log.e("RESPONSE:", response.message());
                    }else{
                        Log.e("RESPONSE:", response.message());
                    }
                }

                @Override
                public void onFailure(Call<RealmList<Process>> call, Throwable t) {

                }
            });

            // fill database from processes data
            //prc.insertList(processes);
//        }else{
//            DataHolder.processes = processesFromDB;
//        }

    }

//    public static void loadProfiles(Context context){
//        //TODO first profiles must be retrieved from server and save into database
//        //TODO if have expired, else there is no need to do that only data loads from database
//        ProfileRepository re = new ProfileRepository();
//        List<Profile> profilesFromDB = re.findAll();
//        if(profilesFromDB == null) {
//            InputStream raw = context.getResources().openRawResource(R.raw.multi_profile);
//            Reader rd = new BufferedReader(new InputStreamReader(raw));
//            JsonArray data = new JsonParser().parse(rd).getAsJsonObject().getAsJsonArray("profiles");
//            Type listType = new TypeToken<List<Profile>>() {
//            }.getType();
//            DataHolder.profiles = new Gson().fromJson(data, listType);
//
//            // save profiles in database
//            TagRepository tr = new TagRepository();
//            ContentRepository cr = new ContentRepository();
//            for (Profile p : profiles) {
//                tr.insertList(p.getTags());
//                cr.insertList(p.getContents());
//            }
//            re.insertList(DataHolder.profiles);
//            //update processes
//            for (int i = 0; i < processes.size(); i++) {
//                DataHolder.processes.get(i).setProfiles(new RealmList<>(profiles.get(i)));
//            }
//            ProcessRepository prc = new ProcessRepository();
//            prc.insertList(DataHolder.processes);
//        }else{
//            DataHolder.profiles = profilesFromDB;
//        }
//
//    }

    public static void initHeaders(){

    }

    //public static List<Profile> getProfiles(){
//        return profiles;
//    }

//            InputStream raw = context.getResources().openRawResource(R.raw.process);
//            Reader rd = new BufferedReader(new InputStreamReader(raw));
//
//            // convert json to process objects
//            JsonArray data = new JsonParser().parse(rd).getAsJsonObject().getAsJsonArray("processes");
//            Type listType = new TypeToken<List<Process>>() {}.getType();
//            DataHolder.processes = new Gson().fromJson(data, listType);
}
