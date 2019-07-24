package com.example.tagsystemapplication.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.tagsystemapplication.Adapters.CustomExpandableListAdapter;
import com.example.tagsystemapplication.MainActivityPrime;
import com.example.tagsystemapplication.MyTag;
import com.example.tagsystemapplication.Objects.SystemObject;
import com.example.tagsystemapplication.Objects.TextObject;
import com.example.tagsystemapplication.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextFragment extends Fragment {


    public static TextObject curObject;
    private TextView title;
    private ExpandableTextView content;


    private ExpandableListView tagList;
    private CustomExpandableListAdapter adapter;

    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<MyTag>> listDataChild = new HashMap<>();


    public TextFragment() {
        SystemObject object =  MainActivityPrime.items.get(MainActivityPrime.currentItemIndex);
        if(object instanceof TextObject) {
            curObject = (TextObject) object;
        }else{
            Log.wtf("TAG:::", "none text fragment passed!!!");
        }
    }

    public static TextFragment newInstance() {
        return new TextFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.text_fragment, container, false);
        title = rootView.findViewById(R.id.tvTitle);
        tagList = rootView.findViewById(R.id.tag_list);
        content = rootView.findViewById(R.id.content);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content.setText(curObject.getStrContent());
        title.setText(curObject.getTitle());
        ViewGroup.LayoutParams params = tagList.getLayoutParams();
        params.height += curObject.getTags().size() * 20;
        tagList.setLayoutParams(params);
        tagList.requestLayout();
        listDataHeader.add("Select Tag(s):");
        listDataChild.put(listDataHeader.get(0), curObject.getTags());
        adapter = new CustomExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        tagList.setAdapter(adapter);

    }

}
