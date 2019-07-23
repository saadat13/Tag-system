package com.example.tagsystemapplication.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.tagsystemapplication.Objects.VideoObject;
import com.example.tagsystemapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<VideoObject> mediaObjects;
    private RequestManager requestManager;

    public VideoRecyclerAdapter(ArrayList<VideoObject> mediaObjects,
                                RequestManager requestManager) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.video_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoViewHolder) viewHolder).onBind(mediaObjects.get(i), requestManager);
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }
}