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

public class UserListActivity extends MenuActivity {
    private final String TAG = "UserListActivity";

    private UserListFragment mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ActionBar ab = getSupportActionBar();
        ab.setSubtitle(getResources().getString(R.string.subtitle_user_list));

        Intent intent = getIntent();
        mActiveUser = intent.getStringExtra(MenuActivity.KEY_USERNAME);
        Log.d(TAG, "Logged in to MainActivity as " + mActiveUser);

        mUserList = UserListFragment.newInstance(mActiveUser);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.user_list_frame, mUserList, null)
                .commit();
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

} //end class UserListActivity
