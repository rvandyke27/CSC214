package com.csc214.rvandyke.soundapplication;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8
 * TA: Julian Weiss
 */

public class SoundLoader {
    private static final String TAG = "SoundLoader";
    private static final String AUDIO_FILE_FOLDER = "audio";

    private AssetManager mAssets;
    private List<SoundByte> mSounds;
    private SoundPool mSoundPool;

    public SoundLoader(Context context){
        mAssets = context.getAssets();
        mSounds = new ArrayList<>();
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        String[] trackNames;
        try{
            trackNames = mAssets.list(AUDIO_FILE_FOLDER);
            int i = 1;
            for(String filename : trackNames){
                String path = AUDIO_FILE_FOLDER + "/" + filename;
                SoundByte sound = new SoundByte(path, "SoundByte " + i, "By the Numbers", "Bobby");
                mSounds.add(sound);

                try{
                    AssetFileDescriptor afd = mAssets.openFd(sound.getPath());
                    int soundId = mSoundPool.load(afd, 1);
                    sound.setId(soundId);
                }
                catch(IOException e) {
                    Log.e(TAG, "load sound file failed", e);
                }
            }
        }
        catch(IOException e){
            Log.e(TAG, "load sound file failed", e);
        }
    } //SoundLoader()

    public void play(SoundByte sound){
        Integer id = sound.getId();
        if(id != null) {
            mSoundPool.play(
                    id,
                    1.0f,
                    1.0f,
                    1,
                    0,
                    1.0f
            );
        }
    } //play()

    public List<SoundByte> getSounds(){
        return mSounds;
    } //getSounds()

    public void release(){
        mSoundPool.release();
    } //release()

} //end class SoundLoader
