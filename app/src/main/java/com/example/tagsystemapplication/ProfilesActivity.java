package com.example.tagsystemapplication;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagsystemapplication.Fragments.ItemFragment;
import com.example.tagsystemapplication.Models.Content;
import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.OutputTag;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.ProfilePackage;
import com.example.tagsystemapplication.Models.Tag;
import com.example.tagsystemapplication.Models.User;
import com.example.tagsystemapplication.Repositories.ContentRepository;
import com.example.tagsystemapplication.Repositories.ProcessRepository;
import com.example.tagsystemapplication.Repositories.ProfilePackageRepository;
import com.example.tagsystemapplication.Repositories.ProfileRepository;
import com.example.tagsystemapplication.Repositories.TagRepository;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tagsystemapplication.DataHolder.currentItemIndex;
import static com.example.tagsystemapplication.DataHolder.currentProcessIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfileIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfilePackage;
import static com.example.tagsystemapplication.DataHolder.isConnectedToInternet;
import static com.example.tagsystemapplication.DataHolder.processes;


public class ProfilesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton nextProfile, previousProfile, firstProfile, lastProfile, ok, nextItem, previousItem, firstItem, lastItem;
    private TextView pageNumber, totalPages, tvTime;
    private FragmentManager fragmentManager;
    private List<Profile> profiles;
    private Toolbar toolbar;
    private CountDownTimer timer;
    private long mTimeLeftInMillis = 0L;

    private boolean hasSendError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState!=null){
            updateUI();
        }else {
            loadPackageProfile();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void updateUI(){
        profiles = DataHolder.profiles;
        if (profiles != null) {
            if (!profiles.isEmpty()) {
                initView();
                currentItemIndex = -1;
                nextItem.performClick();
            }else {
                Toast.makeText(ProfilesActivity.this, "there is no profile exists!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else {
            Toast.makeText(ProfilesActivity.this, "error in loading profiles", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        pageNumber = toolbar.findViewById(R.id.toolbar_title);
        totalPages = toolbar.findViewById(R.id.total_pages);
        tvTime     = toolbar.findViewById(R.id.timer);
        pageNumber.setText(getPageNumber());
        totalPages.append(String.valueOf(profiles.size()));

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

        long remainingTimeMilis = Long.valueOf(currentProfilePackage.getExpireDate()) - System.currentTimeMillis();
        mTimeLeftInMillis = remainingTimeMilis;
        timer = new CountDownTimer(remainingTimeMilis, 1) {
            public void onTick(long millisUntilFinished) {
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(1);

                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                tvTime.setText(timeLeftFormatted);
            }

            public void onFinish() {
                tvTime.setText("time's up!");
            }
        };
        timer.start();

    }

    private String getPageNumber() {
        if(currentProfileIndex >= profiles.size()) return " ";
        int itemsNumber = profiles.get(currentProfileIndex).getContents().size();
        return String.format("(%d/%d)/%d", (currentItemIndex + 1), itemsNumber, currentProfileIndex + 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_profile:
                if (currentProfileIndex + 1 < profiles.size()) {
                    ++currentProfileIndex;
                    currentItemIndex = -1;
                    nextItem.performClick();
                }
                break;
            case R.id.previous_profile:
                if (currentProfileIndex - 1 >= 0) {
                    --currentProfileIndex;
                    currentItemIndex = -1;
                    nextItem.performClick();
                }
                break;
            case R.id.first_profile:
                if (profiles.size() > 0) {
                    currentProfileIndex = 0;
                    currentItemIndex = -1;
                    nextItem.performClick();
                }
                break;
            case R.id.last_profile:
                if (profiles.size() > 0) {
                    currentProfileIndex = profiles.size() - 1;
                    currentItemIndex = -1;
                    nextItem.performClick();
                }
                break;
            case R.id.next_item:
                if (currentItemIndex + 1 < profiles.get(currentProfileIndex).getContents().size()) {
                    Content curItem = profiles.get(currentProfileIndex).getContents().get(++currentItemIndex);
                    selectDestination(curItem);
                }
                break;
            case R.id.previous_item:
                if (currentItemIndex - 1 >= 0) {
                    Content curItem = profiles.get(currentProfileIndex).getContents().get(--currentItemIndex);
                    selectDestination(curItem);
                }
                break;
            case R.id.first_item:
                if (profiles.get(currentProfileIndex).getContents().size() > 0) {
                    currentItemIndex = 0;
                    Content firstItem = profiles.get(currentProfileIndex).getContents().get(currentItemIndex);
                    selectDestination(firstItem);
                }
                break;
            case R.id.last_item:
                int size = profiles.get(currentProfileIndex).getContents().size();
                if (size > 0) {
                    currentItemIndex = size - 1;
                    Content lastItem = profiles.get(currentProfileIndex).getContents().get(currentItemIndex);
                    selectDestination(lastItem);
                }
                break;
            case R.id.ok:
                int cur = currentProfileIndex;
                int numOfContents = profiles.size();
                if(numOfContents > 0) {
                    Profile current = profiles.get(cur);
                    if (cur == numOfContents - 1) {
                        previousProfile.performClick();
                        sendTags(current);
                    } else if (cur == 0) {
                        nextProfile.performClick();
                        sendTags(current);
                        previousProfile.performClick();
                    } else /*if (cur > 0 && cur < numOfContents)*/ {
                        sendTags(current);
                        nextProfile.performClick();
                        previousProfile.performClick();
                    }
                }
                break;
        }
        pageNumber.setText(getPageNumber());
        totalPages.setText("pages: " + String.valueOf(profiles.size()));
    }

    private void selectDestination(Content curItem) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, ItemFragment.newInstance(curItem))
                .commit();
    }

    private void sendTags(Profile profile){
        if(isConnectedToInternet(this)) {
            ArrayList<OutputTag> tags = new ArrayList<>();
            for (Tag t : profile.getTags())
                tags.add(new OutputTag(t.getTitle()));

            Output output = new Output(currentProfilePackage.getId(), processes.get(currentProcessIndex).getId(), profile.getId(), tags);
            //sending to server ...
            API_Interface apiInterface = API_Client.getAuthorizedClient(this).create(API_Interface.class);
            Call<Output> call = apiInterface.sendOutput(output);
            call.enqueue(new Callback<Output>() {
                @Override
                public void onResponse(Call<Output> call, Response<Output> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProfilesActivity.this, "tagged successfully", Toast.LENGTH_SHORT).show();
                        profiles.remove(profile);
                        if(profiles.size() == 0) {
                            //TODO check if request of profile has next -> if has next then next profile should be
                            //TODO loaded from server and saved into database and profiles should be reinitialized else
                            // TODO if has not next then user should be navigated to summary activity
                            if(currentProfilePackage.hasNext()){
                                ProfilePackageRepository ppr = new ProfilePackageRepository();
                                ppr.delete(currentProfilePackage.getId());
                                loadPackageProfile();
                            }else {
                                if(!hasSendError)
                                    ProfilesActivity.this.finish();
                            }
                        }
                    }else{
                        onSendingError(profile);
                    }
                }

                @Override
                public void onFailure(Call<Output> call, Throwable t) {
                    Log.e("Response:::", "");
                    t.printStackTrace();
                }
            });
        }else{
            onSendingError(profile);
        }
    }

    private void onSendingError(Profile profile){
        hasSendError = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("connection error")
                .setMessage("sending tags to server failed")
                .setPositiveButton("Resend", (dialogInterface, i) -> {
                    sendTags(profile);
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
                    loadPackageProfile();
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Exit", (dialogInterface, i) -> {
                    this.finish();
                });
        builder.create().show();
    }

    public void loadPackageProfile() {
        if(isConnectedToInternet(this)){
            loadPackageFromServer();
        }else{
            // load from database
            Log.i("DB_TAG:::", "loading from db...");
            Realm realm = Realm.getDefaultInstance();
//            RealmResults<ProfilePackage> p;
            realm.where(ProfilePackage.class)
                    .equalTo("processId", processes.get(currentProcessIndex).getId()).findFirstAsync().addChangeListener(realmModel -> {
                if(realmModel!=null){
                    ProfilePackage pp = (ProfilePackage) realmModel;
                    currentProfilePackage = pp;
                    DataHolder.profiles = currentProfilePackage.getProfiles();
                    updateUI();
                }
            });

        }

    }

    private void loadPackageFromServer() {
        API_Interface apiInterface = API_Client.getAuthorizedClient(this).create(API_Interface.class);
        Call<ProfilePackage> call = apiInterface.getPackageProfile(processes.get(currentProcessIndex).getId());
        Log.wtf("ID:::", processes.get(currentProcessIndex).getId() + " ");
        call.enqueue(new Callback<ProfilePackage>() {
            @Override
            public void onResponse(Call<ProfilePackage> call, Response<ProfilePackage> response) {
                if (response.code() == 200) {
                    currentProfilePackage = response.body();
                    if (currentProfilePackage != null) {
                        if(!currentProfilePackage.getProfiles().isEmpty()) {
                            DataHolder.profiles = currentProfilePackage.getProfiles();
                            updateUI();
                            updateDatabase();
                            Toast.makeText(ProfilesActivity.this, "database is updating...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProfilesActivity.this, "there is no profile in this process exiting...", Toast.LENGTH_SHORT).show();
                            ProfilesActivity.this.finish();
                        }
                    }else {
                        Log.e("Response:::", "null profiles loaded from response!");
                        onProfileLoadError();
                    }
                }else if(response.code() == 401){
                    String refreshToken = getPreferences(MODE_PRIVATE).getString("refresh", "");
                    if(!refreshToken.isEmpty()) {
                        DataHolder.reinitHeaders(ProfilesActivity.this, refreshToken);
                        Toast.makeText(ProfilesActivity.this, "reinitializing headers", Toast.LENGTH_SHORT).show();
                    }else{
                        startActivity(new Intent(ProfilesActivity.this, SignInActivity.class));
                        ProfilesActivity.this.finish();
                    }
                }else if(response.code() == 404){
                    Toast.makeText(ProfilesActivity.this, "there is no package exists for this process, removing it...", Toast.LENGTH_LONG).show();
                    new ProcessRepository().deleteCurrent();
                    processes.remove(currentProcessIndex);
                    Log.wtf("Prob:::", currentItemIndex + " ");
                    finish();
                } else {
                    Log.i("Response:::", response.toString());
                    onProfileLoadError();
                }
            }

            @Override
            public void onFailure(Call<ProfilePackage> call, Throwable t) {
                Log.e("Response:::", t.toString());
                onProfileLoadError();
            }
        });

    }

    public static void updateDatabase(){
        try(Realm realm = Realm.getDefaultInstance()){
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    List<Profile> profiles = currentProfilePackage.getProfiles();
                    for(Profile p : profiles){
                        // add contents to realm
                        RealmList<Content> realmContents = new RealmList<>();
                        realmContents.addAll(p.getContents());
                        p.setRealmContents(realmContents);

                        // add tags to realm
                        RealmList<Tag> realmTags = new RealmList<>();
                        realmTags.addAll(p.getTags());
                        p.setRealmTags(realmTags);

                        //add users to Tags
                        for(Tag t : p.getTags()){
                            RealmList<User> realmUsers = new RealmList<>();
                            realmUsers.addAll(t.getUsers());
                            t.setRealmUsers(realmUsers);
                        }
                    }

                    // add realm profiles
                    RealmList<Profile> realmProfiles = new RealmList<>();
                    realmProfiles.addAll(profiles);
                    currentProfilePackage.setRealmProfiles(realmProfiles);

                    // add realm profile packages
                    RealmList<ProfilePackage> realmProfilePackages = new RealmList<>();
                    realmProfilePackages.add(currentProfilePackage);
                    processes.get(currentProcessIndex).setRealmProfilePackages(realmProfilePackages);

                    realm.insertOrUpdate(processes.get(currentProcessIndex));
                }
            });
        }
    }
}
