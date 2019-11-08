package com.example.tagsystemapplication;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagsystemapplication.Adapters.MyArrayAdapter;
import com.example.tagsystemapplication.Fragments.ItemFragment;
import com.example.tagsystemapplication.Models.Content;
import com.example.tagsystemapplication.Models.DB;
import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.OutputTag;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.Tag;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tagsystemapplication.DataHolder.currentItemIndex;
import static com.example.tagsystemapplication.DataHolder.currentProcessIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfileIndex;
import static com.example.tagsystemapplication.DataHolder.isConnectedToInternet;
import static com.example.tagsystemapplication.DataHolder.showingProfiles;
import static com.example.tagsystemapplication.DataHolder.taggedProfiles;
import static com.example.tagsystemapplication.DataHolder.untaggedProfiles;
import static com.example.tagsystemapplication.DataHolder.processes;


public class ProfilesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton nextProfile, previousProfile,
            firstProfile, lastProfile, ok, nextItem,
            previousItem, firstItem, lastItem;
    private TextView total, profileNumber;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private CheckBox tagBox;

    private ListView tagList;
    private MyArrayAdapter adapter;
    private List<Profile> backupProfiles;


    private boolean hasSendError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState!=null){
            updateUI();
        }else {
            loadProfiles();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void updateUI(){
        if (untaggedProfiles != null) {
            if (!untaggedProfiles.isEmpty()) {
                initView();
                currentItemIndex = -1;
                nextItem.performClick();
            }else {
                Toast.makeText(ProfilesActivity.this, "there is no profile exists!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else {
            Toast.makeText(ProfilesActivity.this, "error in loading untaggedProfiles", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void updateProfilesTags(){
        Process curP = processes.get(currentProcessIndex);
        for(Profile p : untaggedProfiles){
            List<Tag> t = Tag.cloneList(curP.getTags());
            p.setTags(t);
        }
    }

    private void initView() {

        total = toolbar.findViewById(R.id.toolbar_title);
        profileNumber = toolbar.findViewById(R.id.p_number);
        tagBox = toolbar.findViewById(R.id.chk_tag_state);

        total.setText(getTotal());
        profileNumber.setText(String.valueOf(currentProfileIndex+1));

        nextItem = findViewById(R.id.next_item);
        previousItem = findViewById(R.id.previous_item);
        firstItem = findViewById(R.id.first_item);
        lastItem = findViewById(R.id.last_item);

        nextProfile = findViewById(R.id.next_profile);
        previousProfile = findViewById(R.id.previous_profile);
        firstProfile = findViewById(R.id.first_profile);
        lastProfile = findViewById(R.id.last_profile);
        ok = findViewById(R.id.ok);


        nextItem.setOnClickListener(this);
        previousItem.setOnClickListener(this);
        firstItem.setOnClickListener(this);
        lastItem.setOnClickListener(this);

        nextProfile.setOnClickListener(this);
        previousProfile.setOnClickListener(this);
        firstProfile.setOnClickListener(this);
        lastProfile.setOnClickListener(this);
        ok.setOnClickListener(this);
        tagList = findViewById(R.id.tag_list);

        setupListAdapter(untaggedProfiles.get(currentProfileIndex).getTags());
    }

    private void setupListAdapter(List<Tag> tags) {
        adapter = new MyArrayAdapter(this, tags);
        tagList.setAdapter(adapter);
    }

    private String getTotal() {
        if(currentProfileIndex >= untaggedProfiles.size()) return " ";
        int itemsNumber = untaggedProfiles.get(currentProfileIndex).getContents().size();
        return String.format("(%d/%d)/%d", (currentItemIndex + 1), itemsNumber, untaggedProfiles.size());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_profile:
                if (currentProfileIndex + 1 < untaggedProfiles.size()) {
                    ++currentProfileIndex;
                    currentItemIndex = -1;
                    nextItem.performClick();
                    setupListAdapter(untaggedProfiles.get(currentProfileIndex).getTags());
                }
                break;
            case R.id.previous_profile:
                if (currentProfileIndex - 1 >= 0) {
                    --currentProfileIndex;
                    currentItemIndex = -1;
                    nextItem.performClick();
                    setupListAdapter(untaggedProfiles.get(currentProfileIndex).getTags());
                }
                break;
            case R.id.first_profile:
                if (untaggedProfiles.size() > 0) {
                    currentProfileIndex = 0;
                    currentItemIndex = -1;
                    nextItem.performClick();
                    setupListAdapter(untaggedProfiles.get(currentProfileIndex).getTags());
                }
                break;
            case R.id.last_profile:
                if (untaggedProfiles.size() > 0) {
                    currentProfileIndex = untaggedProfiles.size() - 1;
                    currentItemIndex = -1;
                    nextItem.performClick();
                    setupListAdapter(untaggedProfiles.get(currentProfileIndex).getTags());
                }
                break;
            case R.id.next_item:
                if (currentItemIndex + 1 < untaggedProfiles.get(currentProfileIndex).getContents().size()) {
                    Content curItem = untaggedProfiles.get(currentProfileIndex).getContents().get(++currentItemIndex);
                    selectDestination(curItem);
                }
                break;
            case R.id.previous_item:
                if (currentItemIndex - 1 >= 0) {
                    Content curItem = untaggedProfiles.get(currentProfileIndex).getContents().get(--currentItemIndex);
                    selectDestination(curItem);
                }
                break;
            case R.id.first_item:
                if (untaggedProfiles.get(currentProfileIndex).getContents().size() > 0) {
                    currentItemIndex = 0;
                    Content firstItem = untaggedProfiles.get(currentProfileIndex).getContents().get(currentItemIndex);
                    selectDestination(firstItem);
                }
                break;
            case R.id.last_item:
                int size = untaggedProfiles.get(currentProfileIndex).getContents().size();
                if (size > 0) {
                    currentItemIndex = size - 1;
                    Content lastItem = untaggedProfiles.get(currentProfileIndex).getContents().get(currentItemIndex);
                    selectDestination(lastItem);
                }
                break;
            case R.id.ok:
                Profile currentProfile = untaggedProfiles.get(currentProfileIndex);
                // first change state of current profile to tagged state if already not tagged
                if(!currentProfile.isTagged()) {
                    currentProfile.setTagged(true);
                    taggedProfiles.add(currentProfile);
                    //untaggedProfiles.remove(currentProfile);
                }
//                if(currentProfile.getRealmTags2() == null || currentProfile.getRealmTags2().size() == 0){
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                currentProfile.setRealmTags2(new RealmList<Tag>(currentProfile.getTags().toArray(new Tag[currentProfile.getTags().size()])));
                realm.copyToRealmOrUpdate(currentProfile);
                realm.commitTransaction();
                realm.close();
//                }
//                new DBRepository<>(Profile.class).insert(currentProfile);

                // second navigate to next or previous profile
                if (currentProfileIndex + 1 < untaggedProfiles.size()) {
                    nextProfile.performClick();
                } else if (currentProfileIndex - 1 >= 0) {
                    previousProfile.performClick();
                }

                break;
        }

        total.setText(getTotal());
        profileNumber.setText("profile: " + String.valueOf(currentProfileIndex+1));
        tagBox.setChecked(untaggedProfiles.get(currentProfileIndex).isTagged());

    }


    private void selectDestination(Content curItem) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, ItemFragment.newInstance(curItem))
                .commit();
    }

    private void sendTags(Process process, Profile profile){
//        if(isConnectedToInternet(this)) {
//            ArrayList<OutputTag> tags = new ArrayList<>();
//            for (Tag t : process.getTags()) {
//                if(t.isChecked())
//                    tags.add(new OutputTag(t.getTitle()));
//            }
//            Output output = new Output(process.getId(), profile.getId(), tags);
//            //sending to server ...
//            API_Interface apiInterface = API_Client.getAuthorizedClient(this).create(API_Interface.class);
//            Call<Output> call = apiInterface.sendOutput(output);
//            call.enqueue(new Callback<Output>() {
//                @Override
//                public void onResponse(Call<Output> call, Response<Output> response) {
//                    if (response.isSuccessful()) {
//                        Toast.makeText(ProfilesActivity.this, "tagged successfully", Toast.LENGTH_SHORT).show();
//                        //untaggedProfiles.remove(profile);
//                    }else if(response.code() == 401){
//                        String refreshToken = getSharedPreferences("info",MODE_PRIVATE).getString("refresh", "");
//                        if(!refreshToken.isEmpty()) {
//                            DataHolder.reinitHeaders(ProfilesActivity.this, refreshToken);
//                            Toast.makeText(ProfilesActivity.this, "reinitializing headers", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Log.wtf("TAG::::", "refresh token is empty");
//                            startActivity(new Intent(ProfilesActivity.this, SignInActivity.class));
//                            ProfilesActivity.this.finish();
//                        }
//                    }else{
//                        onSendingError(process, profile);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Output> call, Throwable t) {
//                    Log.e("Response:::", "");
//                    t.printStackTrace();
//                }
//            });
//        }else{
//            onSendingError(process, profile);
//        }
    }

    private void onSendingError(Process process, Profile profile){
        hasSendError = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("connection error")
                .setMessage("sending tags to server failed")
                .setPositiveButton("Resend", (dialogInterface, i) -> {
                    sendTags(process, profile);
                    hasSendError = false;
                    dialogInterface.dismiss();
                });
        builder.create().show();
    }


    private void onProfileLoadError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("connection error")
                .setMessage("connecting to server failed")
                .setCancelable(false)
                .setPositiveButton("Reload", (dialogInterface, i) -> {
                    loadProfiles();
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Exit", (dialogInterface, i) -> {
                    this.finish();
                });
        builder.create().show();
    }

    public void loadProfiles() {
        untaggedProfiles = processes.get(currentProcessIndex).getRealmProfiles();
        if(untaggedProfiles == null || untaggedProfiles.size() == 0)
            loadProfilesFromServer();
        else{
            //updateProfilesTags();
            for(Profile p : untaggedProfiles){
                p.setTags(Tag.cloneList(p.getRealmTags2()));
                p.setContents(p.getRealmContents());
            }
            updateUI();
            for(Profile p : untaggedProfiles){
                if(p.isTagged())
                    taggedProfiles.add(p);
            }
        }
    }

    private void loadProfilesFromServer() {
        API_Interface apiInterface = API_Client.getAuthorizedClient(this).create(API_Interface.class);
        Call<List<Profile>> call = apiInterface.getProfiles(processes.get(currentProcessIndex).getId());
        call.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if(!response.body().isEmpty()) {
//                            showingProfiles = response.body();
                            untaggedProfiles = response.body();
                            updateProfilesTags();
                            updateUI();
                            updateDatabase();
                            Toast.makeText(ProfilesActivity.this, "database is updating...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProfilesActivity.this, "there is no profile in this process exiting...", Toast.LENGTH_SHORT).show();
                            ProfilesActivity.this.finish();
                        }
                    }else {
                        Log.e("Response:::", "null untaggedProfiles loaded from response!");
                        onProfileLoadError();
                    }
                }else if(response.code() == 401){
                    String refreshToken = getSharedPreferences("info", MODE_PRIVATE).getString("refresh", "");
                    if(!refreshToken.isEmpty()) {
                        DataHolder.reinitHeaders(ProfilesActivity.this, refreshToken);
                        Toast.makeText(ProfilesActivity.this, "reinitializing headers", Toast.LENGTH_SHORT).show();
                    }else{
                        startActivity(new Intent(ProfilesActivity.this, SignInActivity.class));
                        ProfilesActivity.this.finish();
                    }
                }else if(response.code() == 404){
                    Toast.makeText(ProfilesActivity.this, "there is no package exists for this process, removing it...", Toast.LENGTH_LONG).show();
                    new DBRepository<>(Process.class).delete(processes.get(currentProcessIndex).getId());
                    processes.remove(currentProcessIndex);
                    Log.wtf("Prob:::", currentItemIndex + " ");
                    finish();
                } else {
                    Log.i("Response:::", response.toString());
                    onProfileLoadError();
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                onProfileLoadError();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("hide tagged profiles").setOnMenuItemClickListener((v)->{
            List<Profile> onlyUntaggedProfiles = new ArrayList<>(untaggedProfiles);
            onlyUntaggedProfiles.removeAll(taggedProfiles);
            backupProfiles = untaggedProfiles;
            untaggedProfiles = onlyUntaggedProfiles;

            if(untaggedProfiles.size() != 0) {
                currentProfileIndex = -1;
                nextProfile.performClick();
                // updating page number
                total.setText(getTotal());
                profileNumber.setText("profile: " + String.valueOf(currentProfileIndex + 1));
            }else{
                untaggedProfiles = backupProfiles;
                backupProfiles = null;
                Toast.makeText(this, "all of profile are tagged", Toast.LENGTH_LONG).show();
            }
            return true;
        });
        menu.add("show tagged profiles").setOnMenuItemClickListener((v)->{
            if(backupProfiles != null) {
                untaggedProfiles = backupProfiles;
                backupProfiles = null;

                currentProfileIndex = -1;
                nextProfile.performClick();
                total.setText(getTotal());
                profileNumber.setText("profile: " + String.valueOf(currentProfileIndex+1));
            }
            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }

    public static void updateDatabase(){
        try(Realm realm = Realm.getDefaultInstance()){
            realm.executeTransactionAsync(realm1 -> {
                Process currentProcess = processes.get(currentProcessIndex);
                // add realm profiles
                //            p.setRealmTags2(new RealmList<Tag>(p.getTags().toArray(new Tag[0])));
//                realm1.beginTransaction();
                currentProcess.setRealmTags(new RealmList<Tag>(currentProcess.getTags().toArray(new Tag[0])));
                for(Profile profile : untaggedProfiles){
                    profile.setRealmContents(new RealmList<Content>(profile.getContents().toArray(new Content[0])));
                    profile.setRealmTags2(new RealmList<Tag>(Tag.cloneList(profile.getTags()).toArray(new Tag[0])));
                }
                currentProcess.setRealmProfiles(new RealmList<Profile>(untaggedProfiles.toArray(new Profile[0])));
//                realm1.commitTransaction();
                realm1.insertOrUpdate(currentProcess);
            });
        }
    }
}
