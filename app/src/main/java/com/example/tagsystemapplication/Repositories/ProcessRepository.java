package com.example.tagsystemapplication.Repositories;

import android.util.Log;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.ProfilePackage;

import java.util.Arrays;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.tagsystemapplication.DataHolder.currentProcessIndex;
import static com.example.tagsystemapplication.DataHolder.processes;

public class ProcessRepository {
    private Realm realm;

    public ProcessRepository(){
        realm = Realm.getDefaultInstance();
    }

    public void insert(final Process process){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Process realmObject = realm.createObject(Process.class, process.getId());
                realmObject.setTitle(process.getTitle());
                realmObject.setTagMethod(process.getTagMethod());
                realmObject.setNumberOfProfiles(process.getNumberOfProfiles());
                realmObject.setOtherDetails(process.getOtherDetails());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", process.toString() + " has successfully added to database");
            }
        });
    }

    public void deleteCurrent(){
        RealmResults<Process> result = realm
                .where(Process.class)
                .equalTo("id", processes.get(currentProcessIndex).getId())
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFirstFromRealm();
            }
        });
    }

    public void insertListFromJson(final String json){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(Process.class, json);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", json + " has successfully added to database");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("REALM_TAG", "Error in adding processes to database");
                Log.e("REALM_TAG", error.getMessage().toString());
            }
        });
    }

    public void insertList(final List<Process> processes){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(processes);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", Arrays.toString(processes.toArray()) + " has successfully added to database");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("REALM_TAG", "Error in adding processes to database");
                Log.e("REALM_TAG", error.getMessage().toString());
            }
        });
    }

    public RealmResults<Process> findAll(){
        RealmResults<Process> processes = realm.where(Process.class).findAll();
        return processes;
    }

    public void close(){
        if(realm == null) return;
        realm.close();
    }




}
