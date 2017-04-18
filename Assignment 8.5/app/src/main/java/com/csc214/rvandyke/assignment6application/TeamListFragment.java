package com.csc214.rvandyke.assignment6application;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8.5
 * TA:Julian Weiss
 */

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csc214.rvandyke.assignment6application.model.BhangraTeam;
import com.csc214.rvandyke.assignment6application.model.Roster;

import java.util.Collection;
import java.util.List;


public class TeamListFragment extends ListFragment {
    private static final String TAG="RvandykeAssignment6";

    private List<BhangraTeam> mTeams;

    public TeamListFragment() {
        // Required empty public constructor
    } //TeamListFragment()

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TeamListFragment created");
        getActivity().setTitle("Active Bhangra Teams");
        mTeams = Roster.get(getActivity()).getRoster();

        ArrayAdapter<BhangraTeam> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mTeams);
        setListAdapter(adapter);
    } //onCreate()

    @Override
    public void onListItemClick(ListView l, View view, int position, long id){
        super.onListItemClick(l, view, position, id);
        Log.d(TAG, "list item clicked");
        FragmentManager manager = getFragmentManager();
        TeamDescriptionDialog dialog = TeamDescriptionDialog.newInstance((BhangraTeam)l.getItemAtPosition(position));

        dialog.show(manager, "TeamDetail");
    } //onListItemClick()

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "TeamListFragment destroyed");
    } //onDestroy()
} //end class TeamListFragment
