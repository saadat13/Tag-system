package com.example.tagsystemapplication.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.tagsystemapplication.Adapters.CustomExpandableListAdapter;
import com.example.tagsystemapplication.MainActivity;
import com.example.tagsystemapplication.MainActivityPrime;
import com.example.tagsystemapplication.MyTag;
import com.example.tagsystemapplication.Objects.ImageObject;
import com.example.tagsystemapplication.Objects.SystemObject;
import com.example.tagsystemapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class ImageFragment extends Fragment {


    public static ImageObject curObject;
    private TextView title;
    private ImageView image;
    private ExpandableListView tagList;

    private CustomExpandableListAdapter adapter;
    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<MyTag>> listDataChild = new HashMap<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("Assert")
    public ImageFragment() {
        SystemObject object =  MainActivityPrime.items.get(MainActivityPrime.currentItemIndex);
        if(object instanceof ImageObject) {
            curObject = (ImageObject) object;
        }else{
            Log.wtf("TAG:::",("none image fragment passed!!!"));
        }
    }

    public static TextFragment newInstance() {
        return new TextFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_fragment, container, false);
        title = rootView.findViewById(R.id.tvTitle);
        image = rootView.findViewById(R.id.image);
        tagList = rootView.findViewById(R.id.tag_list);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(curObject.getTitle());
        loadImage(curObject, view);
        ViewGroup.LayoutParams params = tagList.getLayoutParams();
        params.height += curObject.getTags().size() * 20;
        tagList.setLayoutParams(params);
        tagList.requestLayout();
        listDataHeader.add("Select Tag(s):");
        listDataChild.put(listDataHeader.get(0), curObject.getTags());
        adapter = new CustomExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        tagList.setAdapter(adapter);
    }

    private void loadImage(ImageObject mediaObject, View view) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        Glide.with(view)
                .asBitmap()
                .transition(withCrossFade(factory))
                .load(mediaObject.getUrl())
                .placeholder(R.drawable.ic_not_loaded_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }
}
