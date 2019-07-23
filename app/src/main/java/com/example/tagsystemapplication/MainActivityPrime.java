package com.example.tagsystemapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tagsystemapplication.Adapters.CustomExpandableListAdapter;
import com.example.tagsystemapplication.Objects.ImageObject;
import com.example.tagsystemapplication.Objects.SystemObject;
import com.example.tagsystemapplication.Objects.TextObject;
import com.example.tagsystemapplication.Objects.VideoObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivityPrime extends AppCompatActivity implements View.OnClickListener {

    String sample = "Once the player has been prepared, playback can be controlled by calling methods on the player. For example setPlayWhenReady starts and pauses playback, the various seekTo methods seek within the media,setRepeatMode controls if and how media is looped, setShuffleModeEnabled controls playlist shuffling, and setPlaybackParameters adjusts playback speed and pitch.\n" +
            "\n" +
            "If the player is bound to a PlayerView or PlayerControlView, then user interaction with these components will cause corresponding methods on the player to be invoked.";

    String link = "http://icons.iconarchive.com/icons/paomedia/small-n-flat/256/sign-check-icon.png";
    String vlink = "https://s5.mihanvideo.com/user_contents/videos/icl0tmmwf0ahqzsdz0evt9aociakjfvgswx/37OPwvWlgvDyrE8FhTuo_240p.mp4";


    public static ArrayList<SystemObject> items = new ArrayList<>();
    public static int currentItemIndex = 0;

    private ImageButton next;
    private ImageButton back;
    private ImageButton first;
    private ImageButton last;
    private ImageButton ok;
    private NavHostFragment navHostFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<MyTag> sampleTags = new ArrayList<>();
        sampleTags.add(new MyTag("tag1"));
        sampleTags.add(new MyTag("tag2"));
        sampleTags.add(new MyTag("tag2"));
        sampleTags.add(new MyTag("tag2"));
        sampleTags.add(new MyTag("tag2"));

        items.add(new TextObject(1, "text title", sample + sample + sample, sampleTags));
        items.add(new ImageObject(2, "text title", link, sampleTags));
        items.add(new VideoObject(3, "text title", vlink, vlink, sampleTags));

        setContentView(R.layout.activity_main_prime);

        navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);



        next  = findViewById(R.id.next);
        back  = findViewById(R.id.back);
        first = findViewById(R.id.first);
        last  = findViewById(R.id.last);
        ok    = findViewById(R.id.ok);

        next.setOnClickListener(this);
        back.setOnClickListener(this);
        first.setOnClickListener(this);
        last.setOnClickListener(this);
        ok.setOnClickListener(this);




//        items.add(new VideoObject(2, "text title", vlink,vlink, sampleTags));
//        items.add(new ImageObject(3, "text title", link, sampleTags));



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back.performClick();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                if(currentItemIndex +1 < items.size()){
                    SystemObject curItem = items.get(++currentItemIndex);
                    if (curItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (curItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else if(curItem instanceof VideoObject) {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
//                    Toast.makeText(this, "next pressed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                if(currentItemIndex -1 >= 0){
                    SystemObject curItem = items.get(--currentItemIndex);
                    if (curItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (curItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else /*if(first instanceof VideoObject)*/ {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
//                    Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show();
                }else{
                    finish();
                }
                break;
            case R.id.first:
                if(items.size() > 0) {
                    currentItemIndex = 0;
                    SystemObject firstItem = items.get(currentItemIndex);
                    if (firstItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (firstItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else /*if(first instanceof VideoObject)*/ {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
//                    Toast.makeText(this, "first pressed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.last:
                if(items.size() > 0) {
                    currentItemIndex = items.size() - 1;
                    SystemObject lastItem = items.get(currentItemIndex);
                    if (lastItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (lastItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else /*if(first instanceof VideoObject)*/ {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
//                    Toast.makeText(this, "last pressed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ok:
                break;
        }
    }
}