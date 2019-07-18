package com.example.tagsystemapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.tagsystemapplication.Constants.IMAGE;
import static com.example.tagsystemapplication.Constants.STRING;
import static com.example.tagsystemapplication.Constants.VIDEO;


public class SlideFragment extends Fragment{

    public int FILE_TYPE = -1;

    private ImageView imageView;
    private ImageButton btn_play;
    private TextView textView;
    private LinearLayout checkbox_layout;


    private String address;
    private List<String> tags = new ArrayList<>();
    public SlideFragment() {
        // Required empty public constructor
    }

    public static SlideFragment newInstance(int type, String addressOrString, ArrayList<String> tag) {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("address", addressOrString);
        args.putStringArrayList("tags", tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Toast.makeText(getContext(), "on create", //Toast.LENGTH_SHORT).show();
        Bundle bundle = getArguments();
        if (bundle != null) {
            tags = bundle.getStringArrayList("tags");
            FILE_TYPE = bundle.getInt("type");
            address = bundle.getString("address");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_slide ,container, false);
        checkbox_layout = rootView.findViewById(R.id.checkbox_layout);
        switch (FILE_TYPE){
            case IMAGE:
                imageView = rootView.findViewById(R.id.image_view);
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(address).placeholder(R.drawable.ic_not_loaded_img).into(imageView);
                break;
            case VIDEO:
                btn_play = rootView.findViewById(R.id.play_video);
                btn_play.setVisibility(View.VISIBLE);
                btn_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                        intent.putExtra("address", address);
                        getContext().startActivity(intent);
                    }
                });
                break;
            case STRING:
                textView = rootView.findViewById(R.id.text_view);
                textView.setVisibility(View.VISIBLE);
                textView.setText(address + "\n\n\n");
                break;
            default:break;
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!tags.isEmpty()){
            for(int i = 0 ; i < tags.size() ; i++){
                addCheckbox(view.getContext(), tags.get(i));
            }
        }
    }

    public void addCheckbox(Context context, String tagString){
        final CheckedTextView check1 = new CheckedTextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity = Gravity.RIGHT;
        check1.setLayoutParams(lp);
        check1.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1.isChecked()){
                    check1.setChecked(false);
                    check1.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                }else{
                    check1.setChecked(true);
                    check1.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                }
            }
        });
        check1.setTextColor(Color.BLACK);
        check1.setGravity(Gravity.RIGHT);
        check1.setText(tagString);
        checkbox_layout.addView(check1);
    }


    public void removeTag(){
        //TODO
    }





//    public void reloadFragment()
//    {
//        Log.i("Fragment tag:::", "reloading fragment");
//        Fragment currentFragment = SlideFragment.this;
//        if(getFragmentManager() != null) {
//            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
//            fragTransaction.detach(currentFragment);
//            fragTransaction.attach(currentFragment);
//            fragTransaction.commit();
//            Log.i("Fragment tag:::", "reloading fragment finish");
//        }else{
//            Log.i("Fragment tag:::", "fragment reloading failed");
//        }
//    }




}
