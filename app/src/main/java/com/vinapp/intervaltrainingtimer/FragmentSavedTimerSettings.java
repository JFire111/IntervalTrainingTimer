package com.vinapp.intervaltrainingtimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class FragmentSavedTimerSettings extends Fragment {

    private int numberOfSavedTimer;
    private int totalNumberOfSavedTimers;
    private String nameOfSavedTimer;
    private Training training;
    private TextView totalTimeTextView;
    private TextView savedTimerNameTextView;
    private TextView typeOfTimerTextView;
    private TextView numberOfSavedTimerTextView;
    private SharedPreferences sharedPreferences;
    private String savedTimer;
    private final String SAVED_TIMERS_COUNTER = "SAVED_TIMERS_COUNTER";

    public FragmentSavedTimerSettings(int numberOfSavedTimer) {
        this.numberOfSavedTimer = numberOfSavedTimer;
        savedTimer = "SAVED_TIMER_" + numberOfSavedTimer;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved_timer_settings, container, false);

        savedTimerNameTextView = view.findViewById(R.id.savedTimerNameTextView);
        typeOfTimerTextView = view.findViewById(R.id.typeOfTimerTextView);
        numberOfSavedTimerTextView = view.findViewById(R.id.numberOfSavedTimerTextView);
        totalTimeTextView = view.findViewById(R.id.totalTimeTextView);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(savedTimer, "");
        Training training = gson.fromJson(json, Training.class);
        totalNumberOfSavedTimers = sharedPreferences.getInt(SAVED_TIMERS_COUNTER, 0);

        switch (training.getTrainingType()) {
            case 0:
                typeOfTimerTextView.setText("One exercise");
                break;
            case 1:
                typeOfTimerTextView.setText("Some exercises");
                break;
            default:
                break;
        }

        numberOfSavedTimerTextView.setText(numberOfSavedTimer + "/" + totalNumberOfSavedTimers);
        totalTimeTextView.setText(String.valueOf(training.getTotalTime()));

        return view;
    }
}
