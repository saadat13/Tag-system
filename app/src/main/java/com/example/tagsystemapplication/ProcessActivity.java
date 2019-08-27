package com.example.tagsystemapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter2;
import com.example.tagsystemapplication.Models.DB;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Repositories.ProcessRepository;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tagsystemapplication.DataHolder.USER_RESPONSE;
import static com.example.tagsystemapplication.DataHolder.isConnectedToInternet;
import static com.example.tagsystemapplication.DataHolder.processes;

public class ProcessActivity extends AppCompatActivity {

    ProcessRecyclerAdapter2 adapter;
    RecyclerView rv;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        rv = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        DB.initDB(this);
        setupRecycler();
//        loadProcesses();
    }

    @SuppressLint("StaticFieldLeak")
    private void setupRecycler() {
        rv.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new ProcessRecyclerAdapter2(this, processes);
        rv.setAdapter(adapter);
    }

    public void loadProcesses() {
        if (isConnectedToInternet(this)) {
            Realm realm = Realm.getDefaultInstance();
            //async finding implemented because number of records may be enormous and loading may take some time
            RealmResults<Process> processesFromDB;
            OrderedRealmCollectionChangeListener<RealmResults<Process>> callback = (results, changeSet) -> {
                if (changeSet == null) {
                    // The first time async returns with an null changeSet.
                } else {
                    // Called on every update.
                    // load processes from database
                    if (!results.isEmpty()) {
                        //Toast.makeText(observer, "loading from db...", Toast.LENGTH_SHORT).show();
                        processes = realm.copyFromRealm(results);
                        updateUI();
                        //realm.close();
                    } else {
                        //get data from server
                        Toast.makeText(this, "loading from server...", Toast.LENGTH_SHORT).show();
                        API_Interface apiInterface = API_Client.getAuthorizedClient().create(API_Interface.class);
                        Call<List<Process>> call = apiInterface.getProcesses();
                        call.enqueue(new Callback<List<Process>>() {
                            @Override
                            public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                                if (response.code() == 200) {
                                    processes = response.body();
                                    updateUI();
                                    ProcessRepository rep = new ProcessRepository(); // insert processes into database
                                    rep.insertList(processes);
                                    rep.close();
                                } else {
                                    Log.e("Response:", "Response is not successful!");
                                    onProcessLoadError();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Process>> call, Throwable t) {
                                t.printStackTrace();
                                onProcessLoadError();
                            }
                        });
                    }
                }
//                //realm.close();
            };
            // async finding implemented because number of records may be enormous and loading may take some time
            processesFromDB = realm.where(Process.class).findAllAsync();
            processesFromDB.addChangeListener(callback);
        }
    }

    private void onProcessLoadError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("connection error")
                .setMessage("connecting to server failed")
                .setPositiveButton("reload", (dialogInterface, i) -> {
                    loadProcesses();
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Exit", (dialogInterface, i) -> {
                    this.finish();
                });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProcesses();
    }

    public void updateUI() {
        progressBar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        setupRecycler();
    }
}
