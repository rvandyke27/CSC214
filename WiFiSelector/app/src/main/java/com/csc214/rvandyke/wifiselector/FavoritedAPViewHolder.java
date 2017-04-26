package com.csc214.rvandyke.wifiselector;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csc214.rvandyke.wifiselector.model.AccessPoint;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public class FavoritedAPViewHolder extends RecyclerView.ViewHolder {
    private final TextView mNickname;
    private final TextView mRSSI;
    private final TextView mNotes;
    private final Button mConnectButton;
    private final Button mEditButton;
    private final Button mUnfavoriteButton;

    private AccessPoint  mAccessPoint;

    public FavoritedAPViewHolder(View itemView) {
        super(itemView);
        mNickname = (TextView)itemView.findViewById(R.id.text_view_nickname);
        mRSSI = (TextView)itemView.findViewById(R.id.text_view_rssi);
        mNotes = (TextView)itemView.findViewById(R.id.text_view_notes);
        mConnectButton = (Button)itemView.findViewById(R.id.button_connect);
        mEditButton = (Button)itemView.findViewById(R.id.button_edit_entry);
        mUnfavoriteButton = (Button)itemView.findViewById(R.id.button_unfavorite);

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: connect to this AP
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: launch edit dialogfragment
            }
        });

        mUnfavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: remove from favorites
            }
        });
    } //FavoritedAPViewHolder()

    public void bind(AccessPoint ap){
        mAccessPoint = ap;
        mNickname.setText(ap.getNickname());
        mRSSI.setText(ap.getSignalLevel());
        mNotes.setText(ap.getNotes());
    } //bind()

} //end class
