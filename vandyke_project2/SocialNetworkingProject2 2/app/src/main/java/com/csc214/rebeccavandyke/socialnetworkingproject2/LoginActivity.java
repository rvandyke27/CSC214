package com.csc214.rebeccavandyke.socialnetworkingproject2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csc214.rebeccavandyke.socialnetworkingproject2.model.UserCollection;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static String KEY_USERNAME = "USERNAME";
    private static String KEY_PASSWORD = "PASSWORD";
    private static String KEY_EMAIL = "EMAIL";
    private static String KEY_NEW_USERNAME = "NEW_USERNAME";
    private static String KEY_NEW_PASSWORD = "NEW_PASSWORD";

    private EditText mUsername;
    private EditText mPassword;
    private EditText mEmail;
    private EditText mNewUsername;
    private EditText mNewPassword;

    private UserCollection mUserCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate() called");
        mUserCollection = UserCollection.get(getApplicationContext());

        mUsername = (EditText)findViewById(R.id.edit_text_username);
        mPassword = (EditText)findViewById(R.id.edit_text_password);
        mEmail = (EditText)findViewById(R.id.edit_text_email);
        mNewUsername = (EditText)findViewById(R.id.edit_text_new_username);
        mNewPassword = (EditText)findViewById(R.id.edit_text_new_password);

        if(savedInstanceState != null){
            mUsername.setText(savedInstanceState.getCharSequence(KEY_USERNAME));
            mPassword.setText(savedInstanceState.getCharSequence(KEY_PASSWORD));
            mEmail.setText(savedInstanceState.getCharSequence(KEY_EMAIL));
            mNewUsername.setText(savedInstanceState.getCharSequence(KEY_NEW_USERNAME));
            mNewPassword.setText(savedInstanceState.getCharSequence(KEY_NEW_PASSWORD));
        }

        Button login = (Button)findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserCollection.checkCredentials(mUsername.getText().toString(), mPassword.getText().toString())) {
                    Intent data = new Intent(LoginActivity.this, MainActivity.class);
                    data.putExtra(KEY_USERNAME, mUsername.getText().toString());
                    Log.d(TAG, "Login successful");
                    startActivity(data);
                }
                else{
                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Login failed");
                }
            }
        });

        Button createAccount = (Button)findViewById(R.id.button_create_account);
        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mUserCollection.createAccount(mEmail.getText().toString(), mNewUsername.getText().toString(), mNewPassword.getText().toString())){
                    Intent data = new Intent(LoginActivity.this, MainActivity.class);
                    data.putExtra(MainActivity.KEY_USERNAME, mNewUsername.getText().toString());
                    Log.d(TAG, "Create account successful");
                    startActivity(data);
                }
                else{
                    Toast.makeText(LoginActivity.this, R.string.create_account_error, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Create account failed");
                }
            }
        });
    } //onCreate()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    } //onDestroy()

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putCharSequence(KEY_USERNAME, mUsername.getText());
        savedInstanceState.putCharSequence(KEY_PASSWORD, mPassword.getText());
        savedInstanceState.putCharSequence(KEY_EMAIL, mEmail.getText());
        savedInstanceState.putCharSequence(KEY_NEW_PASSWORD, mNewPassword.getText());
    } //onSaveInstanceState()

} //end class LoginActivity
