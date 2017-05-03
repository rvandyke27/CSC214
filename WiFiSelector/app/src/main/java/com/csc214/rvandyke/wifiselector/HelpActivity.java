package com.csc214.rvandyke.wifiselector;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class HelpActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle("Help");
    } //onCreate()
} //end class HelpActivity
