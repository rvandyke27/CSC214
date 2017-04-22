package com.csc214.rebeccavandyke.socialnetworkingproject2;


import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class MainActivity extends MenuActivity {
    private static final String TAG = "MainActivity";

    private NewsFeedFragment mNewsFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle(getResources().getString(R.string.subtitle_main_feed));

        Intent intent = getIntent();
        mActiveUser = intent.getStringExtra(MenuActivity.KEY_USERNAME);
        Log.d(TAG, "Logged in to MainActivity as " + mActiveUser);

        mNewsFeed = NewsFeedFragment.newInstance(mActiveUser);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.news_feed_frame, mNewsFeed, null)
                .commit();
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

} //end class MainActivity
