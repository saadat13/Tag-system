package com.example.tagsystemapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoPlayActivity extends AppCompatActivity {

    ExoPlayer player;
    PlayerView videoView;
    String address;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        videoView = findViewById(R.id.player);
        if(getIntent().getExtras() != null) {
            address = getIntent().getExtras().getString("address");
        }
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
        videoView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(address);
        MediaSource mediaSource = buildMediaSource(uri, "");
        player.prepare(mediaSource);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        releasePlayer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //First Hide other mobjects (listview or recyclerview), better hide them using Gone.
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            videoView.setLayoutParams(params);
            VideoPlayActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //unhide your mobjects here.
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            videoView.setLayoutParams(params);
            VideoPlayActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }

    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                DataSource.Factory dataSourceFactory =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getApplicationContext(), "app-name"));
// Create a SmoothStreaming media source pointing to a manifest uri.
                new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_DASH:
                DataSource.Factory dataSourceFactory1 =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getApplicationContext(), "app-name"));
// Create a DASH media source pointing to a DASH manifest uri.
                return new DashMediaSource.Factory(dataSourceFactory1)
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                DataSource.Factory dataSourceFactory2 =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getApplicationContext(), "app-name"));
// Create a HLS media source pointing to a playlist uri.
                return new HlsMediaSource.Factory(dataSourceFactory2).createMediaSource(uri);
            case C.TYPE_OTHER:
                DataSource.Factory dataSourceFactory3 =
                        new DefaultHttpDataSourceFactory(Util.getUserAgent(getApplicationContext(), "app-name"));
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
