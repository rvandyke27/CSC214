package com.csc214.rebeccavandyke.socialnetworkingproject2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDBSchema.PostTable;
import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDBSchema.UserTable;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class SocialNetworkDatabaseHelper extends SQLiteOpenHelper {
    public SocialNetworkDatabaseHelper(Context context) {
        super(context, SocialNetworkDBSchema.DATABASE_NAME, null, SocialNetworkDBSchema.VERSION);
    } //SocialNetworkDatabaseHelper()

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + UserTable.NAME
                + "(_id integer primary key autoincrement, "
                + UserTable.Cols.ID + ", "
                + UserTable.Cols.USERNAME + ", "
                + UserTable.Cols.PASSWORD + ", "
                + UserTable.Cols.FULLNAME + ", "
                + UserTable.Cols.EMAIL + ", "
                + UserTable.Cols.PHOTO + ", "
                + UserTable.Cols.BIRTHDATE + ", "
                + UserTable.Cols.HOMETOWN + ", "
                + UserTable.Cols.BIO + ", "
                + UserTable.Cols.USERS_FAVORITED + ")");

        db.execSQL("create table " + PostTable.NAME
                + "(_id integer primary key autoincrement, "
                + PostTable.Cols.ID + ", "
                + PostTable.Cols.USER + ", "
                + PostTable.Cols.POST_TEXT + ", "
                + PostTable.Cols.POST_PHOTO + ", "
                + PostTable.Cols.TIMESTAMP + ")");
    } //onCreate()

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    } //onUpgrade()
} //end class SocialNetworkDatabaseHelper
