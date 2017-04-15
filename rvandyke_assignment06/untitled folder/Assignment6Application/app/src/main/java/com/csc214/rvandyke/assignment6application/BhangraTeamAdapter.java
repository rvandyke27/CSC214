package com.csc214.rvandyke.assignment6application;

import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.csc214.rvandyke.assignment6application.model.BhangraTeam;

import java.util.List;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 6
 * TA:Julian Weiss
 */

public class BhangraTeamAdapter extends RecyclerView.Adapter<BhangraTeam>{
    private List<BhangraTeam> mTeams;

    public BhangraTeamAdapter(List<BhangraTeam> teams){
        mTeams = teams;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    }

    @Override
    public int getItemCount(){
        return mTeams.size();
    }
}
