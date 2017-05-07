package com.csc214.rvandyke.wifiselector;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FavoriteAPListActivity extends MenuActivity implements FavoriteDialog.DialogDismissedListener{
    private static final String TAG = "FavAPListActivity";

    private FavoriteAPListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_favorite_aplist);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle("Favorited Access Points");

        mFragment = FavoriteAPListFragment.newInstance(mSSID, mBSSID);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.favorite_ap_frame, mFragment, null).commit();
    } //onCreate()

    @Override
    public void onChildDismissed(){
        mFragment.dialogDismissed();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
} //end class FavoriteAPListActivity
