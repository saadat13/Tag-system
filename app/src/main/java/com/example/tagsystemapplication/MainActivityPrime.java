package com.example.tagsystemapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Morris on 03,June,2019
 */
public class MainActivityPrime extends AppCompatActivity {

    RecyclerView mRecyclerView;

    private ArrayList<TextObject> mediaObjectList = new ArrayList<>();
    private TextRecyclerAdapter mAdapter;
    private boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prime);
        initView();
        // Prepare demo content
//        prepareImageList();

        //set data object

//        mRecyclerView = findViewById(R.id.image_rv);
//        mAdapter = new TextRecyclerAdapter(mediaObjectList, initGlide());
//
//        //Set Adapter
//        mRecyclerView.setAdapter(mAdapter);

//        if (firstTime) {
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    mRecyclerView.playVideo(false);
//                }
//            });
//            firstTime = false;
//        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.image_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();
        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    protected void onDestroy() {
//        if (mRecyclerView != null) {
//            mRecyclerView.releasePlayer();
//        }
        super.onDestroy();
    }

//    private void prepareImageList() {
//        String link = "http://icons.iconarchive.com/icons/paomedia/small-n-flat/256/sign-check-icon.png";
//        TextObject mediaObject = new TextObject();
//        mediaObject.setId(1);
//        mediaObject.setTags(new String[]{"tag1", "tag2", "tag3", "tag4"});
//        mediaObject.setTitle(
//                "Do you think the concept of marriage will no longer exist in the future?");
//        mediaObjectList.add(mediaObject);
//        mediaObjectList.add(mediaObject);
//        mediaObjectList.add(mediaObject);
//        mediaObjectList.add(mediaObject);
//
//    }

//        private void prepareVideoList() {
//        String vlink = "https://s5.mihanvideo.com/user_contents/videos/icl0tmmwf0ahqzsdz0evt9aociakjfvgswx/37OPwvWlgvDyrE8FhTuo_240p.mp4";
//        MediaObject mediaObject = new MediaObject();
//        mediaObject.setId(1);
//        mediaObject.setTags(new String[]{"tag1", "tag2", "tag3", "tag4"});
//        mediaObject.setTitle(
//                "Do you think the concept of marriage will no longer exist in the future?");
//        mediaObject.setCoverUrl(
//                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-1.png");
//        mediaObject.setUrl(vlink);
//
////        MediaObject mediaObject2 = new MediaObject();
////        mediaObject2.setId(2);
////        mediaObject2.setUserHandle("@hardik.patel");
////        mediaObject2.setTitle(
////                "If my future husband doesn't cook food as good as my mother should I scold him?");
////        mediaObject2.setCoverUrl(
////                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-2.png");
////        mediaObject2.setUrl("https://androidwave.com/media/androidwave-video-2.mp4");
////
////        MediaObject mediaObject3 = new MediaObject();
////        mediaObject3.setId(3);
////        mediaObject3.setUserHandle("@arun.gandhi");
////        mediaObject3.setTitle("Give your opinion about the Ayodhya temple controversy.");
////        mediaObject3.setCoverUrl(
////                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-3.png");
////        mediaObject3.setUrl("https://androidwave.com/media/androidwave-video-3.mp4");
////
////        MediaObject mediaObject4 = new MediaObject();
////        mediaObject4.setId(4);
////        mediaObject4.setUserHandle("@sachin.patel");
////        mediaObject4.setTitle("When did kama founders find sex offensive to Indian traditions");
////        mediaObject4.setCoverUrl(
////                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-4.png");
////        mediaObject4.setUrl("https://androidwave.com/media/androidwave-video-6.mp4");
////
////        MediaObject mediaObject5 = new MediaObject();
////        mediaObject5.setId(5);
////        mediaObject5.setUserHandle("@monika.sharma");
////        mediaObject5.setTitle("When did you last cry in front of someone?");
////        mediaObject5.setCoverUrl(
////                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-5.png");
////        mediaObject5.setUrl("https://androidwave.com/media/androidwave-video-5.mp4");
//
//        mediaObjectList.add(mediaObject);
////        mediaObjectList.add(mediaObject2);
////        mediaObjectList.add(mediaObject3);
////        mediaObjectList.add(mediaObject4);
////        mediaObjectList.add(mediaObject5);
////        mediaObjectList.add(mediaObject);
////        mediaObjectList.add(mediaObject2);
////        mediaObjectList.add(mediaObject3);
////        mediaObjectList.add(mediaObject4);
////        mediaObjectList.add(mediaObject5);
//    }
}