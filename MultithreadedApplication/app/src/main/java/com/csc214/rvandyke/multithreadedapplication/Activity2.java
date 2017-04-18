package com.csc214.rvandyke.multithreadedapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA:Julian Weiss
 */

public class Activity2 extends AppCompatActivity {
    private static final String TAG = "Activity2";

    private EditText mLongInput;
    private TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_2);

        mLongInput = (EditText)findViewById(R.id.edit_text_long_input);
        mOutput = (TextView)findViewById(R.id.text_view_output);

        Button sqrtButton = (Button)findViewById(R.id.button_calculate_sqrt);
        sqrtButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Square Root Requested");
                //TODO
            }
        });

        Button primeButton = (Button)findViewById(R.id.button_calculate_prime);
        primeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Largest Prime Requested");
                //TODO
            }
        });

    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    } //onDestroy()

} //end class Activity2
