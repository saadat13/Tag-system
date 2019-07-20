package com.example.tagsystemapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<TextObject> textObjects;

    public TextRecyclerAdapter(ArrayList<TextObject> textObjects) {
        this.textObjects = textObjects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TextViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.text_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((TextViewHolder) viewHolder).onBind(textObjects.get(i));
    }

    @Override
    public int getItemCount() {
        return textObjects.size();
    }
}