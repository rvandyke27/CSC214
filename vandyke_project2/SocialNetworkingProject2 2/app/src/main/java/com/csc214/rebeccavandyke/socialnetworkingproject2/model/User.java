package com.csc214.rebeccavandyke.socialnetworkingproject2.model;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User implements Comparable<User>{
    private UUID mId;
    private String mUsername;
    private String mPassword;
    private String mEmail;
    private String mFullName;
    private Date mBirthdate;
    private String mHometown;
    private String mBio;
    private String mPhoto;
    private List<String> mUsersFavorited;

    public User() {
        this(UUID.randomUUID());
    }

    public User(UUID id){
        mId = id;
        mBirthdate = new Date();
        mUsername = "";
        mPassword = "";
        mFullName = "";
        mPhoto = "";
        mBio = "";
        mEmail = "";
        mHometown = "";
        mUsersFavorited = new ArrayList<>();
        mUsersFavorited.add("Admin");
    } //User()

    public int compareTo(User other){
        return mUsername.compareTo(other.getUsername());
    } //compareTo()

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getFullName(){
        return mFullName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Date getBirthdate() {
        return mBirthdate;
    }

    public String getStringBirthdate() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        return df.format(mBirthdate);
    }

    public void setBirthdate(Date mBirthdate) {
        this.mBirthdate = mBirthdate;
    }

    public void setStringBirthdate(String birthdate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date stringDate = df.parse(birthdate);
    }

    public String getHometown() {
        return mHometown;
    }

    public void setHometown(String mHometown) {
        this.mHometown = mHometown;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String mBio) {
        this.mBio = mBio;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public List<String> getUsersFavorited() {
        return mUsersFavorited;
    }

    public void setUsersFavorited(List<String> usersFavorited){
        mUsersFavorited = usersFavorited;
    }

    public String getFavoriteUserString(){
        String userList = "";
        for(String s: mUsersFavorited){
            userList += s + ",";
        }
        return userList;
    } //getFavoriteUserString()

    public void setUsersFavorited(String usersFavorited){
        mUsersFavorited = new ArrayList<String>(Arrays.asList(usersFavorited.split(",")));
    } //setUsersFavorited()

} //end class
