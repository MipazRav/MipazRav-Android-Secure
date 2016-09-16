package com.mipazrav.mipazrav;

/**
 * Created by joshuaegoldmeier on 9/5/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[] {"Shiurim", "Ask The Rabbi", "Links"};

    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentShiurim();

        } else if (position == 1) {
            return new FragmentAskTheRabbi();
        } else if (position == 2) {
            return new FragmentLinks();

        }
        return null;
    }


    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }



}

