package com.csc214.rvandyke.wifiselector;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class AdvancedRequirementsActivity extends MenuActivity {
    private static final String TAG = "AdvancedReqActivity";

    private AdvancedRequirementsFragment mFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_requirements);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle("Project Advanced Requirements");

        FragmentManager manager = getSupportFragmentManager();
        mFrag = (AdvancedRequirementsFragment) manager.findFragmentById(R.id.advanced_requirements_frame);

        if(mFrag == null) {
            mFrag = AdvancedRequirementsFragment.newInstance();
            manager.beginTransaction().add(R.id.advanced_requirements_frame, mFrag, null).commit();
        }
    } //onCreate()
} //end class AdvancedRequirementsActivity
