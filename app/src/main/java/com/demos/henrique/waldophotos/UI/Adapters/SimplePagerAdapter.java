package com.demos.henrique.waldophotos.UI.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by henrique on 02-12-2016.
 */

public class SimplePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFrags;

    public SimplePagerAdapter(FragmentManager fm, List<Fragment> mFrags) {
        super(fm);
        this.mFrags = mFrags;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return mFrags.get(position);

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return "---";
    }
}


