package com.csc214.rvandyke.assignment6application.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 6
 * TA:Julian Weiss
 */

public class BhangraTeam implements Comparable<BhangraTeam>{
    private String mName;
    private String mTown;
    private int mDateFounded; //founding year
    private String mDescription;
    private UUID mId;

    public BhangraTeam(String name, String town, String description, int founding){
        mName = name;
        mTown = town;
        mDateFounded = founding;
        mDescription = description;
        mId = UUID.randomUUID();
    }

    public String getName(){
        return mName;
    }

    public String getTown(){
        return mTown;
    }

    public String getDescription(){
        return mDescription;
    }

    public int getDateFounded(){
        return mDateFounded;
    }

    public UUID getId(){
        return mId;
    }

    @Override
    public String toString(){
        return getName() + ", founded in " + getDateFounded() + " in " + getTown();
    }

    @Override
    public int compareTo(@NonNull BhangraTeam o) {
        if(o.getName().equals(this.getName())){
            return 0;
        }
        if(o.getName().compareTo(this.getName()) < 0){
            return -1;
        }
        else{
            return 1;
        }
    }
} //end class
