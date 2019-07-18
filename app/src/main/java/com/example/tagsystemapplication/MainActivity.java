package com.example.tagsystemapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import static com.example.tagsystemapplication.Constants.EXPERT;
import static com.example.tagsystemapplication.Constants.FULL_EXPERT;
import static com.example.tagsystemapplication.Constants.IMAGE;
import static com.example.tagsystemapplication.Constants.MANAGER;
import static com.example.tagsystemapplication.Constants.STRING;
import static com.example.tagsystemapplication.Constants.UNDEFINED;
import static com.example.tagsystemapplication.Constants.VIDEO;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button btn_next;
    private Button btn_previous;
    private Button btn_submit;
    private Button btn_final_submit;
    private FloatingActionButton fab;

    static int USER_TYPE = UNDEFINED;

    String sample = "Once the player has been prepared, playback can be controlled by calling methods on the player. For example setPlayWhenReady starts and pauses playback, the various seekTo methods seek within the media,setRepeatMode controls if and how media is looped, setShuffleModeEnabled controls playlist shuffling, and setPlaybackParameters adjusts playback speed and pitch.\n" +
            "\n" +
            "If the player is bound to a PlayerView or PlayerControlView, then user interaction with these components will cause corresponding methods on the player to be invoked.";

    String link = "http://icons.iconarchive.com/icons/paomedia/small-n-flat/256/sign-check-icon.png";
    String vlink = "https://s5.mihanvideo.com/user_contents/videos/icl0tmmwf0ahqzsdz0evt9aociakjfvgswx/37OPwvWlgvDyrE8FhTuo_240p.mp4";
    ViewPager viewPager;
    SlideAdapter adapter;
    List<SlidePack> slides = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
//        for(int i=0; i < 100; i++)
        slides.add(new SlidePack(STRING, sample + sample + sample, new ArrayList<String>()));
        slides.add(new SlidePack(IMAGE, link, new ArrayList<String>()));
        slides.add(new SlidePack(VIDEO,vlink, new ArrayList<String>()));
        viewPager = findViewById(R.id.pager);
        adapter = new SlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        btn_submit = findViewById(R.id.btn_submit);
        btn_final_submit = findViewById(R.id.btn_fsubmit);
        btn_next = findViewById(R.id.btn_next);
        btn_previous = findViewById(R.id.btn_previous);


        if(USER_TYPE == MANAGER || USER_TYPE == FULL_EXPERT) {
            btn_submit.setEnabled(false);
//            btn_final_submit.setEnabled(true);
        }else if(USER_TYPE == EXPERT){
            btn_final_submit.setEnabled(false);
        }

        btn_final_submit.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        int position = viewPager.getCurrentItem();
//        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.btn_next:
                if(position + 1 < slides.size())
                    viewPager.setCurrentItem(position+1, true);
                break;
            case R.id.btn_previous:
                if(position - 1 > -1)
                    viewPager.setCurrentItem(position - 1, true);
                break;
            case R.id.btn_submit:
                break;
            case R.id.btn_fsubmit:
                if(slides.size() > 0) {
                    //TODO save labels in database by request
                    Toast.makeText(this, position + " ", Toast.LENGTH_SHORT).show();
                    slides.remove(position);
                    adapter = new SlideAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(adapter);
                }
                break;
            case R.id.fab:
                Dialog dialog = new Dialog(this);
                View contentView  = getLayoutInflater().inflate(R.layout.layout_create_tag, null, false);
                dialog.setContentView(contentView);
                TextInputEditText ed = contentView.findViewById(R.id.ed_tag);
                dialog.setTitle("Add new tag");
                Button cancel = contentView.findViewById(R.id.cancel);
                Button add = contentView.findViewById(R.id.add);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tagStr = ed.getText().toString().trim();
                        if(tagStr.isEmpty()){
                            ed.setError("please enter something");
                        }else if(tagStr.length() > 25){
                            ed.setError("length limit");
                        }else{
                            SlidePack slidePack = slides.get(position);
                            slidePack.tags.add(tagStr);
                            slides.set(position, slidePack);
                            adapter = new SlideAdapter(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(position);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
                break;
            default: break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class SlideAdapter extends FragmentPagerAdapter {


        public SlideAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
//            Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            SlidePack pack = slides.get(position);
            SlideFragment slide = SlideFragment.newInstance(pack.type, pack.address, pack.tags);
            return slide;
        }

        @Override
        public int getCount() {
            return slides.size();
        }
    }

    class SlidePack{
        int type;
        String address;
        ArrayList<String> tags;
        public SlidePack(int type, String address,ArrayList<String> tags){
            this.type = type;
            this.address = address;
            this.tags = tags;
        }
    }


}