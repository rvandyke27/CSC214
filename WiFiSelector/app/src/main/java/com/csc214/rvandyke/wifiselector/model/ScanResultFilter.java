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
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanResultFilter {
    private static final String TAG = "ScanResultFilter";

    private WifiManager mWifiManager;
    private List<AccessPoint> mAPList;
    private FavoriteAPList mFavoritedAP;
    private String mSSID;

    public ScanResultFilter(WifiManager wifiManager, String SSID, Context c){
        mWifiManager = wifiManager;
        mSSID = SSID;
        mFavoritedAP = FavoriteAPList.get(c.getApplicationContext());
        updateScan(c);
    } //ScanResultFilter()

    public void updateScan(Context c){
        mFavoritedAP = FavoriteAPList.get(c.getApplicationContext());
        ArrayList<ScanResult> unfiltered = (ArrayList)mWifiManager.getScanResults();
        if(unfiltered==null){
            Log.d(TAG, "scan failed");
        }
        mAPList = new ArrayList<AccessPoint>();
        for(ScanResult s: unfiltered){
            if(s.SSID.equals(mSSID)){
                AccessPoint temp = new AccessPoint(s);
                if(mFavoritedAP.contains(s.BSSID)){
                    temp.setFavorited(true);
                }
                mAPList.add(temp);
            }
        }
        Collections.sort(mAPList);
    } //updateScan()

    public List<AccessPoint> getAccessPoints(){
        return mAPList;
    } //getAccessPoints()

    public int getAPSignalStrength(String BSSID, WifiManager wifiManager){
        int RSSI = -100;
        for(AccessPoint s: mAPList){
            if(s.getBSSID().equals(BSSID)){
                RSSI = s.getSignalLevel();
            }
        }
        return RSSI;
    }

} //end class
