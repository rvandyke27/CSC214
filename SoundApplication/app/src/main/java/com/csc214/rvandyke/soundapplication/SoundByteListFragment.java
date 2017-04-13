package com.csc214.rvandyke.soundapplication;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8
 * TA: Julian Weiss
 */

public class SoundByteListFragment extends Fragment {
    private static final String TAG = "TrackListFragment";

    public interface onSoundChangedListener {
        public void updateView(String s);
    }

    private SoundLoader mSoundLoader;
    private onSoundChangedListener listener;

    public SoundByteListFragment() {
        // Required empty public constructor
    } //SoundByteListFragment()

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setRetainInstance(true);
        mSoundLoader = new SoundLoader(getContext());
    } //onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateview() called");
        View view = inflater.inflate(R.layout.fragment_soundbyte_list, container, false);

        listener = (onSoundChangedListener)getActivity();

        RecyclerView soundRecycler = (RecyclerView)view.findViewById(R.id.soundbyte_recycler);
        soundRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        soundRecycler.setAdapter(new SoundByteAdapter(mSoundLoader.getSounds()));

        return view;
    } //onCreateView()

    private class SoundByteAdapter extends RecyclerView.Adapter<SoundByteHolder> {
        private List<SoundByte> mSounds;

        public SoundByteAdapter(List<SoundByte> sounds){
            mSounds = sounds;
        } //SoundByteAdapter()

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount() called with " + mSounds.size() + " items");
            return mSounds.size();
        } //getItemCount()

        @Override
        public SoundByteHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new SoundByteHolder(inflater, parent);
        } //onCreateViewHolder()

        @Override
        public void onBindViewHolder(SoundByteHolder holder, int position){
            holder.bind(mSounds.get(position));
            Log.d(TAG, "bound item at position " + position);
        } //onBindViewHolder()

    } //end class SoundByteAdapter

    private class SoundByteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mSoundName;
        private TextView mAlbumName;
        private TextView mArtistName;
        private SoundByte mSound;

        public SoundByteHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.soundbyte_view_holder, container, false));

            mSoundName = (TextView)itemView.findViewById(R.id.tv_sound_name);
            mAlbumName = (TextView)itemView.findViewById(R.id.tv_album_name);
            mArtistName = (TextView)itemView.findViewById(R.id.tv_artist);

            itemView.setOnClickListener(this);
        } //SoundByteHolder()

        public void bind(SoundByte sound){
            mSound = sound;
            mSoundName.setText(sound.getName());
            mAlbumName.setText(sound.getAlbum());
            mArtistName.setText(sound.getArtist());
        } //bind()

        @Override
        public void onClick(View v) {
            mSoundLoader.play(mSound);
            int orientation = getActivity().getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                Toast.makeText(getContext(), "Playing " + mSound.getName() + "!", Toast.LENGTH_LONG).show();
            }
            else{
               listener.updateView("Now Playing " + mSound.getName());
            }
        } //onClick()
    } //end class SoundByteHolder

} //end class SoundByteListFragment
