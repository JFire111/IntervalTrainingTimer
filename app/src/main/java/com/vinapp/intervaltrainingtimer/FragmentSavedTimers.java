package com.vinapp.intervaltrainingtimer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSavedTimers extends Fragment {

    FragmentSavedTimerSettings fragmentSavedTimerSettings;

    private SharedPreferences sharedPreferences;
    private final String SAVED_TIMERS_COUNTER = "SAVED_TIMERS_COUNTER";
    int numberOfSavedTimers;

    public FragmentSavedTimers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved_timers, container, false);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (sharedPreferences.contains(SAVED_TIMERS_COUNTER)) {
            numberOfSavedTimers = sharedPreferences.getInt(SAVED_TIMERS_COUNTER, 0);
        }

        if (numberOfSavedTimers != 0) {
            for (int i = 1; i <= numberOfSavedTimers; i++) {
                fragmentSavedTimerSettings = new FragmentSavedTimerSettings(i);
                getFragmentManager().beginTransaction()
                        .add(R.id.fragmentSavedTimersContainer, fragmentSavedTimerSettings)
                        .commit();
            }
        }

        return view;
    }

}
