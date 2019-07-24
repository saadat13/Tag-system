package com.example.tagsystemapplication.Adapters;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.tagsystemapplication.MainActivityPrime;
import com.example.tagsystemapplication.Objects.ProcessObject;
import com.example.tagsystemapplication.ProcessActivity;
import com.example.tagsystemapplication.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ProcessViewHolder extends RecyclerView.ViewHolder {

    /**
     * below view have public modifier because
     * we have access VideoViewHolder inside the ExoPlayerRecyclerView
     */

    private TextView title;
    private ExpandableTextView details;
    private View parent;


    public ProcessViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        title = itemView.findViewById(R.id.title);
        details = itemView.findViewById(R.id.detail);
    }

    void onBind(ProcessActivity activity, ProcessObject processObject) {
        parent.setTag(this);
        title.setText(processObject.getTitle());
        details.setText(processObject.getDetails());
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, MainActivityPrime.class));
            }
        });
    }




}