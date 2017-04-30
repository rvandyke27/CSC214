package com.csc214.rvandyke.wifiselector;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";

    protected WifiManager mWifiManager;
    protected String mSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mSSID = mWifiManager.getConnectionInfo().getSSID().replaceAll("^\"(.*)\"$", "$1");
    } //onCreate()

} //end class MenuActivity
