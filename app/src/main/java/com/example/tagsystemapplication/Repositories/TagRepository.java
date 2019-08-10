package com.example.tagsystemapplication.Repositories;
import android.util.Log;
import com.example.tagsystemapplication.Models.Tag;
import java.util.Arrays;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class TagRepository {
    private Realm realm;

    public TagRepository(){
        realm = Realm.getDefaultInstance();
    }

    public void insert(final Tag tag){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Tag realmObject = realm.createObject(Tag.class, tag.getId());
                realmObject.setTitle(tag.getTitle());
                realmObject.setChecked(tag.isChecked());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("REALM_TAG", tag.toString() + " has successfully added to database");
            }
        });
    }

    public void insertList(final List<Tag> tags){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(tags);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                    Log.i("REALM_TAG", Arrays.toString(tags.toArray()) + " has successfully added to database");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("REALM_TAG", "Error in adding tags to database");
                Log.e("REALM_TAG", error.getMessage().toString());
            }
        });
    }

    public RealmResults<Tag> findAll(){
        RealmResults<Tag> tags = realm.where(Tag.class).findAll();
        return tags;
    }

    public void close(){
        if(realm == null) return;
        realm.close();
    }



}
