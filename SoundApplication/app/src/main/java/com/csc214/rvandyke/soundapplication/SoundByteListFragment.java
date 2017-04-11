package com.csc214.rvandyke.soundapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8
 * TA: Julian Weiss
 */

public class SoundByteListFragment extends Fragment {
    private static final String TAG = "TrackListFragment";

    private SoundLoader mSoundLoader;

    public SoundByteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setRetainInstance(true);
        mSoundLoader = new SoundLoader(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateview() called");
        View view = inflater.inflate(R.layout.fragment_soundbyte_list, container, false);

        RecyclerView soundRecycler = (RecyclerView)view.findViewById(R.id.soundbyte_recycler);
        soundRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //TODO: adapter

        return view;
    }

    private class SoundByteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    }

} //end class SoundByteListFragment
