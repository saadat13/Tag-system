package com.example.tagsystemapplication.Adapters;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.tagsystemapplication.Objects.VideoObject;
import com.example.tagsystemapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.tagsystemapplication.Constants.showOptions;


public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * below view have public modifier because
     * we have access VideoViewHolder inside the ExoPlayerRecyclerView
     */
    public FrameLayout mediaContainer;
    public ImageView mediaCoverImage, volumeControl;
    public ProgressBar progressBar;
    public RequestManager requestManager;
    private TextView title;
    private View parent;
//    private Spinner spinner;
    private ImageButton options;
    private VideoObject object;
    private MySpinnerAdapter adapter;


    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        mediaContainer = itemView.findViewById(R.id.mediaContainer);
        mediaCoverImage = itemView.findViewById(R.id.content);
        title = itemView.findViewById(R.id.tvTitle);
//        spinner = itemView.findViewById(R.id.spinner);
        progressBar = itemView.findViewById(R.id.progressBar);
        volumeControl = itemView.findViewById(R.id.ivVolumeControl);
        options = itemView.findViewById(R.id.imageButton);
        options.setOnClickListener(this);
    }

    void onBind(VideoObject mediaObject, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        this.object = mediaObject;
        title.setText(mediaObject.getTitle());
        adapter = new MySpinnerAdapter(parent.getContext(), R.layout.tag_item, mediaObject.getTags());
//        spinner.setAdapter(adapter);
        this.requestManager
                .load(mediaObject.getCoverUrl())
                .into(mediaCoverImage);
    }

//    private void refreshTagList(){
//        adapter = new MySpinnerAdapter(parent.getContext(), R.layout.tag_item, object.getTags());
//        spinner.setAdapter(adapter);
//    }

    @Override
    public void onClick(View view) {
//        showOptions(view, object);
//        refreshTagList();
    }




}