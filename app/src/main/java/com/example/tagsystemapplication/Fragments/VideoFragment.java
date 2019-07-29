package com.example.tagsystemapplication.Fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.tagsystemapplication.Adapters.CustomExpandableListAdapter;
import com.example.tagsystemapplication.DataHolder;
import com.example.tagsystemapplication.MyTag;
import com.example.tagsystemapplication.Objects.VideoObject;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoFragment extends Fragment {


    public static VideoObject curObject;

    private ExoPlayer player;
    private PlayerView videoView;
    private ExpandableListView tagList;
    private CustomExpandableListAdapter adapter;

    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<MyTag>> listDataChild = new HashMap<>();


    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

    private TextView title;
    private String mediaUrl;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public VideoFragment() {
    }

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.video_fragment, container, false);
        videoView = rootView.findViewById(R.id.player);
        title = rootView.findViewById(R.id.tvTitle);
        tagList = rootView.findViewById(R.id.tag_list);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int processIndex = DataHolder.currentProcessIndex;
        int itemIndex    = DataHolder.currentItemIndex;
        int profileIndex = DataHolder.currentProfileIndex;
        curObject = (VideoObject) DataHolder.getProcesses().get(processIndex).getProfiles().get(profileIndex).getContents().get(itemIndex);
        if(curObject!=null) {
            title.setText(curObject.getTitle());
            ViewGroup.LayoutParams params = tagList.getLayoutParams();
            params.height += curObject.getTags().size() * 20;
            tagList.setLayoutParams(params);
            tagList.requestLayout();
            listDataHeader.add("Select Tag(s):");
            listDataChild.put(listDataHeader.get(0), curObject.getTags());
            adapter = new CustomExpandableListAdapter(getContext(), listDataHeader, listDataChild);
            tagList.setAdapter(adapter);
            mediaUrl = curObject.getUrl();
            initializePlayer();
        }else{
            Log.e("ERROR:::", "curObject is null!!!");
        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        videoView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(mediaUrl);
        MediaSource mediaSource = buildMediaSource(uri, "");
        player.prepare(mediaSource);
    }

    public void onStart() {
        super.onStart();
        //initializePlayer();
    }


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initializePlayer();
//
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Toast.makeText(getContext(), "on stop", Toast.LENGTH_SHORT).show();
//
//    }

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

    public void releasePlayer() {
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
