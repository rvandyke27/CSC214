package com.csc214.rvandyke.wifiselector;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 3
TA: Julian Weiss
 */

public abstract class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";

    protected WifiManager mWifiManager;
    protected String mSSID;
    protected String mBSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(mWifiManager.isWifiEnabled()) {
            mSSID = mWifiManager.getConnectionInfo().getSSID().replaceAll("^\"(.*)\"$", "$1");
            mBSSID = mWifiManager.getConnectionInfo().getBSSID().replaceAll("^\"(.*)\"$", "$1");
        }

    } //onCreate()

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.application_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        switch(item.getItemId()) {
            case R.id.menu_item_help:
                Log.d(TAG, "Help activity launched from Menu");
                Intent help = new Intent(this, HelpActivity.class);
                help.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(help);
                handled = true;
                break;
            case R.id.menu_item_favorites:
                Log.d(TAG, "Favorites list activity launched from Menu");
                Intent viewFavorites = new Intent(this, FavoriteAPListActivity.class);
                viewFavorites.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(viewFavorites);
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }
        return handled;
    } //onOptionsItemSelected()

} //end class MenuActivity
