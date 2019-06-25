package com.example.krazenn.mynews.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.krazenn.mynews.Controllers.Fragment.PageFragment;

public class PageAdapter extends FragmentPagerAdapter {


    //Default Constructor

    // 1 - Array of colors that will be passed to PageFragment
    private String[] title = {"Top stories", "Most Popular", "Business"};

    // 2 - Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);

    }

    @Override
    public int getCount() {
        return (title.length); // 3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        // 4 - Page to return
        return (PageFragment.newInstance(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}


