package com.example.tagsystemapplication;

import android.util.Log;


import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DBRepository <T extends RealmObject> {
    private Realm realm;
    private Class<T> tClass;

    public DBRepository(Class<T> tClass){
        realm = Realm.getDefaultInstance();
        this.tClass = tClass;
    }

    public void insert(final T T){
        realm.executeTransactionAsync(realm -> realm.insertOrUpdate(T),
                () -> Log.i("REALM_TAG", T.toString() + " has successfully inserted or updated in database"));
    }

    public void insertList(final List<T> Ts){
        realm.executeTransactionAsync(realm -> realm.insertOrUpdate(Ts),
                () -> Log.i("REALM_TAG", Arrays.toString(Ts.toArray())+ " has successfully added to database"),
                error -> {
            Log.e("REALM_TAG", "Error in adding Ts to database");
            Log.e("REALM_TAG", error.getMessage());
        });
    }

    public RealmResults<T> findAll(){
        RealmResults<T> Ts = realm.where(tClass).findAll();
        return Ts;
    }



    public void deleteAll(){
        // obtain the results of a query
        final RealmResults<T> results = realm.where(tClass).findAll();
        // All changes to data must happen in a transaction
        realm.executeTransaction(realm -> {
            // Delete all matches
            results.deleteAllFromRealm();
        }
        );
    }

    public T find(int id){
        return realm.where(tClass).equalTo("id", id).findFirst();
    }

    public void clearDB(){
        realm.executeTransactionAsync(realm -> {
            realm.deleteAll();
        });
    }

    public void delete(int id){
        RealmResults<Process> result = realm
                .where(Process.class)
                .equalTo("id", id)
                .findAll();
        realm.executeTransaction(realm -> result.deleteFirstFromRealm());
    }

    public void close(){
        if(realm == null) return;
        realm.close();
    }
}
