package com.example.tagsystemapplication;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagsystemapplication.Fragments.ItemFragment;
import com.example.tagsystemapplication.Models.Content;
import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.OutputTag;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.Tag;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import io.realm.RealmResults;
import static com.example.tagsystemapplication.DataHolder.currentItemIndex;
import static com.example.tagsystemapplication.DataHolder.currentProcessIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfileIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfilePackage;


public class ProfilesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton nextProfile, previousProfile, firstProfile, lastProfile, ok;
    private Button nextItem, previousItem, firstItem, lastItem;
    private TextView pageNumber, totalPages;
    private FragmentManager fragmentManager;
    private List<Profile> profiles;
    private Toolbar toolbar;

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
            DataHolder.loadPackageProfile( this);
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
                        current.setTagged(true);
                        logTags(current);
                        DataHolder.taggedProfiles.add(current);
                        profiles.remove(cur);
                    } else if (cur == 0) {
                        nextProfile.performClick();
                        current.setTagged(true);
                        logTags(current);
                        DataHolder.taggedProfiles.add(current);
                        profiles.remove(cur);
                        previousProfile.performClick();
                    } else /*if (cur > 0 && cur < numOfContents)*/ {
                        profiles.get(cur).setTagged(true);
                        current.setTagged(true);
                        logTags(current);
                        DataHolder.taggedProfiles.add(current);
                        profiles.remove(cur);
                        nextProfile.performClick();
                        previousProfile.performClick();
                    }
                    if(profiles.size() == 0) {
                        //TODO check if request of profile has next -> if has next then next profile should be
                        //TODO loaded from server and saved into database and profiles should be reinitialized else
                        // TODO if has not next then user should be navigated to summary activity
                        if(currentProfilePackage.hasNext()){
                            DataHolder.loadNextPackageProfile(this, false);
                        }else {
                            startActivity(new Intent(ProfilesActivity.this, SummaryActivity.class));
                            this.finish();
                        }
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


    private void logTags(Profile profile){
        Gson gson = new Gson();
        Output output = new Output();
        ArrayList<OutputTag> tags = new ArrayList<>();
        for(Tag t : profile.getTags()){
            tags.add(new OutputTag(t.getTitle()));
        }
        output.setTags(tags);
        String json = gson.toJson(output, Output.class);
        Log.i("INFO:::", json);
        try(FileWriter writer = new FileWriter(new File(Environment.getExternalStorageDirectory(), "output.json"))){
            writer.write(json);
        }catch (IOException e){
            Log.i("ERROR:::", e.getMessage());
        }
    }
}
