package com.example.tagsystemapplication;

import android.annotation.SuppressLint;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.recyclerview.widget.RecyclerView;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * below view have public modifier because
     * we have access PlayerViewHolder inside the ExoPlayerRecyclerView
     */
    public FrameLayout mediaContainer;
    public ImageView image;
    public ProgressBar progressBar;
    public RequestManager requestManager;
    private TextView title;
    private View parent;
    private Spinner spinner;
    private ImageButton options;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        mediaContainer = itemView.findViewById(R.id.mediaContainer);
        image = itemView.findViewById(R.id.ivMediaCoverImage);
        title = itemView.findViewById(R.id.tvTitle);
        spinner = itemView.findViewById(R.id.spinner);
        progressBar = itemView.findViewById(R.id.progressBar);
        options = itemView.findViewById(R.id.imageButton);
    }

    void onBind(ImageObject mediaObject, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        title.setText(mediaObject.getTitle());
        options.setOnClickListener(this);
        spinner.setAdapter(new MySpinnerAdapter(parent.getContext(), R.layout.spinner_item, Constants.getSpinnerTags(mediaObject.getTags())));
        loadImage(mediaObject);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent.getContext(), "image is reloading...", Toast.LENGTH_SHORT).show();
                loadImage(mediaObject);
            }
        });
    }



    @SuppressLint("CheckResult")
    private void loadImage(ImageObject mediaObject) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        this.requestManager
                .asBitmap()
                .transition(withCrossFade(factory))
                .load(mediaObject.getUrl())
                .placeholder(R.drawable.ic_not_loaded_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }

    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.my_item_options, popup.getMenu());
        popup.show();
    }

}