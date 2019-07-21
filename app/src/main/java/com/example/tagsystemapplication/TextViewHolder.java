package com.example.tagsystemapplication;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.tagsystemapplication.Constants.showOptions;


public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * below view have public modifier because
     * we have access PlayerViewHolder inside the ExoPlayerRecyclerView
     */
    public FrameLayout mediaContainer;
    public ProgressBar progressBar;
    private TextView title;
    private ExpandableTextView content;
    private View parent;
    private Spinner spinner;
    private ImageButton options;
    private TextObject object;


    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        mediaContainer = itemView.findViewById(R.id.mediaContainer);
        title = itemView.findViewById(R.id.tvTitle);
        spinner = itemView.findViewById(R.id.spinner);
        progressBar = itemView.findViewById(R.id.progressBar);
        content = itemView.findViewById(R.id.ivMediaCoverImage);
        options = itemView.findViewById(R.id.imageButton);
    }

    void onBind(TextObject textObject) {
        this.object = textObject;
        parent.setTag(this);
        title.setText(textObject.getTitle());
        spinner.setAdapter(new MySpinnerAdapter(parent.getContext(), R.layout.spinner_item, textObject.getTags()));
        content.setText(textObject.getStrContent());
        options.setOnClickListener(this);

    }

    private void refreshTagList(){
        spinner.setAdapter(new MySpinnerAdapter(parent.getContext(), R.layout.spinner_item, object.getTags()));
    }

    @Override
    public void onClick(View view) {
        showOptions(view, object);
        refreshTagList();
    }


}