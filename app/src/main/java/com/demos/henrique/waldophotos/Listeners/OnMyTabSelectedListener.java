package com.demos.henrique.waldophotos.Listeners;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;



public class OnMyTabSelectedListener implements TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;

    public OnMyTabSelectedListener(ViewPager viewPager)
    {
        this.viewPager = viewPager;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        viewPager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
