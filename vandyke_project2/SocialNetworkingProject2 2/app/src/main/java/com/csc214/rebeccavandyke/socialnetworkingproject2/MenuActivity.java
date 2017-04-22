package com.csc214.rebeccavandyke.socialnetworkingproject2;

import android.content.Intent;
import android.support.v4.app.NavUtils;
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

public abstract class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";

    public static String KEY_USERNAME = "USERNAME";
    protected String mActiveUser;

    private static final int RC_ACTIVE_USER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    } //onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        boolean handled;
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                handled=true;
                break;
            case R.id.menu_item_home:
                Log.d(TAG, "Main Feed launched from Menu");
                Intent mainFeed = new Intent(this, MainActivity.class);
                mainFeed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                mainFeed.putExtra(KEY_USERNAME, mActiveUser);
                startActivity(mainFeed);
                handled=true;
                break;
            case R.id.menu_item_write_post:
                Log.d(TAG, "Write Post launched from Menu");
                Intent writePost = new Intent(this, WritePostActivity.class);
                writePost.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                writePost.putExtra(KEY_USERNAME, mActiveUser);
                startActivity(writePost);
                handled=true;
                break;
            case R.id.menu_item_edit_profile:
                Log.d(TAG, "Edit Profile launched from Menu");
                Intent editProfile = new Intent(this, EditProfileActivity.class);
                editProfile.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                editProfile.putExtra(KEY_USERNAME, mActiveUser);
                startActivityForResult(editProfile, RC_ACTIVE_USER);
                handled=true;
                break;
            case R.id.menu_item_view_users:
                Log.d(TAG, "View Users launched from Menu");
                Intent userList = new Intent(this, UserListActivity.class);
                userList.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                userList.putExtra(KEY_USERNAME, mActiveUser);
                startActivity(userList);
                handled=true;
                break;
            case R.id.menu_item_sign_out:
                Log.d(TAG, "Signed out from Menu");
                Intent signOut = new Intent(this, LoginActivity.class);
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signOut);
                handled=true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }
        return handled;
    } //onOptionsItemSelected()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            mActiveUser = data.getStringExtra(KEY_USERNAME);
        }
    } //onActivityResult()

} //end class MenuActivity
