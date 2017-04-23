package com.csc214.rvandyke.multithreadedapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private Button mFetchImage;

    private FetchImage mFetchImageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_4);

        mDisplay = (ImageView)findViewById(R.id.imageview_display);
        mURL = (EditText)findViewById(R.id.edittext_url);

        mFetchImageTask = new FetchImage();

        mFetchImage = (Button)findViewById(R.id.button_fetch_image);
        mFetchImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Fetch Image Requested");
                mFetchImage.setEnabled(false);
                mFetchImageTask.execute(mURL.getText().toString());
                mFetchImageTask = new FetchImage();
            }
        });
    } //onCreate()

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop called");
        mFetchImageTask.cancel(true);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    } //onDestroy()

    private class FetchImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result = null;
            try{
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                result = BitmapFactory.decodeStream(connection.getInputStream());
            }
            catch (IOException e){
                Log.e(TAG, "IOException thrown");
            }
            return result;
        } //doInBackground()

        @Override
        protected void onPostExecute(Bitmap result){
            mFetchImage.setEnabled(true);
            if(result != null) {
                mDisplay.setImageBitmap(result);
            }
            else{
                Toast.makeText(getApplicationContext(), "No Image Found", Toast.LENGTH_LONG).show();
            }
        } //onPostExecute()
    } //end class FetchImage

} //end class Activity4
