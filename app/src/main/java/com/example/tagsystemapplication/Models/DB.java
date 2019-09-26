package com.example.tagsystemapplication.Models;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DB {
    private static String DB_NAME = "myDB.realm";
    private static File DB_PATH = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "tag_db");
    public static void initDB(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .directory(DB_PATH)
                .name(DB_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
