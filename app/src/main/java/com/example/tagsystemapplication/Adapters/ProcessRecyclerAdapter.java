package com.example.tagsystemapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagsystemapplication.DataHolder;
import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.OutputTag;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.Tag;
import com.example.tagsystemapplication.ProfilesActivity;
import com.example.tagsystemapplication.R;
import com.example.tagsystemapplication.SignInActivity;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tagsystemapplication.DataHolder.currentItemIndex;
import static com.example.tagsystemapplication.DataHolder.currentProcessIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfileIndex;
import static com.example.tagsystemapplication.DataHolder.processes;
import static com.example.tagsystemapplication.DataHolder.taggedProfiles;
import static com.example.tagsystemapplication.DataHolder.untaggedProfiles;

public class ProcessRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public ProcessRecyclerAdapter(Context context) {
        this.context = context;
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
        ((ProcessViewHolder) holder).onBind(processes.get(position), position);
    }


    @Override
    public int getItemCount() {
        return processes.size();
    }


    class ProcessViewHolder extends RecyclerView.ViewHolder {

        /**
         * below view have public modifier because
         * we have access VideoViewHolder inside the ExoPlayerRecyclerView
         */

        private TextView title;
        private View parent;
        private TextView expert_user;
        private TextView report;
        private ImageButton btn_sync;


        public ProcessViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            title = itemView.findViewById(R.id.title);
            expert_user = itemView.findViewById(R.id.expert_user);
            btn_sync = itemView.findViewById(R.id.btn_sync);
            report = itemView.findViewById(R.id.report);
        }

        void onBind(Process process, int position) {
            parent.setTag(this);
            title.setText(process.getTitle());
            expert_user.setText(process.getExpertUser());
            parent.setOnClickListener(view -> {
                currentProcessIndex = position;
                currentProfileIndex = 0;
                currentItemIndex = 0;
                Intent intent = new Intent(context , ProfilesActivity.class);
                context.startActivity(intent);
            });
            btn_sync.setOnClickListener((v)->{
                btn_sync.setImageResource(R.drawable.ic_sync);
                RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(2000);
                rotate.setInterpolator(new LinearInterpolator());
                btn_sync.startAnimation(rotate);

                Gson gson = new Gson();
                ArrayList<Output> outputs = new ArrayList<>();
                for(Profile profile : taggedProfiles){
                    ArrayList<OutputTag> outputTags = new ArrayList<>();
                    for(Tag tag : profile.getTags()){
                        if(tag.isChecked())
                            outputTags.add(new OutputTag(tag.getTitle()));
                    }
                    outputs.add(new Output(processes.get(currentProcessIndex).getId(), profile.getId(), outputTags));
                }
                String finalOutput = gson.toJson(outputs);
                Log.wtf("TAG:::", finalOutput);
                API_Interface apiInterface = API_Client.getAuthorizedClient(context).create(API_Interface.class);
                Call<JsonObject> call = apiInterface.sendOutput(finalOutput);
                call.enqueue(new Callback<JsonObject>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            Log.i("RESPONSE:::", response.message());
                            btn_sync.clearAnimation();
                            report.setText(String.format("%d / %d", taggedProfiles.size(), untaggedProfiles.size()));
                        }else if(response.code() == 401){
                            String refreshToken = context.getSharedPreferences("info", MODE_PRIVATE).getString("refresh", "");
                            if(!refreshToken.isEmpty()) {
                                DataHolder.reinitHeaders(context, refreshToken);
                                Toast.makeText(context, "reinitializing headers", Toast.LENGTH_SHORT).show();
                            }else{
                                context.startActivity(new Intent(context, SignInActivity.class));
                            }
                        }else{
                            btn_sync.clearAnimation();
                            btn_sync.setImageResource(R.drawable.ic_sync_problem);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        btn_sync.clearAnimation();
                        btn_sync.setImageResource(R.drawable.ic_sync_problem);
                    }
                });
            });
        }
    }
}