package com.example.tagsystemapplication.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.tagsystemapplication.Objects.ImageObject;
import com.example.tagsystemapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ImageObject> imageObjects;
    private RequestManager requestManager;

    public ImageRecyclerAdapter(ArrayList<ImageObject> imageObjects,
                                RequestManager requestManager) {
        this.imageObjects = imageObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.image_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ImageViewHolder) viewHolder).onBind(imageObjects.get(i), requestManager);
    }

    @Override
    public int getItemCount() {
        return imageObjects.size();
    }
}