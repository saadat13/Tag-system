package com.example.tagsystemapplication.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tagsystemapplication.Objects.ProcessObject;
import com.example.tagsystemapplication.ProcessActivity;
import com.example.tagsystemapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProcessRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ProcessObject> processObjects;
    private ProcessActivity activity;

    public ProcessRecyclerAdapter(ProcessActivity activity, ArrayList<ProcessObject> processObjects) {
        this.processObjects = processObjects;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProcessViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.process_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ProcessViewHolder) viewHolder).onBind(activity, processObjects.get(i), i);
    }

    @Override
    public int getItemCount() {
        return processObjects.size();
    }
}