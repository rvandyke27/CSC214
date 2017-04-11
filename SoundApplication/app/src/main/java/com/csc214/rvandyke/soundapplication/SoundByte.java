package com.csc214.rvandyke.soundapplication;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8
 * TA: Julian Weiss
 */

public class SoundByte {
    private final String mPath;
    private final String mName;
    private final String mAlbum;
    private final String mArtist;
    private Integer mId;

    public SoundByte(String path, String name, String album, String artist) {
        mPath = path;
        mName = name;
        mAlbum = album;
        mArtist = artist;
    } //SoundByte()

    public String getPath(){
        return mPath;
    } //getPath()

    public String getName(){
        return mName;
    }

    public String getAlbum(){
        return mAlbum;
    }

    public String getArtist(){
        return mArtist;
    }

    public Integer getId(){
        return mId;
    }

    public void setId(Integer id){
        mId = id;
    }

} //end class SoundByte
