package com.example.tagsystemapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
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

    public static ProfilePackage currentProfilePackage = null;

    public static String TOKEN = "";
    public static HashMap<String, String> HEADER = new HashMap<>();


    public static void loadProcesses(ProcessActivity observer){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Process> processesFromDB;
        OrderedRealmCollectionChangeListener<RealmResults<Process>> callback = (results, changeSet) -> {
            if (changeSet == null) {
                // The first time async returns with an null changeSet.
            } else {
                // Called on every update.
                // load processes from database
                if(!results.isEmpty()) {
                    //Toast.makeText(observer, "loading from db...", Toast.LENGTH_SHORT).show();
                    DataHolder.processes = realm.copyFromRealm(results);
                    observer.updateUI();
                    //realm.close();
                }else{
                    //get data from server
                    Toast.makeText(observer, "loading from server...", Toast.LENGTH_SHORT).show();
                    API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
                    Call<List<Process>> call = apiInterface.getProcesses();
                    call.enqueue(new Callback<List<Process>>() {
                        @Override
                        public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                            if(response.code() == 200) {
                                processes = response.body();
                                observer.updateUI();
                                ProcessRepository rep = new ProcessRepository(); // insert processes into database
                                rep.insertList(processes);
                                rep.close();
                            }else{
                                Log.e("Response:", "Response is not successful!");
                                onProcessLoadError(observer);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Process>> call, Throwable t) {
                            t.printStackTrace();
                            onProcessLoadError(observer);
                        }
                    });
                }
            }
            //realm.close();
        };
        // async finding implemented because number of records may be enormous and loading may take some time
        processesFromDB = realm.where(Process.class).findAllAsync();
        processesFromDB.addChangeListener(callback);

    }

    private static void onProcessLoadError(ProcessActivity observer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(observer)
                .setTitle("connection error")
                .setMessage("connecting to server failed")
                .setPositiveButton("reload", (dialogInterface, i) -> {
                    loadProcesses(observer);
                    dialogInterface.dismiss();
                });
        builder.create().show();
    }

    private static void onProfileLoadError(ProfilesActivity observer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(observer)
                .setTitle("connection error")
                .setMessage("connecting to server failed")
                .setPositiveButton("reload", (dialogInterface, i) -> {
                    loadPackageProfile(observer);
                    dialogInterface.dismiss();
                });
        builder.create().show();
    }

    public static void loadPackageProfile(ProfilesActivity activity) {
        if (processes.get(currentProcessIndex).getProfilePackages() != null) {
            try{
                // always one and only one package is in memory and database for a certain process
                currentProfilePackage = processes.get(currentProcessIndex).getProfilePackages().get(0);
                if(Long.valueOf(currentProfilePackage.getExpireDate()) < System.currentTimeMillis())
                    profiles.addAll(currentProfilePackage.getProfiles());
            }catch (IndexOutOfBoundsException|NullPointerException e){
                loadPackageFromServer(activity);
                Log.e("MyException:::", e.getMessage().toString());
            }
        } else {
            loadPackageFromServer(activity);
        }
    }

    private static void loadPackageFromServer(ProfilesActivity activity) {
        API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
        Call<ProfilePackage> call = apiInterface.getPackageProfile(processes.get(currentProcessIndex).getId());
        call.enqueue(new Callback<ProfilePackage>() {
            @Override
            public void onResponse(Call<ProfilePackage> call, Response<ProfilePackage> response) {
                if (response.code() == 200) {
                    currentProfilePackage = response.body();
                    if (currentProfilePackage != null) {
                        profiles.addAll(currentProfilePackage.getProfiles());
                        activity.updateUI();
                        updateDatabase();
                        Toast.makeText(activity, "database is updating...", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Response:::", "null profiles loaded from response!");
                        onProfileLoadError(activity);
                    }
                }else if(response.code() == 404){
                    Toast.makeText(activity, "there is no package exists for this process, removing it...", Toast.LENGTH_LONG).show();
                    new ProcessRepository().deleteCurrent();
                    processes.remove(currentItemIndex);
                    activity.finish();
                } else {
                    Log.i("Response:::", response.toString());
                    onProfileLoadError(activity);
                }
            }

            @Override
            public void onFailure(Call<ProfilePackage> call, Throwable t) {
                Log.e("Response:::", t.toString());
                onProfileLoadError(activity);
            }
        });
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
        ppr.close();
        pr.close();
        tr.close();
        cr.close();
        prc.close();
    }

    public static void initHeaders(){
        HEADER.put("content_type", "application/json");
    }

}
