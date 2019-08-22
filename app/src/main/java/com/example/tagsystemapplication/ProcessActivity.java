package com.example.tagsystemapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter2;
import com.example.tagsystemapplication.Models.DB;
import com.example.tagsystemapplication.Models.Process;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProcessActivity extends AppCompatActivity {

    private List<Process> processes=new ArrayList<>();
    ProcessRecyclerAdapter2 adapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        rv = findViewById(R.id.list);
        DB.initDB(this);
        setupRecycler();
        DataHolder.loadProcesses(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        //setupRecycler();
    }

    public void updateUI() {
        processes = DataHolder.processes;
        setupRecycler();
    }
}
