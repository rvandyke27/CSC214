package com.csc214.rvandyke.assignment6application;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8.5
 * TA:Julian Weiss
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RvandykeAssignment6";

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity created");

        mViewPager = (ViewPager)findViewById(R.id.view_pager_teams);

        TeamListFragment listFragment = new TeamListFragment();
        TeamRecyclerFragment recyclerFragment = new TeamRecyclerFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(listFragment);
        fragments.add(recyclerFragment);

        FragmentManager manager = getSupportFragmentManager();
        TeamPagerAdapter adapter = new TeamPagerAdapter(manager, fragments);

        mViewPager.setAdapter(adapter);
    } //onCreate
} //end class MainActivity
