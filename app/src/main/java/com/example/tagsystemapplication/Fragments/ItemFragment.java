package com.example.tagsystemapplication.Fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import com.example.tagsystemapplication.DataHolder;
import com.example.tagsystemapplication.Models.Content;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.Tag;
import com.example.tagsystemapplication.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;
import static com.example.tagsystemapplication.DataHolder.currentItemIndex;
import static com.example.tagsystemapplication.DataHolder.currentProcessIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfileIndex;

public class ItemFragment extends Fragment {

    private Content curObject;
    private ExpandableTextView content;
    private TextView title;
    private ImageView imageView;


    private ExoPlayer player;
    private PlayerView videoView;
    private ExpandableListView tagList;
    private CustomExpandableListAdapter adapter;


    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<Tag>> listDataChild = new HashMap<>();


    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           curObject = getArguments().getParcelable("curObject");
        }
    }

    public static ItemFragment newInstance(Content curObject) {
        Bundle args = new Bundle();
        args.putParcelable("curObject", curObject);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }



    //TODO this method should be implemented
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("curObject", curObject);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        if(curObject.getType().equals("text")){
            rootView = inflater.inflate(R.layout.text_fragment, container, false);
            content = rootView.findViewById(R.id.content);
        }else if(curObject.getType().equals("image")){
            rootView = inflater.inflate(R.layout.image_fragment, container, false);
            imageView = rootView.findViewById(R.id.image);
        }else if(curObject.getType().equals("video")){
            rootView  = inflater.inflate(R.layout.video_fragment, container, false);
            videoView = rootView.findViewById(R.id.player);
        }
        title = rootView.findViewById(R.id.tvTitle);
        tagList = rootView.findViewById(R.id.tag_list);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Profile profile = DataHolder.profiles.get(currentProfileIndex);
        title.setText(curObject.getTitle());
        ViewGroup.LayoutParams params = tagList.getLayoutParams();
        params.height += profile.getTags().size() * 30;
        tagList.setLayoutParams(params);
        tagList.requestLayout();
        listDataHeader.add("Select Tag(s):");
        listDataChild.put(listDataHeader.get(0), profile.getTags());
        adapter = new CustomExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        tagList.setAdapter(adapter);
        if(curObject.getType().equals("image"))
            loadImage(imageView);
        else if(curObject.getType().equals("text")){
            content.setText(curObject.getUrl());
        }

    }

    private void loadImage(View view) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        Glide.with(view)
                .asBitmap()
                .transition(withCrossFade(factory))
                .load(curObject.getUrl())
                .placeholder(R.drawable.ic_not_loaded_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        videoView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(curObject.getUrl());
        MediaSource mediaSource = buildMediaSource(uri, "");
        player.prepare(mediaSource);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(videoView != null)
            initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                DataSource.Factory dataSourceFactory =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "app-name"));
// Create a SmoothStreaming media source pointing to a manifest uri.
                new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_DASH:
                DataSource.Factory dataSourceFactory1 =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "app-name"));
// Create a DASH media source pointing to a DASH manifest uri.
                return new DashMediaSource.Factory(dataSourceFactory1)
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                DataSource.Factory dataSourceFactory2 =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "app-name"));
// Create a HLS media source pointing to a playlist uri.
                return new HlsMediaSource.Factory(dataSourceFactory2).createMediaSource(uri);
            case C.TYPE_OTHER:
                DataSource.Factory dataSourceFactory3 =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "app-name"));
// Create a progressive media source pointing to a stream uri.
                return new ProgressiveMediaSource.Factory(dataSourceFactory3)
                        .createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }
    }


}
