package com.csc214.rvandyke.multithreadedapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA:Julian Weiss
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_main);

        Button launchActivity2 = (Button)findViewById(R.id.button_launch_activity_2);
        launchActivity2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "launched Activity2 from MainActivity");
                Intent activity2 = new Intent(MainActivity.this, Activity2.class);
                startActivity(activity2);
            }
        });

        Button launchActivity3 = (Button)findViewById(R.id.button_launch_activity_3);
        launchActivity3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "launched Activity3 from MainActivity");
                Intent activity3 = new Intent(MainActivity.this, Activity3.class);
                startActivity(activity3);
            }
        });

        Button launchActivity4 = (Button)findViewById(R.id.button_launch_activity_4);
        launchActivity4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "launched Activity4 from MainActivity");
                Intent activity4 = new Intent(MainActivity.this, Activity4.class);
                startActivity(activity4);
            }
        });
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    } //onDestroy()

} //end class MainActivity
