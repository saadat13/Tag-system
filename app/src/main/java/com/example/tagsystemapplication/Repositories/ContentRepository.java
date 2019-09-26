package com.example.tagsystemapplication.Repositories;

import android.util.Log;
import com.example.tagsystemapplication.Models.Content;
import java.util.Arrays;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class ContentRepository {
    private Realm realm;

    public ContentRepository(){
        realm = Realm.getDefaultInstance();
    }

    public void insert(final Content content){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(content);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", content.toString() + " has successfully added to database");
            }
        });
    }

    public void insertList(final List<Content> contents){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(contents);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", Arrays.toString(contents.toArray())+ " has successfully added to database");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("REALM_TAG", "Error in adding contents to database");
                Log.e("REALM_TAG", error.getMessage().toString());
            }
        });
    }

    public RealmResults<Content> findAll(){
        RealmResults<Content> Contents = realm.where(Content.class).findAll();
        return Contents;
    }

    public void close(){
        if(realm == null) return;
        realm.close();
    }


}
