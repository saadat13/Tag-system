package com.example.tagsystemapplication.Repositories;

import android.util.Log;
import com.example.tagsystemapplication.Models.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileRepository {
    private Realm realm;

    public ProfileRepository(){
        realm = Realm.getDefaultInstance();
    }

    public void insert(final Profile profile){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Profile realmObject = realm.createObject(Profile.class, profile.getId());
                realmObject.setMultiContent(profile.isMultiContent());
                realmObject.setTagged(profile.isTagged());
                realmObject.setValidated(profile.isValidated());
                realmObject.setContents(profile.getContents());
                realmObject.setTags(profile.getTags());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", profile.toString() + " has successfully added to database");
            }
        });
    }


    public void insertList(final List<Profile> profiles){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
               realm.copyToRealmOrUpdate(profiles);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", Arrays.toString(profiles.toArray()) + " has successfully added to database");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("REALM_TAG", "Error in adding profiles to database");
                Log.e("REALM_TAG", error.getMessage().toString());
            }
        });
    }


    public ArrayList<Profile> findAll(){
        RealmResults<Profile> profiles = realm.where(Profile.class).findAll();
        ArrayList<Profile> list = new ArrayList<>(realm.copyFromRealm(profiles));
        return list;
    }


}
