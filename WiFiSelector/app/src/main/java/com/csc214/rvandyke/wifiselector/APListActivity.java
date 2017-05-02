package com.csc214.rvandyke.wifiselector;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
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
        Log.d(TAG, "Connection to SSID: " + mSSID + ", BSSID: " + mBSSID);
        setContentView(R.layout.activity_aplist);
        Log.d(TAG, "onCreate() called");

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle(mSSID);

        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        Log.d(TAG, "Wifi state " + mWifiManager.getWifiState());
        //Log.d(TAG, "Wifi enabled");
        boolean t1 = mWifiManager.setWifiEnabled(false);
        Log.d(TAG, "did turn off wifi work? " + t1);
        Log.d(TAG, "Wifi turned off? " + mWifiManager.setWifiEnabled(false) + ", current wifi state: " + mWifiManager.getWifiState());
        //Log.d(TAG, "Wifi disabled");
        boolean t2 = mWifiManager.setWifiEnabled(true);
        Log.d(TAG, "did turn on wifi work? " + t2);
        Log.d(TAG, "Wifi state " + mWifiManager.getWifiState());

        //mScanResults = ScanResultFragment.newInstance(mSSID, mBSSID);
        //FragmentManager manager = getSupportFragmentManager();
        //manager.beginTransaction()
                //.replace(R.id.scan_list_frame, mScanResults, null)
                //.commit();

    } //onCreate()

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 13){
            for(int grantResult: grantResults) {
                if(grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

} //end class APListActivity
