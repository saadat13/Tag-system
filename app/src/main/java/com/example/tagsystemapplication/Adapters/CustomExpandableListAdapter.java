package com.example.tagsystemapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tagsystemapplication.Models.Tag;
import com.example.tagsystemapplication.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<Tag>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<Tag>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }


    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {
        View rootView;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = layoutInflater.inflate(R.layout.tag_item, parent, false);
        }else{
            rootView = convertView;
        }

        Tag tag = ((Tag) getChild(groupPosition, childPosition));
        String expandedListText = tag.getTitle();
        String percent = tag.getPercent() + "%";
        boolean isChecked = tag.isChecked();
        TextView expandedListTextView = rootView.findViewById(R.id.tag_name);
        CheckBox checkBox = rootView.findViewById(R.id.checkBox);
        TextView tv_percent = rootView.findViewById(R.id.tv_percent);
        tv_percent.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("users")
                    .setMessage(Arrays.toString(tag.getUsers().toArray()))
                    .setPositiveButton("ok", null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .create()
                    .show();
        });
        tv_percent.setText(percent);
//        Log.wtf("CHECKBOX:::", isChecked + " ");
        checkBox.setChecked(isChecked);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isChecked = checkBox.isChecked();
                tag.setChecked(isChecked);
            }
        });
        expandedListTextView.setText(expandedListText);

        return rootView;
    }


    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        View rootView;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = layoutInflater.inflate(R.layout.list_group, parent, false);
        }else{
            rootView = convertView;
        }
        TextView listTitleTextView = (TextView) rootView
                .findViewById(R.id.lblListHeader);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return rootView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}