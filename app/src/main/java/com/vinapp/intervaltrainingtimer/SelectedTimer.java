package com.vinapp.intervaltrainingtimer;

import androidx.fragment.app.Fragment;

public class SelectedTimer extends Fragment {

    private static SelectedTimer selectedTimer;
    private Training training;

    private SelectedTimer(Training training) {
        this.training = training;
    }

    public static SelectedTimer getInstance() {
        if (SelectedTimer.selectedTimer == null) {
            selectedTimer = new SelectedTimer(null);
        }
        return selectedTimer;
    }

    public void setSelectedTimerSettings(Training training) {
        this.training = training;
    }

    public Training getTraining() {
        return training;
    }
}
