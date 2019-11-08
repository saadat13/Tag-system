package com.example.tagsystemapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tagsystemapplication.Models.Tag;
import com.example.tagsystemapplication.R;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Tag> {

    private final List<Tag> list;
    private final Context context;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;

    public MyArrayAdapter(Context context, List<Tag> list) {
        super(context, R.layout.tag_item, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.tag_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.tag_name);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox);


            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    list.get(getPosition).setChecked(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
//                    untaggedProfiles.get(currentProfileIndex).getTags().get(position).setChecked(buttonView.isChecked());
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.tag_name, viewHolder.text);
            convertView.setTag(R.id.checkBox, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.
        viewHolder.text.setText(list.get(position).getTitle());
        viewHolder.checkbox.setChecked(list.get(position).isChecked());


        return convertView;
    }
}