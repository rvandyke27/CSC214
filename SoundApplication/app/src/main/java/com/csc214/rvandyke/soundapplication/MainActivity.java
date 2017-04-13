package com.csc214.rvandyke.soundapplication;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8
 * TA: Julian Weiss
 */

public class MainActivity extends AppCompatActivity implements SoundByteListFragment.onSoundChangedListener{
    private static final String TAG = "MainActivity";

    private TextView mSongName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSongName = (TextView)findViewById(R.id.text_view_sound_name);

        FragmentManager manager = getSupportFragmentManager();

        SoundByteListFragment fragment = (SoundByteListFragment)manager.findFragmentById(R.id.main_activity_frame);
        if(fragment == null) {
            fragment = new SoundByteListFragment();
            manager.beginTransaction()
                    .add(R.id.main_activity_frame, fragment, "audio")
                    .commit();
        }
    } //onCreate()

    public void updateView(String message){
        mSongName.setText(message);
    } //updateView

} //end class MainActivity
