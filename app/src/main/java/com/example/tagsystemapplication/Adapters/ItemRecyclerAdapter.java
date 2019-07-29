package com.example.tagsystemapplication.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.tagsystemapplication.ChecklistActivity;
import com.example.tagsystemapplication.Objects.SystemObject;
import com.example.tagsystemapplication.Objects.TextObject;
import com.example.tagsystemapplication.Objects.Profile;
import com.example.tagsystemapplication.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Profile> items;
    private ChecklistActivity activity;

    public ItemRecyclerAdapter(ChecklistActivity activity, ArrayList<Profile> items) {
        this.items = items;
        this.activity = activity;
        Log.i("TAG:::", items.get(0).getContents().size() + " ");
    }

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

        void onBind(Profile profile) {
            this.profile = profile;
            parent.setTag(this);
            parent.setOnClickListener(this);
            boolean isMultiContent = profile.getContents().size() > 1;
            SystemObject firstObject = profile.getContents().get(0);
            if(isMultiContent)
                title.setText("multi content");
            else
                title.setText(firstObject.getTitle());
            for(int i = 0; i< firstObject.getTags().size() ; i++){
                Chip chip = new Chip(parent.getContext());
                chip.setText("#" + firstObject.getTitle());
                chip.setClickable(true);
                chip.setCheckable(true);
                chip.setChecked(firstObject.getTags().get(i).isChecked());
                hashtags.addView(chip);
            }
            loadImage(firstObject);
        }

        private void loadImage(SystemObject mediaObject) {
            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            if(profile.getContents().size() > 1){
                Glide.with(parent)
                        .asBitmap()
                        .transition(withCrossFade(factory))
                        .load(mediaObject.getUrl())
                        .placeholder(R.drawable.ic_multicontent)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image);
            }else{
                if(!(mediaObject instanceof TextObject)) {
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