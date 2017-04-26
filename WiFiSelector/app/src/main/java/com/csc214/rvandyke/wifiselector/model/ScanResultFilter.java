package com.csc214.rvandyke.wifiselector.model;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class ScanResultFilter {
    private static final String TAG = "ScanResultFilter";

    private WifiManager mWifiManager;
    private List<ScanResult> mAPList;
    private String mSSID;

    public ScanResultFilter(Context c, String SSID){
        mWifiManager = (WifiManager)c.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mSSID = SSID;
        updateScan();
    } //ScanResultFilter()

    public void updateScan(){
        ArrayList<ScanResult> unfiltered = (ArrayList)mWifiManager.getScanResults();
        mAPList = new ArrayList<ScanResult>();
        for(ScanResult s: unfiltered){
            if(s.SSID.equals(mSSID)){
                mAPList.add(s);
            }
        }
    } //updateScan()

    public List<ScanResult> getAccessPoints(){
        return mAPList;
    } //getAccessPoints()

    public int getAPSignalStrength(String BSSID, WifiManager wifiManager){
        int RSSI = -100;
        for(ScanResult s: mAPList){
            if(s.BSSID.equals(BSSID)){
                RSSI = s.level;
            }
        }
        return RSSI;
    }

} //end class
