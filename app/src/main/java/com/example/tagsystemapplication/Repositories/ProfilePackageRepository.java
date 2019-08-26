package com.example.tagsystemapplication.Repositories;

import android.util.Log;

import com.example.tagsystemapplication.Models.Profile;
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

    public void delete(int id){
        RealmResults<ProfilePackage> profilePackage = realm.where(ProfilePackage.class).equalTo("id", id).findAll();
        realm.executeTransaction((realm)->{
            profilePackage.deleteFirstFromRealm();
        });
    }

    public ArrayList<ProfilePackage> find(int processId){
        Process process = realm.where(Process.class)
                .equalTo("id", processId).findFirst();
        return new ArrayList<>(realm.copyFromRealm(process.getProfilePackages()));
    }

    public void close(){
        if(realm == null) return;
        realm.close();
    }


}
