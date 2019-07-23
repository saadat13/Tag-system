package com.example.tagsystemapplication.ListSlides;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.tagsystemapplication.ExoPlayerRecyclerView;
import com.example.tagsystemapplication.Adapters.VideoRecyclerAdapter;
import com.example.tagsystemapplication.Objects.VideoObject;
import com.example.tagsystemapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class VideoListSlide extends Fragment {

    private ExoPlayerRecyclerView recyclerView;
    private VideoRecyclerAdapter adapter;
    private static ArrayList<VideoObject> mediaObjects;
    private OnFragmentInteractionListener mListener;
    private boolean firstTime = true;

    public VideoListSlide() {
        // Required empty public constructor
    }


    public static VideoListSlide newInstance(ArrayList<VideoObject> objects) {
        VideoListSlide fragment = new VideoListSlide();
        mediaObjects = objects;
        return fragment;
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();
        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void refreshList(){
        recyclerView.setMediaObjects(mediaObjects);
        adapter = new VideoRecyclerAdapter(mediaObjects, initGlide());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_list_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (firstTime) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    recyclerView.playVideo(false);
                }
            });
            firstTime = false;
        }
    }

    private void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.exoPlayerRecyclerView);
        recyclerView.setMediaObjects(mediaObjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new VideoRecyclerAdapter(mediaObjects, initGlide());
        recyclerView.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Log.e("Warning:", " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroy() {
        if (recyclerView != null) {
            recyclerView.releasePlayer();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
