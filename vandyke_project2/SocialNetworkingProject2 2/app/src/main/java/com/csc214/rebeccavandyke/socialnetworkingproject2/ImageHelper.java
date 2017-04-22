package com.csc214.rebeccavandyke.socialnetworkingproject2;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class ImageHelper {
    private static final String TAG = "ImageHelper";

    public static Bitmap getScaledBitmap(String path, View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int size = 1;
        if(srcHeight > height || srcWidth > width) {
            if(srcWidth > srcHeight){
                size = Math.round(srcHeight/height);
            }
            else{
                size = Math.round(srcWidth/width);
            }
        }

        BitmapFactory.Options scaled = new BitmapFactory.Options();
        scaled.inSampleSize = size;

        return BitmapFactory.decodeFile(path);
    } //getScaledBitmap()

} //end class ImageHelper()
