package com.csc214.rvandyke.multithreadedapplication;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA:Julian Weiss
 */


public class SqrtHandlerThread extends HandlerThread {
    private static final String TAG="SqrtHandlerThread";
    private static final int MESSAGE_CALCULATE_SQRT = 427;

    public interface SqrtProgressListener extends JobListener<String>{}

    private Handler mHandler;
    private Handler mResponseHandler;
    private SqrtProgressListener mListener;

    public SqrtHandlerThread(Handler responseHandler){
        super(TAG);
        mResponseHandler = responseHandler;
    } //SqrtHandlerThread()

    public void setSqrtProgressListener( SqrtProgressListener listener) {
        mListener = listener;
    } //setSqrtProgressListener()

    @Override
    public void onLooperPrepared(){
        mHandler = new SqrtHandler(mResponseHandler, mListener);
    } //onLooperPrepared()

    public void calculateSqrt(String val){
        Message message = mHandler.obtainMessage(MESSAGE_CALCULATE_SQRT, val);

        message.sendToTarget();
    } //calculateSqrt()

    private static class SqrtHandler extends Handler {
        private final Handler mResponseHandler;
        private final SqrtProgressListener mListener;

        SqrtHandler(Handler responseHandler, SqrtProgressListener listener){
            mResponseHandler = responseHandler;
            mListener = listener;
        } //SqrtHandler()

        @Override
        public void handleMessage(Message msg){
            if(msg.what == MESSAGE_CALCULATE_SQRT){
                try{
                    Long val = Long.valueOf(msg.obj.toString());
                    mResponseHandler.post(new JobCompletePoster<String>(Double.toString(Math.sqrt(val)), mListener));
                }
                catch(NumberFormatException e){
                    Log.e(TAG, "NumberFormatException");
                    mResponseHandler.post(new JobCompletePoster<String>("", mListener));
                }
            }
        }
    } //end class SqrtHandler

} //end class SqrtHandlerThread
