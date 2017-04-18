package com.csc214.rvandyke.multithreadedapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA:Julian Weiss
 */

public class Activity4 extends AppCompatActivity {
    private static final String TAG = "Activity4";

    private ImageView mDisplay;
    private EditText mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_4);

        mDisplay = (ImageView)findViewById(R.id.imageview_display);
        mURL = (EditText)findViewById(R.id.edittext_url);

        Button fetchImage = (Button)findViewById(R.id.button_fetch_image);
        fetchImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Fetch Image Requested");
                //TODO
            }
        });
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    } //onDestroy()

} //end class Activity4
