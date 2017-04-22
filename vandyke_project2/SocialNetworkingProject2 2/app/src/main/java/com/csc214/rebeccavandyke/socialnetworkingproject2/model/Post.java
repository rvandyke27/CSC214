package com.csc214.rebeccavandyke.socialnetworkingproject2.model;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

import java.text.SimpleDateFormat;
import java.util.UUID;

public class Post implements Comparable<Post>{
    private UUID mId;
    private String mUser;
    private String mText;
    private String mImagePath;
    private long mTimestamp;

    @Override
    public int compareTo(Post other){
        //this is more recent than other
        if((mTimestamp - other.getTimestamp()) > 0){
            return -1;
        }
        else{
            return 1;
        }
    } //compareTo()

    public boolean containsImage(){
        return (!mImagePath.equals(""));
    } //containsImage()

    public Post(){
        this(UUID.randomUUID());
        mUser = "";
        mText = "";
        mImagePath = "";
        mTimestamp = 0;
    } //Post()

    public Post(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String getStringTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(mTimestamp);
    }

    public void setTimestamp(long mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

} //end class
