package com.example.tagsystemapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter;
import com.example.tagsystemapplication.Models.DB;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import static com.example.tagsystemapplication.DataHolder.taggedProfiles;
import static com.example.tagsystemapplication.DataHolder.untaggedProfiles;

public class ProcessActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProcessRecyclerAdapter adapter;
    private RecyclerView rv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_load_error;
    private DrawerLayout mDrawerLayout;
    private NavigationView nav;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        rv = findViewById(R.id.list);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        tv_load_error = findViewById(R.id.tv_load_error);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        nav.setNavigationItemSelectedListener(this);
        DB.initDB(this);
        setupRecycler();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv_load_error.setVisibility(View.GONE);
                triggerLoadingProcess();
            }
        });

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
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
        adapter = new ProcessRecyclerAdapter(this);
        rv.setAdapter(adapter);
    }

//    public void loadProcesses() {
//        //first step : loading from database
//        triggerLoadingProcess();
//        //second step : get data from server and merge with database data
//
////        if(processes.size() == 0)
////            tv_load_error.setVisibility(View.VISIBLE);
////        triggerLoadingProcess();
//    }

    private void triggerLoadingProcess() {
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
                }
                if(isConnectedToInternet(this))
                    getProcessFromServer();
            }
//                //realm.close();
        };
        // async finding implemented because number of records may be enormous and loading may take some time
        processesFromDB = realm.where(Process.class).findAllAsync();
        processesFromDB.addChangeListener(callback);
    }

    private void getProcessFromServer() {
        Toast.makeText(this, "loading from server...", Toast.LENGTH_SHORT).show();
        API_Interface apiInterface = API_Client.getAuthorizedClient(this).create(API_Interface.class);
        Call<List<Process>> call = apiInterface.getProcesses();
        call.enqueue(new Callback<List<Process>>() {
            @Override
            public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<Process> pFromServer = response.body();
                        for (Process p : pFromServer) {
                            if(!processes.contains(p))
                                processes.add(p);

                        }
//                        tv_load_error.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        rv.setVisibility(View.VISIBLE);
                        rv.invalidate();

                }else if(response.code() == 401){
                    String refreshToken = getSharedPreferences("info", MODE_PRIVATE).getString("refresh", "");
                    if(!refreshToken.isEmpty()) {
                        DataHolder.reinitHeaders(ProcessActivity.this, refreshToken);
                        Toast.makeText(ProcessActivity.this, "reinitializing headers", Toast.LENGTH_SHORT).show();
                        getProcessFromServer();
                    }else{
                        startActivity(new Intent(ProcessActivity.this, SignInActivity.class));
                        ProcessActivity.this.finish();
                    }
                } else if(response.code() == 404 ){
                    Toast.makeText(ProcessActivity.this, "there is no process available", Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("Response:", "");
                    Log.e("PROCESS:::", "Response is not successful! response code: " + response.code());
                    onProcessLoadError();
                }
            }

            @Override
            public void onFailure(Call<List<Process>> call, Throwable t) {
                t.printStackTrace();
                Log.e("PROCESS:::", "on failure process load!!!");
                onProcessLoadError();
            }
        });

    }

    private void onProcessLoadError() {
//        tv_load_error.setVisibility(View.VISIBLE);
//        rv.setVisibility(View.GONE);
        Toast.makeText(this, "Loading from server failed", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        triggerLoadingProcess();
    }

    public void updateUI() {
        swipeRefreshLayout.setRefreshing(false);
        rv.setVisibility(View.VISIBLE);
        setupRecycler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.user_info:
                break;
            case R.id.settings:
                break;
            case R.id.logout:
                startActivity(new Intent(ProcessActivity.this, SignInActivity.class));
                this.finish();
                break;
        }
        return true;
    }
}
