package com.vinapp.intervaltrainingtimer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPageAdapter extends FragmentPagerAdapter {

    int tabCounts;

    FragmentOneExercise fragmentOneExercise = new FragmentOneExercise();
    FragmentSomeExercise fragmentSomeExercise = new FragmentSomeExercise();
    FragmentSavedTimers fragmentSavedTimers = new FragmentSavedTimers();


    public SectionPageAdapter(FragmentManager fm, int tabCounts) {
        super(fm);
        this.tabCounts = tabCounts;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentOneExercise;
            case 1:
                return fragmentSomeExercise;
            case 2:
                return fragmentSavedTimers;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCounts;
    }

}
