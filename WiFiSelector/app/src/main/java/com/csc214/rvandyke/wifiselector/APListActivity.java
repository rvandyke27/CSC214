package com.csc214.rvandyke.wifiselector;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class APListActivity extends MenuActivity {
    private static final String TAG = "APListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplist);
        Log.d(TAG, "onCreate() called");

        mWifiManager.setWifiEnabled(true);
        //wait for wifi to turn on
        while(!mWifiManager.isWifiEnabled()){}

    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

} //end class APListActivity
