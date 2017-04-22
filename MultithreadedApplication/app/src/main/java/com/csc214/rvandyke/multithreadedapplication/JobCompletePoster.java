package com.csc214.rvandyke.multithreadedapplication;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA:Julian Weiss
 */

public class JobCompletePoster<W> implements Runnable {
    private final JobListener<W> mListener;
    private final W mMessage;

    public JobCompletePoster(W message, JobListener<W> listener) {
        mListener = listener;
        mMessage = message;
    } //JobCompletePoster

    @Override
    public void run(){
        mListener.jobComplete(mMessage);
    }
} //end class JobCompletePoster
