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
import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDatabaseHelper;
import com.csc214.rebeccavandyke.socialnetworkingproject2.database.SocialNetworkDBSchema.PostTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;



public class PostCollection {
    private static final String TAG = "PostCollection";
    private static PostCollection sPostCollection;

    private Context mAppContext;
    private final SQLiteDatabase mDatabase;
    private final List<Post> mPostList;

    private PostCollection(Context appContext){
        mAppContext = appContext.getApplicationContext();

        mDatabase = new SocialNetworkDatabaseHelper(mAppContext).getWritableDatabase();
        mPostList = new ArrayList<>();

        if(isEmpty()) {
            Post welcomePost = new Post();
            welcomePost.setUser("Admin");
            welcomePost.setText("Empty feed? Try favoriting some other users!");
            welcomePost.setTimestamp(new Date().getTime());
            welcomePost.setImagePath("");
            addPost(welcomePost);
        }

    } //PostCollection()

    public static synchronized PostCollection get(Context c) {
        if(sPostCollection == null){
            sPostCollection = new PostCollection(c);
        }
        return sPostCollection;
    } //get()

    public boolean isEmpty(){
        List<Post> posts = getPostList();
        return posts.isEmpty();
    } //isEmpty()

    public List<Post> getPostList(){
        mPostList.clear();
        SocialNetworkCursorWrapper wrapper = queryPosts(null, null);

        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()) {
                Post post = wrapper.getPost();
                mPostList.add(post);
                wrapper.moveToNext();
            }
        }
        finally{
            wrapper.close();
        }
        return mPostList;
    } //getPostList()

    public List<Post> getNewsFeed(User activeUser){
        List<String> usersFavorited = activeUser.getUsersFavorited();

        List<Post> newsFeed = new ArrayList<Post>();
        SocialNetworkCursorWrapper wrapper = queryPosts(null, null);

        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()) {
                Post post = wrapper.getPost();
                if(usersFavorited.contains(post.getUser())){
                    newsFeed.add(post);
                }
                wrapper.moveToNext();
            }
        }
        finally{
            wrapper.close();
        }
        //sort posts chronologically
        Collections.sort(newsFeed);
        return newsFeed;
    } //getNewsFeed()

    private SocialNetworkCursorWrapper queryPosts(String where, String[] args){
        Cursor cursor = mDatabase.query(
                SocialNetworkDBSchema.PostTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return new SocialNetworkCursorWrapper(cursor);
    } //queryPosts()

    public void addPost(Post post){
        ContentValues values = getContentValues(post);
        mDatabase.insert(PostTable.NAME, null, values);
        Log.d(TAG, "addPost() called");
    } //addPost()

    private static ContentValues getContentValues(Post post){
        ContentValues values = new ContentValues();
        values.put(PostTable.Cols.ID, post.getId().toString());
        values.put(PostTable.Cols.USER, post.getUser());
        values.put(PostTable.Cols.POST_TEXT, post.getText());
        values.put(PostTable.Cols.POST_PHOTO, post.getImagePath());
        values.put(PostTable.Cols.TIMESTAMP, post.getTimestamp());

        return values;
    } //getContentValues()

} //end class
