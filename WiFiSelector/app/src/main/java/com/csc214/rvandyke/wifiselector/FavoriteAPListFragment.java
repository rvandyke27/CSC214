package com.csc214.rvandyke.wifiselector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csc214.rvandyke.wifiselector.model.AccessPoint;
import com.csc214.rvandyke.wifiselector.model.FavoriteAPList;

import org.w3c.dom.Text;

import java.util.List;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class FavoriteAPListFragment extends Fragment {
    private static final String TAG = "FavAPListFragment";

    private static final String ARG_BSSID = "bssid";
    private static final String ARG_SSID = "ssid";

    private RecyclerView mRecyclerView;
    private FavoriteAPList mFavoriteAPList;
    private APAdapter mAdapter;
    private String mSSID;
    private String mBSSID;


    public FavoriteAPListFragment() {
        // Required empty public constructor
    } //FavoriteAPListFragment()

    public static FavoriteAPListFragment newInstance(String ssid, String bssid){
        Bundle args = new Bundle();
        args.putString(ARG_BSSID, bssid);
        args.putString(ARG_SSID, ssid);
        FavoriteAPListFragment fragment = new FavoriteAPListFragment();
        fragment.setArguments(args);
        Log.d(TAG, "newInstance() called with ssid=" + ssid + ", bssid=" + bssid);
        return fragment;
    } //newInstance()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_aplist, container, false);

        mFavoriteAPList = FavoriteAPList.get(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.favorite_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        mSSID = args.getString(ARG_SSID);
        mBSSID = args.getString(ARG_BSSID);
        updateUI();

        return view;
    } //onCreateView()

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    } //onResume()

    public void updateUI(){
        List<AccessPoint> apList = mFavoriteAPList.getFavoritedAPs();
        if(mAdapter == null){
            mAdapter = new FavoriteAPListFragment.APAdapter(apList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.update(apList);
        }
    } //updateUI()

    private class APAdapter extends RecyclerView.Adapter<APViewHolder>{
        private static final String TAG = "APAdapter";
        private List<AccessPoint> mAPList;

        public APAdapter(List<AccessPoint> apList){
            mAPList = apList;
        } //APAdapter()

        public void update(List<AccessPoint> apList){
            mAPList = apList;
            notifyDataSetChanged();
        } //update()

        @Override
        public int getItemCount(){
            return mAPList.size();
        } //getItemCount()

        @Override
        public APViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            return new APViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ap_view_holder, parent, false));
        } //onCreateViewHolder()

        @Override
        public void onBindViewHolder(APViewHolder holder, int position){
            AccessPoint ap = mAPList.get(position);
            Log.d(TAG, "onBindViewHolder() called on position " + position);
            holder.bind(ap);
        } //onBindViewHolder()

    } //end class APAdapter

    private class APViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNickname;
        private final TextView mSSID;
        private final TextView mNotes;
        private final Button mEdit;

        private AccessPoint mAP;

        public APViewHolder(View itemView){
            super(itemView);
            mNickname = (TextView)itemView.findViewById(R.id.text_view_nickname);
            mSSID = (TextView)itemView.findViewById(R.id.text_view_ssid);
            mNotes = (TextView)itemView.findViewById(R.id.text_view_notes);
            mEdit = (Button)itemView.findViewById(R.id.button_edit_entry);

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Launch edit fragment
                }
            });
        } //APViewHolder()

        public void bind(AccessPoint ap){
            mAP = ap;
            mNickname.setText(ap.getNickname());
            mSSID.setText(ap.getSSID());
            mNotes.setText(ap.getNotes());
        } //bind()
    } //end class APViewHolder

} //end class FavoriteAPListFragment
