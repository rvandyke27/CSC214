package com.csc214.rvandyke.assignment6application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 6
 * TA:Julian Weiss
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RvandykeAssignment6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity created");

        TeamListFragment fragment = new TeamListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_frame, fragment).commit();
        setContentView(R.layout.activity_main);
    }
}
