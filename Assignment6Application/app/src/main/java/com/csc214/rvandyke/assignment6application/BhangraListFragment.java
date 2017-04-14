package com.csc214.rvandyke.assignment6application;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 6
 * TA:Julian Weiss
 */

public class BhangraListFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public BhangraListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bhangra_list, container, false);

        mRecyclerview = (RecyclerView)view.findViewById(R.id.recycler_view_teams);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        return inflater.inflate(R.layout.fragment_bhangra_list, container, false);
    }

}
