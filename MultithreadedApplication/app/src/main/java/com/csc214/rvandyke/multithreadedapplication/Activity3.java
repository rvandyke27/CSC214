package com.csc214.rvandyke.multithreadedapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA:Julian Weiss
 */

public class Activity3 extends AppCompatActivity {
    private static final String TAG = "Activity3";

    private EditText mLongInput;
    private TextView mOutput;

    private SqrtHandlerThread mSqrtHandler;
    private PrimeNumberHandlerThread mPrimeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_3);

        mLongInput = (EditText)findViewById(R.id.edit_text_long_input);
        mOutput = (TextView)findViewById(R.id.text_view_output);

        Handler responseHandler = new Handler();

        mSqrtHandler = new SqrtHandlerThread(responseHandler);
        mSqrtHandler.setSqrtProgressListener(new SqrtHandlerThread.SqrtProgressListener() {
            @Override
            public void jobComplete(String message) {
                if(!message.equals("")){
                    mOutput.setText(getString(R.string.sqrt) + message);
                    Toast.makeText(getApplicationContext(), getString(R.string.sqrt) + message, Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mSqrtHandler.start();
        mSqrtHandler.getLooper();
        Log.d(TAG, "Started Sqrt handler");

        mPrimeHandler = new PrimeNumberHandlerThread(responseHandler);
        mPrimeHandler.setPrimeProgressListener(new PrimeNumberHandlerThread.PrimeProgressListener() {
            @Override
            public void jobComplete(String message) {
                if(!message.equals("")){
                    mOutput.setText("Largest Prime = " + message);
                    Toast.makeText(getApplicationContext(), "Largest Prime = " + message, Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPrimeHandler.start();
        mPrimeHandler.getLooper();
        Log.d(TAG, "Started prime handler");


        Button sqrtButton = (Button)findViewById(R.id.button_calculate_sqrt);
        sqrtButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Square Root Requested");
                mSqrtHandler.calculateSqrt(mLongInput.getText().toString());
            }
        });

        Button primeButton = (Button)findViewById(R.id.button_calculate_prime);
        primeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Largest Prime Requested");
                mPrimeHandler.calculatePrime(mLongInput.getText().toString());
            }
        });
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    } //onDestroy()

} //end class Activity3
