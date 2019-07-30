package com.example.tagsystemapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.tagsystemapplication.Adapters.ItemRecyclerAdapter;
import com.example.tagsystemapplication.Objects.Profile;

import java.util.ArrayList;


public class SummaryActivity extends AppCompatActivity {


    private RecyclerView rv;
    private ItemRecyclerAdapter adapter;
    private ArrayList<Profile> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profiles = DataHolder.taggedProfiles;
        setContentView(R.layout.activity_summary);
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new ItemRecyclerAdapter(this , profiles);
        rv.setAdapter(adapter);
    }

}