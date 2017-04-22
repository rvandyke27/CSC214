package com.csc214.rebeccavandyke.socialnetworkingproject2;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class EditProfileActivity extends MenuActivity {
    private static final String TAG = "EditProfileActivity";

    private EditProfileFragment mEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle(getResources().getString(R.string.subtitle_edit_profile));

        Intent intent = getIntent();
        mActiveUser = intent.getStringExtra(MainActivity.KEY_USERNAME);
        Log.d(TAG, "Logged in to EditProfileActivity as " + mActiveUser);

        mEditProfile = EditProfileFragment.newInstance(mActiveUser);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.edit_profile_frame, mEditProfile, null)
                .commit();
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

} //end class EditProfileActivity
