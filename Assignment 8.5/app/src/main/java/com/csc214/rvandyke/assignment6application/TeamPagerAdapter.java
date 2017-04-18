package com.csc214.rvandyke.assignment6application;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Rebecca Van Dyke
 * rvandyke@u.rochester.edu
 * CSC 214 Assignment 8.5
 * TA:Julian Weiss
 */

public class TeamPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public TeamPagerAdapter(FragmentManager manager, List<Fragment> fragments){
        super(manager);
        mFragments = fragments;
    } //TeamPagerAdapter

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    } //getItem

    @Override
    public int getCount() {
        return mFragments.size();
    } //getCount()

} //end class TeamPagerAdapter
