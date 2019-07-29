package com.example.tagsystemapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tagsystemapplication.Objects.ImageObject;
import com.example.tagsystemapplication.Objects.Profile;
import com.example.tagsystemapplication.Objects.SystemObject;
import com.example.tagsystemapplication.Objects.TextObject;
import com.example.tagsystemapplication.Objects.VideoObject;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import static com.example.tagsystemapplication.DataHolder.currentItemIndex;
import static com.example.tagsystemapplication.DataHolder.currentProcessIndex;
import static com.example.tagsystemapplication.DataHolder.currentProfileIndex;


public class ProfilesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton nextProfile, previousProfile, firstProfile, lastProfile, ok;
    private Button nextItem, previousItem, firstItem, lastItem;
    private NavHostFragment navHostFragment;
    private TextView pageNumber, totalPages;

    ArrayList<Profile> profiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        currentProcessIndex = getIntent().getExtras().getInt("processIndex");
        profiles = DataHolder.getProcesses().get(currentProcessIndex).getProfiles();
        initView();
        currentItemIndex = -1;
        nextItem.performClick();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pageNumber = toolbar.findViewById(R.id.toolbar_title);
        totalPages = toolbar.findViewById(R.id.total_pages);
        pageNumber.setText(getPageNumber());
        totalPages.append(String.valueOf(profiles.size()));

        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

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
                    SystemObject curItem = profiles.get(currentProfileIndex).getContents().get(++currentItemIndex);
                    if (curItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (curItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else if (curItem instanceof VideoObject) {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
                }
                break;
            case R.id.previous_item:
                if (currentItemIndex - 1 >= 0) {
                    SystemObject curItem = profiles.get(currentProfileIndex).getContents().get(--currentItemIndex);
                    if (curItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (curItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else /*if(firstProfile instanceof VideoObject)*/ {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
                }
                break;
            case R.id.first_item:
                if (profiles.get(currentProfileIndex).getContents().size() > 0) {
                    currentItemIndex = 0;
                    SystemObject firstItem = profiles.get(currentProfileIndex).getContents().get(currentItemIndex);
                    if (firstItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (firstItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else /*if(firstProfile instanceof VideoObject)*/ {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
                }
                break;
            case R.id.last_item:
                int size = profiles.get(currentProfileIndex).getContents().size();
                if (size > 0) {
                    currentItemIndex = size - 1;
                    SystemObject lastItem = profiles.get(currentProfileIndex).getContents().get(currentItemIndex);
                    if (lastItem instanceof TextObject) {
                        navHostFragment.getNavController().navigate(R.id.textFragment);
                    } else if (lastItem instanceof ImageObject) {
                        navHostFragment.getNavController().navigate(R.id.imageFragment);
                    } else /*if(firstProfile instanceof VideoObject)*/ {
                        navHostFragment.getNavController().navigate(R.id.videoFragment);
                    }
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
                        DataHolder.getProcesses().remove(currentProcessIndex);
                        startActivity(new Intent(ProfilesActivity.this, ChecklistActivity.class));
                        this.finish();
                    }
                }
                break;
        }
        pageNumber.setText(getPageNumber());
        totalPages.setText("pages: " + String.valueOf(profiles.size()));
    }

    private void logTags(Profile profile){
        StringBuilder builder = new StringBuilder();
        for(SystemObject c : profile.getContents()){
            builder.append(String.valueOf(c.getuId())).append(" tags: ");
            for(MyTag tag : c.getTags()){
                if(tag.isChecked())
                    builder.append("#").append(tag.getTitle()).append(" ");
            }
            builder.append("\n");
        }
        Log.e("INFO:::", builder.toString());
    }
}
