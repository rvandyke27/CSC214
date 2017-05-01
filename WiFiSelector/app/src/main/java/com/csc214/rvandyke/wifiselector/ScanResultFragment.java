package com.csc214.rvandyke.wifiselector;


import android.Manifest;
import android.bluetooth.le.AdvertiseData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csc214.rvandyke.wifiselector.model.AccessPoint;
import com.csc214.rvandyke.wifiselector.model.ConnectionUpdatedListener;
import com.csc214.rvandyke.wifiselector.model.FavoriteAPList;
import com.csc214.rvandyke.wifiselector.model.ScanResultFilter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class ScanResultFragment extends Fragment implements ConnectionUpdatedListener{
    private static final String TAG = "ScanResultFragment";

    private static String ARG_SSID = "ssid";
    private static String ARG_BSSID = "bssid";

    private RecyclerView mRecyclerView;
    protected String mSSID;
    protected String mBSSID;
    private ScanResultAdapter mAdapter;
    private ScanResultFilter mScanFilter;
    private ScanReceiver mScanReceiver;
    protected WifiManager mWifiManager;
    private WifiConfiguration mActiveConfiguration;

    public ScanResultFragment() {
        // Required empty public constructor
    }

    public static ScanResultFragment newInstance(String ssid, String bssid){
        Bundle args = new Bundle();
        args.putString(ARG_SSID, ssid);
        args.putString(ARG_BSSID, bssid);
        ScanResultFragment fragment = new ScanResultFragment();
        fragment.setArguments(args);
        Log.d(TAG, "newInstance() called");
        return fragment;
    } //newInstance()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called");
        View view = inflater.inflate(R.layout.fragment_scan_result, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.scan_result_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        mSSID = args.getString(ARG_SSID);
        mBSSID = args.getString(ARG_BSSID);

        mWifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mScanFilter = new ScanResultFilter(mWifiManager, mSSID, getContext());

        for(WifiConfiguration wc: mWifiManager.getConfiguredNetworks()){
            if(wc.status == WifiConfiguration.Status.CURRENT){
                mActiveConfiguration = wc;
                break;
            }
        }

        mScanReceiver = new ScanReceiver();

        if(Build.VERSION.SDK_INT >= 23) {
            if ((getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) || (getActivity().checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE}, 13);
                Log.d(TAG, "requesting permissions");
            }
            else{
                mScanFilter.updateScan();
            }
        }

        return view;
    } //onCreateview()

    @Override
    public void onResume(){
        super.onResume();
        mScanFilter = new ScanResultFilter((WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE), mSSID, getContext());
        mScanFilter.updateScan();
    } //onResume

    @Override
    public void onStart(){
        super.onStart();

        IntentFilter i = new IntentFilter();
        i.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getActivity().registerReceiver(mScanReceiver, i);
        Log.d(TAG, "Receiver registered in onStart()");
    } //onStart()

    @Override
    public void onStop(){
        super.onStop();
        getActivity().unregisterReceiver(mScanReceiver);
        Log.d(TAG, "Receiver unregistered in onStop()");
    } //onStop()

    public boolean connectTo(String bssid){
        WifiConfiguration newWc = new WifiConfiguration();
        mBSSID = bssid;
        newWc.BSSID = bssid;
        newWc.SSID = mSSID;
        newWc.preSharedKey = mActiveConfiguration.preSharedKey;
        newWc.hiddenSSID = mActiveConfiguration.hiddenSSID;
        newWc.status = WifiConfiguration.Status.ENABLED;
        newWc.allowedAuthAlgorithms = mActiveConfiguration.allowedAuthAlgorithms;
        newWc.allowedGroupCiphers = mActiveConfiguration.allowedGroupCiphers;
        newWc.allowedKeyManagement = mActiveConfiguration.allowedKeyManagement;
        newWc.allowedPairwiseCiphers = mActiveConfiguration.allowedPairwiseCiphers;
        newWc.allowedProtocols = mActiveConfiguration.allowedProtocols;
        newWc.wepKeys = mActiveConfiguration.wepKeys;
        newWc.wepTxKeyIndex = mActiveConfiguration.wepTxKeyIndex;
        if(Build.VERSION.SDK_INT >= 18) {
            newWc.enterpriseConfig = mActiveConfiguration.enterpriseConfig;
        }
        if(Build.VERSION.SDK_INT >= 23) {
            newWc.roamingConsortiumIds = mActiveConfiguration.roamingConsortiumIds;
            newWc.providerFriendlyName = mActiveConfiguration.providerFriendlyName;
        }

        int netId = mWifiManager.addNetwork(newWc);
        Log.d(TAG, "Added network w/ id " + netId);
        return mWifiManager.enableNetwork(netId, true);
        //TODO: pass connection info back up to activity
    }

    private class ScanResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final String TAG = "ScanResultAdapter";
        private List<AccessPoint> mFilteredScanResults;

        public ScanResultAdapter(List<AccessPoint> filteredScanResults){
            mFilteredScanResults = filteredScanResults;
        } //ScanResultAdapter()

        public void updateScans(List<AccessPoint> filteredScanResults) {
            mFilteredScanResults = filteredScanResults;
            notifyDataSetChanged();
        } //update()

        //TODO: favorite

        //TODO: launch edit dialogfragment

        //TODO: connect

        //TODO: unfavorite

        @Override
        public int getItemCount(){
            return mFilteredScanResults.size();
        } //getItemCount()

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            Log.d(TAG, "onCreateViewHolder() called, type=" + viewType);
            switch(viewType){
                case 1: //favorited
                    return new FavoritedAPViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorited_ap_view_holder, parent, false));
                default: //case 0, not favorited
                    return new ScanResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_result_view_holder, parent, false));
            }
        } //onCreateViewHolder()

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            AccessPoint ap = mFilteredScanResults.get(position);
            if(ap.getBSSID().equals(mBSSID)){
                holder.itemView.setBackground(getResources().getDrawable(R.drawable.connected_ap_background));
                Log.d(TAG, "indicate active AP");
            }
            else{
                holder.itemView.setBackground(getResources().getDrawable(R.drawable.nonconnected_ap_background));
            }
            Log.d(TAG, "onBindViewHolder() called on position " + position);
            if(ap.isFavorited()){
                FavoritedAPViewHolder holder1 = (FavoritedAPViewHolder) holder;
                holder1.bind(ap);
            }
            else{
                ScanResultViewHolder holder0 = (ScanResultViewHolder) holder;
                holder0.bind(ap);
            }
        } //onBindViewHolder()

        @Override
        public int getItemViewType(int position) {
            if(mFilteredScanResults.get(position).isFavorited()){
                return 1;
            }
            else{
                return 0;
            }
        } //getItemViewType()

    } //end class ScanResultAdapter

    private class ScanResultViewHolder extends RecyclerView.ViewHolder{
        private final TextView mBSSID;
        private final TextView mRSSI;
        private final Button mConnectButton;
        private final Button mAddToFavoritesButton;
        private AccessPoint mScanResult;

        public ScanResultViewHolder(View itemView){
            super(itemView);
            mBSSID = (TextView)itemView.findViewById(R.id.text_view_bssid);
            mRSSI = (TextView)itemView.findViewById(R.id.text_view_signal_strength);
            mConnectButton = (Button)itemView.findViewById(R.id.button_connect);
            mAddToFavoritesButton = (Button)itemView.findViewById(R.id.button_add_to_favorites);

            mConnectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:update connection to this
                }
            });

            mAddToFavoritesButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //TODO: launch addtofavorites dialogfragment
                }
            });
        } //ScanResultViewHolder()

        public void bind(AccessPoint sr) {
            mScanResult = sr;
            Log.d(TAG, "binding " + sr.getBSSID() + ", RSSI " + sr.getSignalLevel());
            mBSSID.setText(sr.getBSSID());
            String ss = "Signal Strength: " + WifiManager.calculateSignalLevel(sr.getSignalLevel(), 100);
            mRSSI.setText(ss);
        } //bind()

    } //end class ScanResultViewHolder

    public class ScanReceiver extends BroadcastReceiver {
        public ScanReceiver(){
        }

        @Override
        public void onReceive(Context context, Intent intent){
            Log.d(TAG, "ScanReceiver onReceive() called");
            if(intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)) {
                Log.d(TAG, "onReceive() scan completed");
                List<ScanResult> scanResults = mWifiManager.getScanResults();
                mScanFilter.filterScans(scanResults);
                List<AccessPoint> filteredScans = mScanFilter.getAccessPoints();
                if (mAdapter == null) {
                    mAdapter = new ScanResultAdapter(filteredScans);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.updateScans(filteredScans);
                }
            }
        }

    } //end class ScanReceiver

} //end class
