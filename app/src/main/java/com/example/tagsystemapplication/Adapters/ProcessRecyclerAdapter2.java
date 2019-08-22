package com.example.tagsystemapplication.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.ProcessActivity;
import com.example.tagsystemapplication.ProfilesActivity;
import com.example.tagsystemapplication.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class ProcessRecyclerAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Process> processObjects;
    private ProcessActivity activity;

    public ProcessRecyclerAdapter2(ProcessActivity activity, List<Process> processes) {
        this.processObjects = processes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProcessViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProcessViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.process_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ProcessViewHolder) holder).onBind(activity, processObjects.get(position), position);
    }




    @Override
    public int getItemCount() {
        return processObjects.size();
    }


    class ProcessViewHolder extends RecyclerView.ViewHolder {

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

        void onBind(ProcessActivity activity, Process process, int index) {
            parent.setTag(this);
            title.setText(process.getTitle());
            details.setText(process.getOtherDetails());
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ProfilesActivity.class);
                    intent.putExtra("processIndex", index);
                    activity.startActivity(intent);
                }
            });
        }
    }
}