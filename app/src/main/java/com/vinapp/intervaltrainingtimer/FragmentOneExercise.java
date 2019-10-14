package com.vinapp.intervaltrainingtimer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class FragmentOneExercise extends Fragment implements MainActivity.DataProviderOneExercise {

    private Button numberOfRoundsButton;
    private Button timeOfWorkButton;
    private Button timeOfRestButton;
    private boolean numberOfRoundIsChanged;
    private boolean timeOfWorkIsChanged;
    private boolean timeOfRestIsChanged;

    private TextView numberOfRoundsTextView;
    private TextView timeOfWorkTextView;
    private TextView timeOfRestTextView;

    private Switch startFromRestSwitch;
    private boolean startFromRest;

    private TextView delayTextView;
    private int defaultDelayValue = 3;
    private int delay = defaultDelayValue;
    private SeekBar delaySeekBar;

    public FragmentOneExercise() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one_exercise, container, false);

        numberOfRoundsButton = view.findViewById(R.id.numberOfRoundsButtonOE);
        timeOfWorkButton = view.findViewById(R.id.timeOfWorkButtonOE);
        timeOfRestButton = view.findViewById(R.id.timeOfRestButtonOE);

        numberOfRoundsTextView = view.findViewById(R.id.numberOfRoundsTextView);
        timeOfWorkTextView = view.findViewById(R.id.timeOfWorkTextView);
        timeOfRestTextView = view.findViewById(R.id.timeOfRestTextView);

        numberOfRoundIsChanged = false;
        timeOfWorkIsChanged = false;
        timeOfRestIsChanged = false;

        delayTextView = view.findViewById(R.id.delayTextView);
        delayTextView.setText("Start in " + delay + "s");

        startFromRestSwitch = view.findViewById(R.id.startFromRestSwitch);
        isStartFromRest();

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

        timeOfWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA_TYPE = "time";
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                intent.putExtra("defaultValue", timeOfWorkButton.getText());
                intent.putExtra("DATA_TYPE", DATA_TYPE);
                intent.putExtra("valueIsChanged", timeOfWorkIsChanged);
                intent.putExtra("title", timeOfWorkTextView.getText());
                startActivityForResult(intent, 1);
            }
        });

        timeOfRestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA_TYPE = "time";
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                intent.putExtra("defaultValue", timeOfRestButton.getText());
                intent.putExtra("DATA_TYPE", DATA_TYPE);
                intent.putExtra("valueIsChanged", timeOfRestIsChanged);
                intent.putExtra("title", timeOfRestTextView.getText());
                startActivityForResult(intent, 2);
            }
        });

        return view;
    }

    private void isStartFromRest() {
        startFromRestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    startFromRest = true;
                } else {
                    startFromRest = false;
                }
            }
        });
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
                timeOfWorkButton.setText(buttonText);
                timeOfWorkIsChanged = valueIsChanged;
                break;
            case 2:
                timeOfRestButton.setText(buttonText);
                timeOfRestIsChanged = valueIsChanged;
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
    public int getTimeOfWork() {
        return convertButtonValueToInt(timeOfWorkButton);
    }

    @Override
    public int getTimeOfRestBetweenRounds() {
        return convertButtonValueToInt(timeOfRestButton);
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean getStartFromRest(){
        return startFromRest;
    }

    private int convertButtonValueToInt(Button button) {
        int value;
        int minutes;
        int seconds;
        String buttonValue;

        if (button.equals(numberOfRoundsButton)){
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

    @Override
    public void onResume() {
        super.onResume();
    }
}
