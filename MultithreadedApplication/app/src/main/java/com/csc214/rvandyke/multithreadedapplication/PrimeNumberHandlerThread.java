package com.csc214.rvandyke.multithreadedapplication;

import android.app.ActivityOptions;
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

public class PrimeNumberHandlerThread extends HandlerThread {
    private static final String TAG = "Prime#HandlerThread";
    private static final int MESSAGE_CALCULATE_PRIME = 123;

    public interface PrimeProgressListener extends JobListener<String> {
    }

    private Handler mHandler;
    private Handler mResponseHandler;
    private PrimeProgressListener mListener;

    public PrimeNumberHandlerThread(Handler responseHandler){
        super(TAG);
        mResponseHandler = responseHandler;
    } //PrimeNumberHandlerThread()

    public void setPrimeProgressListener(PrimeProgressListener listener) {
        mListener = listener;
    } //setPrimeProgressListener()

    public void calculatePrime(String val){
        mHandler.obtainMessage(MESSAGE_CALCULATE_PRIME, val).sendToTarget();
    } //calculatePrime()

    @Override
    protected void onLooperPrepared() {
        mHandler = new PrimeNumberHandler(mResponseHandler, mListener);
    } //onLooperPrepared()

    private static class PrimeNumberHandler extends Handler {
        private final Handler mResponseHandler;
        private final PrimeProgressListener mListener;

        PrimeNumberHandler(Handler responseHandler, PrimeProgressListener listener) {
            mResponseHandler = responseHandler;
            mListener = listener;
        } //PrimeNumberHandler()

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MESSAGE_CALCULATE_PRIME) {
                try{
                    long output = 0;
                    Long val = Long.valueOf(msg.obj.toString());
                    for (long i = 0; i <= val; i++) {
                        if (Activity2.isPrime(i)) {
                            output = i;
                        }
                    }
                    mResponseHandler.post(new JobCompletePoster<String>(Double.toString(output), mListener));
                }
                catch(NumberFormatException e){
                    Log.e(TAG, "NumberFormatException");
                    mResponseHandler.post(new JobCompletePoster<String>("", mListener));
                }
            }
        }
    }

} //end class PrimeNumberHandlerThread
