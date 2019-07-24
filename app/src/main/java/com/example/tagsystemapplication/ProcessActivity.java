package com.example.tagsystemapplication;

import android.app.Activity;
import android.os.Bundle;

import com.example.tagsystemapplication.Adapters.ProcessRecyclerAdapter;
import com.example.tagsystemapplication.Objects.ProcessObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProcessActivity extends Activity {

    RecyclerView rv;
    ArrayList<ProcessObject> processes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);


        for(int i =0; i< 40; i++)
            processes.add(new ProcessObject("new process " + (i+1) ,"other details....",3 , Constants.TAGGING_METHOD.SINGLE_MODE));


        rv = findViewById(R.id.list);
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

    }
}
