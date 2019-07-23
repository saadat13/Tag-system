package com.example.tagsystemapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

import com.example.tagsystemapplication.ListSlides.ImageListSlide;
import com.example.tagsystemapplication.ListSlides.TextListSlide;
import com.example.tagsystemapplication.ListSlides.VideoListSlide;
import com.example.tagsystemapplication.Objects.ImageObject;
import com.example.tagsystemapplication.Objects.VideoObject;
import com.example.tagsystemapplication.Objects.TextObject;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity{


    String sample = "Once the player has been prepared, playback can be controlled by calling methods on the player. For example setPlayWhenReady starts and pauses playback, the various seekTo methods seek within the media,setRepeatMode controls if and how media is looped, setShuffleModeEnabled controls playlist shuffling, and setPlaybackParameters adjusts playback speed and pitch.\n" +
            "\n" +
            "If the player is bound to a PlayerView or PlayerControlView, then user interaction with these components will cause corresponding methods on the player to be invoked.";

    String link = "http://icons.iconarchive.com/icons/paomedia/small-n-flat/256/sign-check-icon.png";
    String vlink = "https://s5.mihanvideo.com/user_contents/videos/icl0tmmwf0ahqzsdz0evt9aociakjfvgswx/37OPwvWlgvDyrE8FhTuo_240p.mp4";
    ViewPager viewPager;
    TabLayout tabLayout;
    SlideAdapter adapter;

    public static  ArrayList<TextObject> tobjects = new ArrayList<>();
    public static  ArrayList<VideoObject> mobjects = new ArrayList<>();
    public static  ArrayList<ImageObject> iobjects = new ArrayList<>();

    TextListSlide textSlide;
    VideoListSlide videoSlide;
    ImageListSlide imageSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<MyTag> sampleTags = new ArrayList<>();
        sampleTags.add(new MyTag("tag1"));
        sampleTags.add(new MyTag("tag2"));

        mobjects.add(new VideoObject(1, "title", vlink ,vlink,sampleTags));
        mobjects.add(new VideoObject(2, "title", vlink ,vlink,sampleTags));
        mobjects.add(new VideoObject(3, "title", vlink ,vlink,sampleTags));

        iobjects.add(new ImageObject(1, "image title", link, sampleTags));
        iobjects.add(new ImageObject(2, "image title", link, sampleTags));
        iobjects.add(new ImageObject(3, "image title", link, sampleTags));

        tobjects.add(new TextObject(1, "text title", sample, sampleTags));
        tobjects.add(new TextObject(2, "text title", sample, sampleTags));
        tobjects.add(new TextObject(3, "text title", sample, sampleTags));

        viewPager = findViewById(R.id.pager);
        adapter = new SlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        textSlide  = TextListSlide.newInstance(tobjects);
        videoSlide = VideoListSlide.newInstance(mobjects);
        imageSlide = ImageListSlide.newInstance(iobjects);

        textSlide.setRetainInstance(true);
        videoSlide.setRetainInstance(true);
        imageSlide.setRetainInstance(true);

        tabLayout.getTabAt(0).setText("Text");
        tabLayout.getTabAt(1).setText("Video");
        tabLayout.getTabAt(2).setText("Image");

        viewPager.setOffscreenPageLimit(3);
//        prepareTextList();

    }


    class SlideAdapter extends FragmentPagerAdapter{

        public SlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            return VideoListSlide.newInstance(mobjects);
            switch (position){
                case 0:
                    return textSlide;
                case 1:
                    return videoSlide;
                case 2:
                    return imageSlide;
            }
            return textSlide;
        }

        @Override
        public int getCount() {
            return 3; // 3 now
        }
    }

}