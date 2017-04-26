package com.csc214.rvandyke.wifiselector;


import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csc214.rvandyke.wifiselector.model.AccessPoint;

import org.w3c.dom.Text;

import java.util.List;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class ScanResultFragment extends Fragment {
    private static final String TAG = "ScanResultFragment";

    public ScanResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called");
        return inflater.inflate(R.layout.fragment_scan_result, container, false);
    } //onCreateview()

    private class ScanResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final String TAG = "ScanResultAdapter";
        private List<AccessPoint> mScanResults;
        private List<AccessPoint> mFavoritedAPs;

        public ScanResultAdapter(List<AccessPoint> scanResults, List<AccessPoint> favoritedAPs){
            mScanResults = scanResults;
            mFavoritedAPs = favoritedAPs;

        }
    }

    private class ScanResultViewHolder extends RecyclerView.ViewHolder{
        private final TextView mBSSID;
        private final TextView mRSSI;
        private final Button mConnectButton;
        private final Button mAddToFavoritesButton;
        private AccessPoint mScanResult;

        public ScanResultViewHolder(View itemView){
            super(itemView);
            mBSSID = (TextView)itemView.findViewById(R.id.text_view_bssid);
            mRSSI = (TextView)itemView.findViewById(R.id.text_view_rssi);
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
            mBSSID.setText(sr.getBSSID());
            mRSSI.setText(sr.getSignalLevel());
        } //bind()

    } //end class ScanResultViewHolder

} //end class
