package com.csc214.rvandyke.assignment6application;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csc214.rvandyke.assignment6application.model.BhangraTeam;
import com.csc214.rvandyke.assignment6application.model.Roster;

import java.util.List;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8.5
 * TA:Julian Weiss
 */

public class TeamRecyclerFragment extends Fragment {
    private static final String TAG = "TeamRecyclerFragment";

    private RecyclerView mRecyclerView;

    public TeamRecyclerFragment() {
        // Required empty public constructor
    } //TeamRecyclerFragment()


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_recycler, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.team_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TeamAdapter adapter = new TeamAdapter(Roster.get(getActivity()).getRoster());
        mRecyclerView.setAdapter(adapter);
        return view;
    } //onCreateView()

    private class TeamAdapter extends RecyclerView.Adapter<TeamViewHolder>{
        private List<BhangraTeam> mTeams;

        public TeamAdapter(List<BhangraTeam> teams){
            mTeams = teams;
        } //TeamAdapter()

        @Override
        public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.team_view_holder, parent, false);
            return new TeamViewHolder(view);
        } //onCreateViewHolder()

        @Override
        public void onBindViewHolder(TeamViewHolder holder, int position){
            holder.bindTeam(mTeams.get(position));
        } //onBindViewHolder()

        @Override
        public int getItemCount(){
            return mTeams.size();
        } //getItemCount()
    } //end class TeamAdapter

    private class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTeamName;
        private TextView mTeamDetails;
        private BhangraTeam mTeam;

        public TeamViewHolder(View view){
            super(view);
            mTeamName = (TextView)view.findViewById(R.id.tv_team_name);
            mTeamDetails = (TextView)view.findViewById(R.id.tv_team_details);
        } //TeamViewHolder()

        public void bindTeam(BhangraTeam team){
            mTeamName.setText(team.getName());
            mTeamDetails.setText("Founded in " + team.getTown() + " in " + team.getDateFounded());
            mTeam = team;
        } //bindTeam()

        @Override
        public void onClick(View v){
            FragmentManager manager = getFragmentManager();
            TeamDescriptionDialog dialog = TeamDescriptionDialog.newInstance(mTeam);

            dialog.show(manager, "TeamDetail");
        }
    } //end class TeamViewHolder

} //end clas TeamRecyclerFragment
