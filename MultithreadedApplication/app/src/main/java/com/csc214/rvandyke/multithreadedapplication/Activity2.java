package com.csc214.rvandyke.multithreadedapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                new CalculateSqrt().execute(mLongInput.getText().toString());
            }
        });

        Button primeButton = (Button)findViewById(R.id.button_calculate_prime);
        primeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Largest Prime Requested");
                new findPrime().execute(mLongInput.getText().toString());
            }
        });

    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    } //onDestroy()

    private class CalculateSqrt extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){
            Log.d(TAG, getString(R.string.calculatesqrt));
            Long val = Long.valueOf(0);
            try{
                val = Long.valueOf(params[0]);
            } catch(NumberFormatException e){
                return "";
            }
            if(val >= 2) {
                return Double.toString(Math.sqrt(val));
            }
            else{
                return "";
            }
        } //doInBackground()

        @Override
        protected void onPostExecute(String s){
            if(!s.equals("")) {
                mOutput.setText(getString(R.string.sqrt) + s);
            }
            else{
                Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_LONG).show();
            }
        } //onPostExecute()

    } //end class CalculateSqrt

    private class findPrime extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params){
            long output = 0;
            long val = 0;

            try{
                val = Long.valueOf(params[0]);
            } catch(NumberFormatException e) {
                return "";
            }
            if(val >= 2) {
                for (long i = 0; i <= val; i++) {
                    if (isPrime(i)) {
                        output = i;
                    }
                }
                return Double.toString(output);
            }
            else{
                return "";
            }
        } //doInBackground()

        @Override
        protected void onPostExecute(String s){
            if(!s.equals("")) {
                mOutput.setText(getString(R.string.largestPrime) + s);
            }
            else{
                Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_LONG).show();
            }
        } //onPostExecute()
    } //end class findPrime

    public boolean isPrime(long n){
        if(n%2 == 0){
            return false;
        }
        else{
            for(int i=3; i*i<=n; i +=2){
                if(n%i==0){
                    return false;
                }
            }
            return true;
        }
    } //isPrime()

} //end class Activity2
