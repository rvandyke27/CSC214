package com.csc214.rvandyke.soundapplication;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8
 * TA: Julian Weiss
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();

        SoundByteListFragment fragment = (SoundByteListFragment)manager.findFragmentByTag("audio");
        if(fragment == null) {
            fragment = new SoundByteListFragment();
            manager.beginTransaction()
                    .add(R.id.main_activity_frame, fragment, "audio")
                    .commit();
        }
    }
} //end class MainActivity
