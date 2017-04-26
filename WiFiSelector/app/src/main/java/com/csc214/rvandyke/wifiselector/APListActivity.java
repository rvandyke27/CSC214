package com.csc214.rvandyke.wifiselector;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class APListActivity extends AppCompatActivity {
    private static final String TAG = "APListActivity";

    private WifiManager mWifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplist);
        Log.d(TAG, "onCreate() called");
        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mWifiManager.setWifiEnabled(true);
        //wait for wifi to turn on
        while(!mWifiManager.isWifiEnabled()){}
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
} //end class APListActivity
