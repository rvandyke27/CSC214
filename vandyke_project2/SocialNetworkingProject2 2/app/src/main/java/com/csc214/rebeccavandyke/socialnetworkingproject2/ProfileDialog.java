package com.csc214.rebeccavandyke.socialnetworkingproject2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csc214.rebeccavandyke.socialnetworkingproject2.model.User;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.UserCollection;

import org.w3c.dom.Text;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class ProfileDialog extends DialogFragment {

    private static final String TAG = "ProfileDialog";

    private static String KEY_USERNAME = "USERNAME";
    private static String KEY_FULL_NAME = "FULL_NAME";
    private static String KEY_PROPIC = "PROPIC";
    private static String KEY_EMAIL = "EMAIL";
    private static String KEY_HOMETOWN = "HOMETOWN";
    private static String KEY_BIRTHDATE = "BIRTHDATE";
    private static String KEY_BIO = "BIO";

    public ProfileDialog() {
        // Required empty public constructor
    } //ProfileFragment()

    public static ProfileDialog newInstance(User user){
        ProfileDialog dialog = new ProfileDialog();

        Bundle args = new Bundle();
        args.putString(KEY_USERNAME, user.getUsername());
        args.putString(KEY_FULL_NAME, user.getFullName());
        args.putString(KEY_PROPIC, user.getPhoto());
        args.putString(KEY_EMAIL, user.getEmail());
        args.putString(KEY_HOMETOWN, user.getHometown());
        args.putString(KEY_BIRTHDATE, user.getStringBirthdate());
        args.putString(KEY_BIO, user.getBio());

        dialog.setArguments(args);

        Log.d(TAG, "newInstance() called for " + user.getUsername());

        return dialog;
    } //newInstance()

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, null);

        TextView username = (TextView)view.findViewById(R.id.text_view_username_dialog);
        TextView fullName = (TextView)view.findViewById(R.id.text_view_full_name_dialog);
        ImageView propic = (ImageView)view.findViewById(R.id.image_view_propic_dialog);
        TextView email = (TextView)view.findViewById(R.id.text_view_email_dialog);
        TextView hometown = (TextView)view.findViewById(R.id.text_view_hometown_dialog);
        TextView birthDate = (TextView)view.findViewById(R.id.text_view_birthdate_dialog);
        TextView bio = (TextView)view.findViewById(R.id.text_view_bio_dialog);
        Button closeButton = (Button)view.findViewById(R.id.button_close);

        Bundle args = getArguments();

        username.setText(args.getString(KEY_USERNAME));
        fullName.setText(args.getString(KEY_FULL_NAME));
        String photoPath = args.getString(KEY_PROPIC);
        Bitmap img = ImageHelper.getScaledBitmap(photoPath, propic);
        propic.setScaleType(ImageView.ScaleType.FIT_CENTER);
        propic.setImageBitmap(img);
        email.setText(args.getString(KEY_EMAIL));
        hometown.setText(args.getString(KEY_HOMETOWN));
        birthDate.setText(args.getString(KEY_BIRTHDATE));
        bio.setText(args.getString(KEY_BIO));

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .create();
    } //onCreateDialog()


} //end class ProfileFragment
