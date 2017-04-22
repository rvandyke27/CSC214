package com.csc214.rebeccavandyke.socialnetworkingproject2;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csc214.rebeccavandyke.socialnetworkingproject2.model.User;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.UserCollection;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class EditProfileFragment extends Fragment{
    private static final String TAG = "EditProfileFragment";

    private static final int CAPTURE_IMAGE_RQ = 27;

    private static String ARG_USERNAME = "username";

    private static String KEY_USERNAME = "USERNAME";
    private static String KEY_FULL_NAME = "FULL_NAME";
    private static String KEY_EMAIL = "EMAIL";
    private static String KEY_HOMETOWN = "HOMETOWN";
    private static String KEY_BIO = "BIO";
    private static String KEY_BIRTHDATE = "BIRTHDATE";
    private static String KEY_PHOTO_PATH = "PHOTO_PATH";

    private TextView mUserName;
    private EditText mEditFullName;
    private TextView mEmail;
    private EditText mEditHometown;
    private EditText mEditBio;
    private TextView mBirthDateEntry;
    private ImageView mProfilePicture;

    private User mActiveUser;
    private UserCollection mUserCollection;

    public EditProfileFragment() {
        // Required empty public constructor
    } //EditProfileFragment()

    public static EditProfileFragment newInstance(String activeUser){
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, activeUser);
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        Log.d(TAG, "newInstance() called with user " + activeUser);
        return fragment;
    } //newInstance()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        mUserCollection = UserCollection.get(getContext());

        mUserName = (TextView)view.findViewById(R.id.text_view_username);
        mEditFullName = (EditText)view.findViewById(R.id.edit_full_name);
        mEmail = (TextView)view.findViewById(R.id.text_view_email);
        mEditHometown = (EditText)view.findViewById(R.id.edit_hometown);
        mEditBio = (EditText)view.findViewById(R.id.edit_bio);
        mBirthDateEntry = (TextView)view.findViewById(R.id.text_view_birthdate);
        mProfilePicture = (ImageView)view.findViewById(R.id.image_view_propic);

        Bundle args = getArguments();
        String activeUsername = args.getString(ARG_USERNAME);
        mActiveUser = mUserCollection.getUser(activeUsername);
        Log.d(TAG, "EditProfileFragment created for user " + mActiveUser.getUsername());

        if(savedInstanceState != null){
            mUserName.setText(savedInstanceState.getCharSequence(KEY_USERNAME));
            mEditFullName.setText(savedInstanceState.getCharSequence(KEY_FULL_NAME));
            mEmail.setText(savedInstanceState.getCharSequence(KEY_EMAIL));
            mEditHometown.setText(savedInstanceState.getCharSequence(KEY_HOMETOWN));
            mEditBio.setText(savedInstanceState.getCharSequence(KEY_BIO));
            mBirthDateEntry.setText(savedInstanceState.getCharSequence(KEY_BIRTHDATE));
            mActiveUser.setPhoto(savedInstanceState.getString(KEY_PHOTO_PATH));
        }
        else {
            mUserName.setText(mActiveUser.getUsername());
            if (!mActiveUser.getFullName().equals("")) {
                mEditFullName.setText(mActiveUser.getFullName());
            }
            mEmail.setText(mActiveUser.getEmail());
            if (!mActiveUser.getHometown().equals("")) {
                mEditHometown.setText(mActiveUser.getHometown());
            }
            if (!mActiveUser.getBio().equals("")) {
                mEditBio.setText(mActiveUser.getBio());
            }
            final DateFormat form = DateFormat.getDateInstance();
            mBirthDateEntry.setText(form.format(mActiveUser.getBirthdate()));
        }
        updatePicture();

        final DatePickerDialog setBirthdate = new DatePickerDialog(getContext());
        setBirthdate.getDatePicker().init(1995, 0, 1, new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day){
                Date birthday = new Date(year, month, day);
                mActiveUser.setBirthdate(birthday);
                mBirthDateEntry.setText(mActiveUser.getStringBirthdate());
            }
        });

        Button setDateButton = (Button)view.findViewById(R.id.button_update_birthday);
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBirthdate.show();
            }
        });

        Button takePhotoButton = (Button)view.findViewById(R.id.button_launch_camera);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                File profilePictureFile = null;

                try {
                    profilePictureFile = createImageFile();
                } catch(IOException ex){
                    Log.d(TAG, "Error creating file");
                }
                if(profilePictureFile != null) {
                    mActiveUser.setPhoto(profilePictureFile.getAbsolutePath());
                    Log.d(TAG, "Picture saved to " + mActiveUser.getPhoto());

                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                            "com.csc214.rebeccavandyke.socialnetworkingproject2.fileprovider",
                            profilePictureFile);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    startActivityForResult(intent, CAPTURE_IMAGE_RQ);
                }
            }
        });

        Button saveButton = (Button)view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActiveUser.setUsername(mUserName.getText().toString());
                mActiveUser.setFullName(mEditFullName.getText().toString());
                mActiveUser.setEmail(mEmail.getText().toString());
                mActiveUser.setHometown(mEditHometown.getText().toString());
                mActiveUser.setBio(mEditBio.getText().toString());

                Log.d(TAG, "Profile picture path on updateUser: " + mActiveUser.getPhoto());

                mUserCollection.updateUser(mActiveUser);
                Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_LONG).show();
                Intent data = new Intent();
                data.putExtra(MenuActivity.KEY_USERNAME, mActiveUser.getUsername());
                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });

        return view;

    } //onCreateView()

    private File createImageFile() throws IOException{
        String filename = "IMG_" + mActiveUser.getUsername();

        File picturesDirectory = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(filename, ".jpg", picturesDirectory);

        mActiveUser.setPhoto(image.getAbsolutePath());
        return image;
    } //createImageFile()

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAPTURE_IMAGE_RQ) {
            if(resultCode == Activity.RESULT_OK){
                updatePicture();
            }
        }
    } //onActivityResult()

    private void updatePicture(){
        Bitmap propic = ImageHelper.getScaledBitmap(mActiveUser.getPhoto(), mProfilePicture);
        mProfilePicture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mProfilePicture.setImageBitmap(propic);
    } //updatePicture()

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putCharSequence(KEY_USERNAME, mUserName.getText());
        savedInstanceState.putCharSequence(KEY_FULL_NAME, mEditFullName.getText());
        savedInstanceState.putCharSequence(KEY_EMAIL, mEmail.getText());
        savedInstanceState.putCharSequence(KEY_HOMETOWN, mEditHometown.getText());
        savedInstanceState.putCharSequence(KEY_BIO, mEditBio.getText());
        savedInstanceState.putCharSequence(KEY_BIRTHDATE, mBirthDateEntry.getText());
        savedInstanceState.putString(KEY_PHOTO_PATH, mActiveUser.getPhoto());

    } //onSaveInstanceState

} //end class EditProfileFragment
