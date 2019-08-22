package com.example.tagsystemapplication.Repositories;

import android.util.Log;

import com.example.tagsystemapplication.Models.ProfilePackage;

import java.util.ArrayList;
import com.example.tagsystemapplication.Models.Process;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ProfilePackageRepository {
    private Realm realm;

    public ProfilePackageRepository(){
        realm = Realm.getDefaultInstance();
    }

    public void insert(final ProfilePackage ppackage){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(ppackage);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", ppackage.toString() + " has successfully added to database");
            }
        });
    }


    public ArrayList<ProfilePackage> find(int processId){
        Process process = realm.where(Process.class)
                .equalTo("id", processId).findFirst();
        if(process!=null){
            return new ArrayList<>(realm.copyFromRealm(process.getProfilePackages()));
        }
        return null;
    }

    public void close(){
        if(realm == null) return;
        realm.close();
    }


}
