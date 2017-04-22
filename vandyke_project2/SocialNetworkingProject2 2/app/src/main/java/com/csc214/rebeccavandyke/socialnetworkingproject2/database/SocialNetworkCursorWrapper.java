package com.csc214.rebeccavandyke.socialnetworkingproject2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.csc214.rebeccavandyke.socialnetworkingproject2.model.Post;
import com.csc214.rebeccavandyke.socialnetworkingproject2.model.User;
import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDBSchema.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class SocialNetworkCursorWrapper extends CursorWrapper {

    public SocialNetworkCursorWrapper(Cursor cursor){
        super(cursor);
    } //SocialNetworkCursorWrapper

    public User getUser() {
        UUID id = UUID.fromString(getString(getColumnIndex(UserTable.Cols.ID)));
        String username = getString(getColumnIndex(UserTable.Cols.USERNAME));
        String fullname = getString(getColumnIndex(UserTable.Cols.FULLNAME));
        String password = getString(getColumnIndex(UserTable.Cols.PASSWORD));
        String photo = getString(getColumnIndex(UserTable.Cols.PHOTO));
        Date birthdate = new Date(getLong(getColumnIndex(UserTable.Cols.BIRTHDATE)));
        String hometown = getString(getColumnIndex(UserTable.Cols.HOMETOWN));
        String email = getString(getColumnIndex(UserTable.Cols.EMAIL));
        String bio = getString(getColumnIndex(UserTable.Cols.BIO));
        String usersFavorited = getString(getColumnIndex(UserTable.Cols.USERS_FAVORITED));

        User user = new User(id);
        user.setBio(bio);
        user.setBirthdate(birthdate);
        user.setEmail(email);
        user.setHometown(hometown);
        user.setPassword(password);
        user.setUsername(username);
        user.setFullName(fullname);
        if(photo != null) {
            user.setPhoto(photo);
        }
        else{
            user.setPhoto("");
        }
        user.setUsersFavorited(usersFavorited);

        return user;
    } //getUser()

    public Post getPost(){
        UUID id = UUID.fromString(getString(getColumnIndex(PostTable.Cols.ID)));
        String user = getString(getColumnIndex(PostTable.Cols.USER));
        String text = getString(getColumnIndex(PostTable.Cols.POST_TEXT));
        String photo = getString(getColumnIndex(PostTable.Cols.POST_PHOTO));
        long timeStamp = getLong(getColumnIndex(PostTable.Cols.TIMESTAMP));

        Post post = new Post(id);
        post.setUser(user);
        post.setText(text);
        post.setImagePath(photo);
        post.setTimestamp(timeStamp);

        return post;
    } //getPost()
} //end class
