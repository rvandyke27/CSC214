package com.csc214.rvandyke.wifiselector;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
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

    private ScanResultFragment mScanResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplist);
        Log.d(TAG, "onCreate() called");

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle(mSSID);

        mWifiManager.setWifiEnabled(true);
        //wait for wifi to turn on
        while(!mWifiManager.isWifiEnabled()){}
        Log.d(TAG, "Wifi enabled");

        mScanResults = ScanResultFragment.newInstance(mSSID);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.scan_list_frame, mScanResults, null)
                .commit();

    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

} //end class APListActivity
