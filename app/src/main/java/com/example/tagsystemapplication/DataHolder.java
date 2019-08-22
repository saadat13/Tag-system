package com.example.tagsystemapplication;

import android.util.Log;
import android.widget.Toast;

import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.ProfilePackage;
import com.example.tagsystemapplication.Repositories.ContentRepository;
import com.example.tagsystemapplication.Repositories.ProcessRepository;
import com.example.tagsystemapplication.Repositories.ProfilePackageRepository;
import com.example.tagsystemapplication.Repositories.ProfileRepository;
import com.example.tagsystemapplication.Repositories.TagRepository;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataHolder{

    public static int currentProfileIndex = 0;
    public static int currentItemIndex = 0;
    public static int currentProcessIndex=0;


    public static List<Process> processes = new ArrayList<>();

    public  static List<Profile> profiles = new ArrayList<>();
    public static List<Profile> taggedProfiles = new ArrayList<>();

    public static ProfilePackage currentProfilePackage = new ProfilePackage();

    public static String TOKEN = "";
    public static HashMap<String, String> HEADER = new HashMap<>();


    public static void loadProcesses(ProcessActivity observer){
        //TODO first expire time of process must be checked
        // TODO if it not expired then data should be loaded from local database
        // TODO else database should be erased and then data should be retrieved from server and
        //TODO then saved into data base

        ProcessRepository prc = new ProcessRepository();
        RealmResults<Process> processesFromDB = prc.findAll();
//        initHeaders();
        if(processesFromDB.isEmpty()) {
            //get data from server
            API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
            Call<List<Process>> call = apiInterface.getProcesses();
            call.enqueue(new Callback<List<Process>>() {
                @Override
                public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                    if(response.isSuccessful()) {
                        processes = response.body();
                        observer.updateUI();
                    }else{
                        Log.e("Response:", "Response is not successful!");
                    }
                }

                @Override
                public void onFailure(Call<List<Process>> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            //fill database from processes data
            //prc.insertList(processes);
        }else{
            Toast.makeText(observer, "loading from db...", Toast.LENGTH_SHORT).show();
            DataHolder.processes = processesFromDB;
            observer.updateUI();
        }

    }

    public static void loadPackageProfile(ProfilesActivity activity){
        ProfilePackageRepository ppr = new ProfilePackageRepository();
        ArrayList<ProfilePackage> packages = ppr.find(currentProfilePackage.getProcessId());
        if(packages != null){
            if(!packages.isEmpty()){
                for(ProfilePackage p : packages){
                    profiles.addAll(p.getProfiles());
                }
                activity.updateUI();
            }
        }else{
            loadNextPackageProfile(activity ,true);
        }
    }


    public static void loadNextPackageProfile(ProfilesActivity activity,  boolean isFirstTime){
        if(currentProfilePackage.hasNext() || isFirstTime){
            API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
            Call<ProfilePackage> call = apiInterface.getPackageProfile(1, 1);
            call.enqueue(new Callback<ProfilePackage>() {
                @Override
                public void onResponse(Call<ProfilePackage> call, Response<ProfilePackage> response) {
                    if(response.code() == 200){
                        currentProfilePackage = response.body();
                        if(currentProfilePackage!=null) {
                            profiles.addAll(currentProfilePackage.getProfiles());
                            activity.updateUI();
                            updateDatabase();
                        } else
                            Log.e("Response:::", "null profiles loaded from response!");
                    }else{
                        Log.i("Response:::", response.toString());
                    }
                }

                @Override
                public void onFailure(Call<ProfilePackage> call, Throwable t) {
                    Log.e("Response:::", t.toString());
                }
            });
        }
    }

    public static void updateDatabase(){
        // save profiles in database
        ProfilePackageRepository ppr = new ProfilePackageRepository();
        ProfileRepository pr = new ProfileRepository();
        TagRepository tr = new TagRepository();
        ContentRepository cr = new ContentRepository();
        for (Profile p : currentProfilePackage.getProfiles()) {
            tr.insertList(p.getTags());
            cr.insertList(p.getContents());
        }
        pr.insertList(DataHolder.profiles);
        ppr.insert(currentProfilePackage);
        //update processes
        ProcessRepository prc = new ProcessRepository();
        prc.insertList(DataHolder.processes);
        Log.i("MSG:::", "Database has updated successfully!");
    }

    public static void initHeaders(){
        HEADER.put("content_type", "application/json");
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
