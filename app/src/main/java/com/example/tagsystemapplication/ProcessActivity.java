package com.example.tagsystemapplication;

import android.app.Activity;
import android.os.Bundle;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter;
import com.example.tagsystemapplication.Objects.Process;
import com.example.tagsystemapplication.Objects.Profile;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProcessActivity extends Activity {

    private ArrayList<Process> processes;
    private  ArrayList<Profile> profiles;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        DataHolder.initProcess(this);
        processes = DataHolder.getProcesses(this);
        rv = findViewById(R.id.list);
        setupRecycler();
    }

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
        setupRecycler();
    }
}
