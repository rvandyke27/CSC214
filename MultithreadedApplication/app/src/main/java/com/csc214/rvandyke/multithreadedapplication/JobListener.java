package com.csc214.rvandyke.multithreadedapplication;

/*
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA:Julian Weiss
 */

public interface JobListener<W> {
    public void jobComplete(W message);
}
