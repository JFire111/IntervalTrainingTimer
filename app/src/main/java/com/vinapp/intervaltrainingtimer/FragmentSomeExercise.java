package com.vinapp.intervaltrainingtimer;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class FragmentSomeExercise extends Fragment implements MainActivity.DataProviderSomeExercises {

    private Button numberOfRoundsButton;
    private Button numberOfExercisesButton;
    private Button timeOfWorkButton;
    private Button timeOfRestBetweenExercisesButton;
    private Button timeOfRestBetweenRoundsButton;

    private TextView numberOfRoundsTextView;
    private TextView numberOfExercisesTextView;
    private TextView timeOfWorkTextView;
    private TextView timeOfRestBetweenExercisesTextView;
    private TextView timeOfRestBetweenRoundsTextView;

    private boolean numberOfRoundIsChanged;
    private boolean numberOfExercisesIsChanged;
    private boolean timeOfWorkIsChanged;
    private boolean timeOfRestBetweenExercisesIsChanged;
    private boolean timeOfRestBetweenRoundsIsChanged;

    private TextView delayTextView;
    private int defaultDelayValue = 3;
    private int delay = defaultDelayValue;
    private SeekBar delaySeekBar;


    public FragmentSomeExercise() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_some_exercise, container, false);

        numberOfRoundsButton = view.findViewById(R.id.numberOfRoundsButtonSE);
        numberOfExercisesButton = view.findViewById(R.id.numberOfExercisesButtonSE);
        timeOfWorkButton = view.findViewById(R.id.timeOfWorkButtonSE);
        timeOfRestBetweenExercisesButton = view.findViewById(R.id.timeOfRestBetweenExercisesButtonSE);
        timeOfRestBetweenRoundsButton = view.findViewById(R.id.timeOfRestBetweenRoundsButtonSE);

        numberOfRoundsTextView = view.findViewById(R.id.numberOfRoundsTextView);
        numberOfExercisesTextView = view.findViewById(R.id.numberOfExercisesTextView);
        timeOfWorkTextView = view.findViewById(R.id.timeOfWorkTextView);
        timeOfRestBetweenExercisesTextView = view.findViewById(R.id.timeOfRestBetweenExercisesTextView);
        timeOfRestBetweenRoundsTextView = view.findViewById(R.id.timeOfRestBetweenRoundsTextView);

        numberOfRoundIsChanged = false;
        numberOfExercisesIsChanged = false;
        timeOfWorkIsChanged = false;
        timeOfRestBetweenExercisesIsChanged = false;
        timeOfRestBetweenRoundsIsChanged = false;

        delayTextView = view.findViewById(R.id.delayTextView);
        delayTextView.setText("Start in " + delay + "s");
        delaySeekBar = view.findViewById(R.id.delaySeekBar);
        delaySeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        numberOfRoundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA_TYPE = "number";
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                intent.putExtra("defaultValue", numberOfRoundsButton.getText());
                intent.putExtra("DATA_TYPE", DATA_TYPE);
                intent.putExtra("valueIsChanged", numberOfRoundIsChanged);
                intent.putExtra("title", numberOfRoundsTextView.getText());
                startActivityForResult(intent, 0);
            }
        });

        numberOfExercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA_TYPE = "number";
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                intent.putExtra("defaultValue", numberOfExercisesButton.getText());
                intent.putExtra("DATA_TYPE", DATA_TYPE);
                intent.putExtra("valueIsChanged", numberOfExercisesIsChanged);
                intent.putExtra("title", numberOfExercisesTextView.getText());
                startActivityForResult(intent, 1);
            }
        });

        timeOfWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA_TYPE = "time";
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                intent.putExtra("defaultValue", timeOfWorkButton.getText());
                intent.putExtra("DATA_TYPE", DATA_TYPE);
                intent.putExtra("valueIsChanged", timeOfWorkIsChanged);
                intent.putExtra("title", timeOfWorkTextView.getText());
                startActivityForResult(intent, 2);
            }
        });

        timeOfRestBetweenExercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA_TYPE = "time";
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                intent.putExtra("defaultValue", timeOfRestBetweenExercisesButton.getText());
                intent.putExtra("DATA_TYPE", DATA_TYPE);
                intent.putExtra("valueIsChanged", timeOfRestBetweenExercisesIsChanged);
                intent.putExtra("title", timeOfRestBetweenExercisesTextView.getText());
                startActivityForResult(intent, 3);
            }
        });

        timeOfRestBetweenRoundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA_TYPE = "time";
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                intent.putExtra("defaultValue", timeOfRestBetweenRoundsButton.getText());
                intent.putExtra("DATA_TYPE", DATA_TYPE);
                intent.putExtra("valueIsChanged", timeOfRestBetweenRoundsIsChanged);
                intent.putExtra("title", timeOfRestBetweenRoundsTextView.getText());
                startActivityForResult(intent, 4);
            }
        });

        return view;
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            delay = seekBar.getProgress();
            delayTextView.setText("Start in " + delay + "s");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String buttonText = data.getStringExtra("buttonText");
        boolean valueIsChanged = data.getBooleanExtra("ValueIsChanged", false);
        switch (requestCode) {
            case 0:
                numberOfRoundsButton.setText(buttonText);
                numberOfRoundIsChanged = valueIsChanged;
                break;
            case 1:
                numberOfExercisesButton.setText(buttonText);
                numberOfExercisesIsChanged = valueIsChanged;
                break;
            case 2:
                timeOfWorkButton.setText(buttonText);
                timeOfWorkIsChanged = valueIsChanged;
                break;
            case 3:
                timeOfRestBetweenExercisesButton.setText(buttonText);
                timeOfRestBetweenExercisesIsChanged = valueIsChanged;
                break;
            case 4:
                timeOfRestBetweenRoundsButton.setText(buttonText);
                timeOfRestBetweenRoundsIsChanged = valueIsChanged;
                break;
            default:
                break;
        }
    }

    @Override
    public int getNumberOfRounds() {
        return convertButtonValueToInt(numberOfRoundsButton);
    }

    @Override
    public int getNumberOfExercises() {
        return convertButtonValueToInt(numberOfExercisesButton);
    }

    @Override
    public int getTimeOfWork() {
        return convertButtonValueToInt(timeOfWorkButton);
    }

    @Override
    public int getTimeOfRestBetweenRounds() {
        return convertButtonValueToInt(timeOfRestBetweenRoundsButton);
    }

    @Override
    public int getTimeOfRestBetweenExercises() {
        return convertButtonValueToInt(timeOfRestBetweenExercisesButton);
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public void setNumberOfRounds(int numberOfRounds) {
        numberOfRoundsButton.setText(convertIntToButtonValue(numberOfRounds, numberOfRoundsButton));
        numberOfRoundIsChanged = true;
    }

    @Override
    public void setNumberOfExercises(int numberOfExercises) {
        numberOfExercisesButton.setText(convertIntToButtonValue(numberOfExercises, numberOfExercisesButton));
        numberOfExercisesIsChanged = true;
    }

    @Override
    public void setTimeOfWork(int timeOfWork) {
        timeOfWorkButton.setText(convertIntToButtonValue(timeOfWork, timeOfWorkButton));
        timeOfWorkIsChanged = true;
    }

    @Override
    public void setTimeOfRestBetweenRounds(int timeOfRestBetweenRounds) {
        timeOfRestBetweenRoundsButton.setText(convertIntToButtonValue(timeOfRestBetweenRounds, timeOfRestBetweenRoundsButton));
        timeOfRestBetweenRoundsIsChanged = true;
    }

    @Override
    public void setTimeOfRestBetweenExercises(int timeOfRestBetweenExercises) {
        timeOfRestBetweenExercisesButton.setText(convertIntToButtonValue(timeOfRestBetweenExercises, timeOfRestBetweenExercisesButton));
        timeOfRestBetweenExercisesIsChanged = true;
    }

    @Override
    public void setDelay(int delay) {
        this.delay = delay;
        this.delaySeekBar.setProgress(delay);
    }

    private int convertButtonValueToInt(Button button) {
        int value;
        int minutes;
        int seconds;
        String buttonValue;

        if (button.equals(numberOfRoundsButton) || button.equals(numberOfExercisesButton)){
            buttonValue = String.valueOf(button.getText());
            try {
                value = Integer.parseInt(buttonValue);
            } catch (NumberFormatException nfe) {
                value = 0;
            }
        } else {
            buttonValue = String.valueOf(button.getText()).replaceAll(":", "");

            try {
                minutes = Integer.parseInt(buttonValue.substring(0, 2));
                seconds = Integer.parseInt(buttonValue.substring(2));
                value = minutes * 60 + seconds;
            } catch (NumberFormatException nfe) {
                value = 0;
            }
        }
        return value;
    }

    private String convertIntToButtonValue(int intValue, Button button) {
        String value;
        int minutes;
        int seconds;

        if (button.equals(numberOfRoundsButton) || button.equals(numberOfExercisesButton)){
            value = String.valueOf(intValue);
        } else {
            minutes = intValue / 60;
            seconds = intValue % 60;
            if (minutes < 10) {
                value = "0" + minutes;
            } else {
                value = String.valueOf(minutes);
            }
            if (seconds < 10) {
                value += ":0" + seconds;
            } else {
                value += ":" + seconds;
            }
        }
        return value;
    }
}
