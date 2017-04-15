package com.csc214.rvandyke.assignment6application;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 6
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TeamListFragment created");
        getActivity().setTitle("Active Bhangra Teams");
        mTeams = Roster.get(getActivity()).getRoster();

        ArrayAdapter<BhangraTeam> adapter = new ArrayAdapter<>(getActivity(), R.layout.view_team, mTeams);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id){
        super.onListItemClick(l, view, position, id);

        FragmentManager manager = getFragmentManager();
        TeamDescriptionDialog dialog = TeamDescriptionDialog.newInstance((BhangraTeam)l.getItemAtPosition(position));

        dialog.show(manager, "TeamDetail");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "TeamListFragment destroyed");
    }
}
