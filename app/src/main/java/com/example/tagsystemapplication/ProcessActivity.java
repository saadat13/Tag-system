package com.example.tagsystemapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter2;
import com.example.tagsystemapplication.Models.DB;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Repositories.ProcessRepository;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;
import com.google.android.material.navigation.NavigationView;

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

import static com.example.tagsystemapplication.DataHolder.USER_RESPONSE;
import static com.example.tagsystemapplication.DataHolder.isConnectedToInternet;
import static com.example.tagsystemapplication.DataHolder.processes;

public class ProcessActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProcessRecyclerAdapter2 adapter;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_load_error;
    private DrawerLayout mDrawerLayout;
    NavigationView nav;
    ActionBarDrawerToggle toggle;


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
                loadProcesses();
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
        adapter = new ProcessRecyclerAdapter2(this, processes);
        rv.setAdapter(adapter);
    }

    public void loadProcesses() {
        if (isConnectedToInternet(this)) {
            //get data from server
            Toast.makeText(this, "loading from server...", Toast.LENGTH_SHORT).show();
            API_Interface apiInterface = API_Client.getAuthorizedClient(this).create(API_Interface.class);
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
                        String refreshToken = getPreferences(MODE_PRIVATE).getString("refresh", "");
                        if(!refreshToken.isEmpty()) {
                            DataHolder.reinitHeaders(ProcessActivity.this, refreshToken);
                            Toast.makeText(ProcessActivity.this, "reinitializing headers", Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(ProcessActivity.this, SignInActivity.class));
                            ProcessActivity.this.finish();
                        }
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
                        //realm.close();
                    }
                    updateUI();
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
