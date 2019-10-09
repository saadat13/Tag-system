package com.example.tagsystemapplication.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.Content;
import com.example.tagsystemapplication.R;
import com.google.android.material.chip.Chip;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Profile> items;
//    private SummaryActivity activity;
//
//    public ItemRecyclerAdapter(SummaryActivity activity, List<Profile> items) {
//        this.items = items;
//        this.activity = activity;
////        Log.i("TAG:::", items.get(0).getContents().size() + " ");
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.tagged_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ItemViewHolder) viewHolder).onBind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private TextView title;
        private View parent;
        private LinearLayout hashtags;
        private Profile profile;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            image = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.item_title);
            hashtags = itemView.findViewById(R.id.hashtags);
        }

        @SuppressLint("ClickableViewAccessibility")
        void onBind(Profile profile) {
            this.profile = profile;
            parent.setTag(this);
            parent.setOnClickListener(this);
            Content firstObject = profile.getContents().get(0);
            boolean isMultiContent = profile.getContents().size() > 1;
            if(isMultiContent)
                title.setText("multi content");
            else
                title.setText(firstObject.getTitle());
            for(int i = 0; i< profile.getTags().size() ; i++){
                Chip chip = new Chip(parent.getContext());
                chip.setText("#" + profile.getTags().get(i).getTitle());
                chip.setClickable(true);
                chip.setCheckable(true);
                chip.setChecked(profile.getTags().get(i).isChecked());
                hashtags.addView(chip);
            }
            loadImage(firstObject);
        }



        private void loadImage(Content mediaObject) {
            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            if(profile.getContents().size() > 1){
                Glide.with(parent)
                        .asBitmap()
                        .transition(withCrossFade(factory))
                        .load(R.drawable.ic_multicontent)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image);
            }else{
                if(!(mediaObject.getType().equals("text"))) {
                    Glide.with(parent)
                            .asBitmap()
                            .transition(withCrossFade(factory))
                            .load(mediaObject.getUrl())
                            .placeholder(R.drawable.ic_not_loaded_img)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(image);
                }else{
                    Glide.with(parent)
                            .asBitmap()
                            .transition(withCrossFade(factory))
                            .load(R.drawable.ic_text)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(image);
                }

            }
        }

        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(activity , ProfilesActivity.class);
//            intent.putExtra("processIndex", currentProcessIndex);
//            activity.startActivity(intent);
           // activity.finish();
        }
    }
}