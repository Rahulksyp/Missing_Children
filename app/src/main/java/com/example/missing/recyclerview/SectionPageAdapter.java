package com.example.missing.recyclerview;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class   SectionPageAdapter extends FragmentPagerAdapter{

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                EXPLORE explore = new EXPLORE();
                return explore;

            case 1:
                REPORT report = new REPORT();
                return report;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Explore";
            case 1:
                return "Report";


            default:
                return null;
        }
    }
}
