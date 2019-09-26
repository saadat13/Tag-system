package com.example.tagsystemapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter2;
import com.example.tagsystemapplication.Models.DB;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Repositories.ProcessRepository;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tagsystemapplication.DataHolder.isConnectedToInternet;
import static com.example.tagsystemapplication.DataHolder.processes;

public class ProcessActivity extends AppCompatActivity {

    ProcessRecyclerAdapter2 adapter;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_load_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        rv = findViewById(R.id.list);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        tv_load_error = findViewById(R.id.tv_load_error);
        DB.initDB(this);
        setupRecycler();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv_load_error.setVisibility(View.GONE);
                loadProcesses();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

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
            //get data from server
            Toast.makeText(this, "loading from server...", Toast.LENGTH_SHORT).show();
            API_Interface apiInterface = API_Client.getAuthorizedClient().create(API_Interface.class);
            Call<List<Process>> call = apiInterface.getProcesses();
            call.enqueue(new Callback<List<Process>>() {
                @Override
                public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                    if (response.code() == 200) {
                        processes = response.body();
                        Log.wtf("Tag:::", processes.size() + " ");
                        updateUI();
                        //update database ...
                        ProcessRepository rep = new ProcessRepository(); // insert processes into database
                        rep.deleteAll(); // delete old records
                        rep.insertList(processes);
                        rep.close();
                    }else if(response.code() == 401){
                        DataHolder.reinitHeaders(ProcessActivity.this);
                        Toast.makeText(ProcessActivity.this, "reinitializing headers", Toast.LENGTH_SHORT).show();
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
        }else{
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
                        Toast.makeText(this, "loading from db...", Toast.LENGTH_SHORT).show();
                        processes = realm.copyFromRealm(results);
                        updateUI();
                        //realm.close();
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
        tv_load_error.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        loadProcesses();
    }

    public void updateUI() {
        swipeRefreshLayout.setRefreshing(false);
        rv.setVisibility(View.VISIBLE);
        setupRecycler();
    }
}
