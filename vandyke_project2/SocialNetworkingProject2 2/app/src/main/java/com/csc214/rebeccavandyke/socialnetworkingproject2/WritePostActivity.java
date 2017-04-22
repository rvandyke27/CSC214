package com.csc214.rebeccavandyke.socialnetworkingproject2;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class WritePostActivity extends MenuActivity {
    private static final String TAG = "WritePostActivity";

    private WritePostFragment mWritePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle(getResources().getString(R.string.subtitle_write_post));


        Intent intent = getIntent();
        mActiveUser = intent.getStringExtra(MainActivity.KEY_USERNAME);
        Log.d(TAG, "Logged in to WritePostActivity as " + mActiveUser);

        mWritePost = WritePostFragment.newInstance(mActiveUser);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.write_post_frame, mWritePost, null)
                .commit();
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

} //end class WritePostActivity
