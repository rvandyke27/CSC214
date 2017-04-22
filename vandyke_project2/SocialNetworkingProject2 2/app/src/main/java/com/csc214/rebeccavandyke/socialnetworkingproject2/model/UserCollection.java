package com.csc214.rebeccavandyke.socialnetworkingproject2.model;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkCursorWrapper;
import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDBSchema;
import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDBSchema.UserTable;
import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserCollection {
    private static final String TAG = "UserCollection";

    private static UserCollection sUserCollection;

    private final Context mContext;
    private final SQLiteDatabase mDatabase;
    private final List<User> mUserList;

    private UserCollection(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new SocialNetworkDatabaseHelper(mContext).getWritableDatabase();
        mUserList = new ArrayList<>();
    } //UserCollection()

    public static synchronized UserCollection get(Context c){
        if(sUserCollection == null){
            sUserCollection = new UserCollection(c);
        }
        return sUserCollection;
    } //get()

    public List<User> getUserList(){
        mUserList.clear();
        SocialNetworkCursorWrapper wrapper = queryUsers(null, null);

        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()) {
                User user = wrapper.getUser();
                mUserList.add(user);
                wrapper.moveToNext();
            }
        }
        finally{
            wrapper.close();
        }
        //alphabetize by username
        Collections.sort(mUserList);
        return mUserList;
    } //getUserList()

    public void addUser(User user){
        ContentValues values = getContentValues(user);
        mDatabase.insert(UserTable.NAME, null, values);
        Log.d(TAG, "added user " + user.getUsername() + " to database");
    } //addUser()

    public User getUser(String username){
        SocialNetworkCursorWrapper wrapper = queryUsers(null, null);
        User result = null;

        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                User user = wrapper.getUser();
                if(user.getUsername().equals(username)){
                    result = user;
                    break;
                }
                wrapper.moveToNext();
            }
        }
        finally{
            wrapper.close();
        }
        return result;
    } //getUser()

    public boolean createAccount(String email, String username, String password){
        getUserList();
        boolean credentialsAvailable = true;
        for(User u: mUserList){
            if((u.getEmail().equals(email)) || (u.getUsername().equals(username))){
                credentialsAvailable = false;
            }
        }

        if(email.contains("@") && credentialsAvailable){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setUsername(username);
            addUser(newUser);
        }

        return (email.contains("@") && credentialsAvailable);
    } //createAccount()

    public boolean checkCredentials(String username, String password){
        User user = getUser(username);
        if(user != null){
            if(user.getPassword().equals(password)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    } //checkCredentials()

    public void updateUser(User user){
        String id = user.getId().toString();
        ContentValues values = getContentValues(user);
        Log.d(TAG, "updated user " + user.getUsername() + " in database");
        mDatabase.update(UserTable.NAME,
                values,
                UserTable.Cols.ID + "=?",
                new String[]{id});
    } //updateUser()

    private SocialNetworkCursorWrapper queryUsers(String where, String[] args){
        Cursor cursor = mDatabase.query(
                SocialNetworkDBSchema.UserTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return new SocialNetworkCursorWrapper(cursor);
    } //queryUsers()

    private static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.ID, user.getId().toString());
        values.put(UserTable.Cols.USERNAME, user.getUsername());
        values.put(UserTable.Cols.PASSWORD, user.getPassword());
        values.put(UserTable.Cols.FULLNAME, user.getFullName());
        values.put(UserTable.Cols.EMAIL, user.getEmail());
        values.put(UserTable.Cols.BIRTHDATE, user.getBirthdate().getTime());
        values.put(UserTable.Cols.HOMETOWN, user.getHometown());
        values.put(UserTable.Cols.BIO, user.getBio());
        values.put(UserTable.Cols.PHOTO, user.getPhoto());
        values.put(UserTable.Cols.USERS_FAVORITED, user.getFavoriteUserString());

        return values;
    } //getContentValues()

} //end class
