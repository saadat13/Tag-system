package com.example.tagsystemapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter;
import com.example.tagsystemapplication.Models.DB;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Repositories.ProcessRepository;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;

public class ProcessActivity extends AppCompatActivity {

    private RealmResults<Process> processes;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        rv = findViewById(R.id.list);
        DB.initDB(this);
        DataHolder.loadProcesses(this);
        //DataHolder.loadProfiles(this);
        processes = DataHolder.processes;
        setupRecycler();
    }

    @SuppressLint("StaticFieldLeak")
    private void setupRecycler() {
        rv.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        ProcessRecyclerAdapter adapter = new ProcessRecyclerAdapter(this, processes);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setupRecycler();
    }


}
