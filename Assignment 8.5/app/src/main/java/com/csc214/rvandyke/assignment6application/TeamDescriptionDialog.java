package com.csc214.rvandyke.assignment6application;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csc214.rvandyke.assignment6application.R;
import com.csc214.rvandyke.assignment6application.model.BhangraTeam;

import org.w3c.dom.Text;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 6
 * TA:Julian Weiss
 */

public class TeamDescriptionDialog extends DialogFragment {
    private static final String ARG_NAME = "ARG_NAME";
    private static final String ARG_YEAR = "ARG_YEAR";
    private static final String ARG_CITY = "ARG_CITY";
    private static final String ARG_DESCRIPTION = "ARG_DESCRIPTION";

    public TeamDescriptionDialog() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_team_description_dialog, null);

        Bundle args = getArguments();
        String teamName = args.getString(ARG_NAME);
        TextView tvTeamName = (TextView)view.findViewById(R.id.tv_team_name);
        tvTeamName.setText(teamName);

        String teamCity = args.getString(ARG_CITY);
        TextView tvTeamCity = (TextView)view.findViewById(R.id.tv_team_hometown);
        tvTeamCity.setText(teamCity);

        int teamFounding = args.getInt(ARG_YEAR);
        TextView tvTeamFounding = (TextView)view.findViewById(R.id.tv_team_founding);
        tvTeamFounding.setText("Founded in " + teamFounding);

        String teamDescription = args.getString(ARG_DESCRIPTION);
        TextView tvTeamDescription = (TextView)view.findViewById(R.id.tv_description);
        tvTeamDescription.setText(teamDescription);

        return new AlertDialog.Builder(getActivity()).setView(view).setTitle("Team Details").create();
    } //onCreateDialog()

    public static TeamDescriptionDialog newInstance(BhangraTeam team){
        TeamDescriptionDialog dialog = new TeamDescriptionDialog();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, team.getName());
        args.putString(ARG_CITY, team.getTown());
        args.putInt(ARG_YEAR, team.getDateFounded());
        args.putString(ARG_DESCRIPTION, team.getDescription());
        dialog.setArguments(args);
        return dialog;
    }

}
