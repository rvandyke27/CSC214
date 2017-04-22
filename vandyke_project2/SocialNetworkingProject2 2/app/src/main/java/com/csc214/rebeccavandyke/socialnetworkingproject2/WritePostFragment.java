package com.csc214.rebeccavandyke.socialnetworkingproject2;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.csc214.rebeccavandyke.socialnetworkingproject2.model.Post;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.PostCollection;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.User;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.UserCollection;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class WritePostFragment extends Fragment {
    private static final String TAG = "WritePostFragment";

    private static final int CAPTURE_IMAGE_RQ = 27;

    private static String ARG_USERNAME = "username";

    private static String KEY_POST_TEXT = "POST_TEXT";
    private static String KEY_POST_PHOTO = "POST_PHOTO";

    private EditText mEditPostText;
    private ImageView mPostPhoto;

    private User mActiveUser;
    private PostCollection mPostCollection;

    private String mPhotoPath;

    public WritePostFragment() {
        // Required empty public constructor
    } //WritePostFragment()

    public static WritePostFragment newInstance(String activeUser){
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, activeUser);
        WritePostFragment fragment = new WritePostFragment();
        fragment.setArguments(args);
        Log.d(TAG, "newInstance() called with user " + activeUser);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_post, container, false);

        mPostCollection = PostCollection.get(getContext());

        mPhotoPath = "";

        mEditPostText = (EditText)view.findViewById(R.id.edit_text_post_text);
        mPostPhoto = (ImageView)view.findViewById(R.id.image_view_post_image);

        Bundle args = getArguments();
        String activeUsername = args.getString(ARG_USERNAME);
        mActiveUser = UserCollection.get(getContext()).getUser(activeUsername);
        Log.d(TAG, "EditProfileFragment created for user " + mActiveUser.getUsername());

        if(savedInstanceState != null){
            mEditPostText.setText(savedInstanceState.getCharSequence(KEY_POST_TEXT));
            mPhotoPath = savedInstanceState.getString(KEY_POST_PHOTO);
            if(!mPhotoPath.equals("")){
                updatePicture();
            }
        }

        Button takePhotoButton = (Button)view.findViewById(R.id.button_launch_camera);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                File profilePictureFile = null;

                try {
                    profilePictureFile = createImageFile();
                } catch (IOException ex) {
                    Log.d(TAG, "Error creating file");
                }
                if (profilePictureFile != null) {
                    mPhotoPath = profilePictureFile.getAbsolutePath();
                    Log.d(TAG, "Picture saved to " + mPhotoPath);

                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                            "com.csc214.rebeccavandyke.socialnetworkingproject2.fileprovider",
                            profilePictureFile);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    startActivityForResult(intent, CAPTURE_IMAGE_RQ);
                }
            }
        });

        Button publishButton = (Button)view.findViewById(R.id.button_publish_post);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                post.setText(mEditPostText.getText().toString());
                post.setTimestamp(new Date().getTime());
                post.setUser(mActiveUser.getUsername());
                post.setImagePath(mPhotoPath);
                mPostCollection.addPost(post);
                getActivity().finish();
            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getActivity().finish();
            }
        });

        return view;
    } //onCreateView

    private File createImageFile() throws IOException {
        String filename = "IMG_" + mActiveUser.getUsername() + System.currentTimeMillis();

        File picturesDirectory = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(filename, ".jpg", picturesDirectory);

        mPhotoPath = image.getAbsolutePath();
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
        Bitmap propic = ImageHelper.getScaledBitmap(mPhotoPath, mPostPhoto);
        mPostPhoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mPostPhoto.setImageBitmap(propic);
    } //updatePicture()

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putCharSequence(KEY_POST_TEXT, mEditPostText.getText());
        savedInstanceState.putString(KEY_POST_PHOTO, mPhotoPath);
    } //onSaveInstanceState()

} //end class WritePostFragment
