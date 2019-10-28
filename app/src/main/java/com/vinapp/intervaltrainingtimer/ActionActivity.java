package com.vinapp.intervaltrainingtimer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class ActionActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView remainingTimeInfo;
    private TextView startTextInfo;
    private FloatingActionButton timerButton;

    private int numberOfRounds;
    private int numberOfExercises;
    private int timeOfWork;
    private int timeOfRestBetweenRounds;
    private int timeOfRestBetweenExercises;
    private int totalTime;
    private int remainingTime;
    private boolean pauseTimer; //if timer on pauseTimer = true, else pauseTimer = false
    private boolean finishTimer;
    private int oneSecond = 1000; //1 second is 1000 milliseconds
    private boolean startFromRest;
    private int delay;
    private int trainingType;
    private final int ONE_EXERCISE_TRAINING = 0;
    private final int SOME_EXERCISES_TRAINING = 1;
    private SharedPreferences currentSettings;
    private boolean soundOn;
    private String showingTime;

    private Training training;

    private ColorSwitch colorSwitch;
    private SoundSwitch soundSwitch;
    private SoundPool soundPool;
    private int sound;

    private final DecimalFormat showingFormat = new DecimalFormat("00");

    private CountDownTimer delayCountDownTimer;
    private CountDownTimer trainingCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        timerButton = findViewById(R.id.timerButton);
        remainingTimeInfo = findViewById(R.id.remainingTimeText);
        startTextInfo = findViewById(R.id.actionInfoTextView);

        timerButton.hide();
        currentSettings = PreferenceManager.getDefaultSharedPreferences(this);
        soundOn = currentSettings.getBoolean(getString(R.string.prefKeySoundOn), false);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        training = (Training) getIntent().getParcelableExtra("training");

        numberOfRounds = training.getNumberOfRounds();
        numberOfExercises = training.getNumberOfExercises();
        timeOfWork = training.getTimeOfWork();
        timeOfRestBetweenRounds = training.getTimeOfRestBetweenRounds();
        timeOfRestBetweenExercises = training.getTimeOfRestBetweenExercises();
        delay = training.getDelay();
        startFromRest = training.isStartFromRest();
        totalTime = training.getTotalTime();
        trainingType = training.getTrainingType();

        isStartFromRest();
        setSoundPool();

        switch (trainingType) {
            case ONE_EXERCISE_TRAINING:
                colorSwitch = new ColorSwitch(numberOfRounds, timeOfWork, timeOfRestBetweenRounds, startFromRest);
                soundSwitch = new SoundSwitch(numberOfRounds, timeOfWork, timeOfRestBetweenRounds);
                break;
            case SOME_EXERCISES_TRAINING:
                colorSwitch = new ColorSwitch(numberOfRounds, numberOfExercises, timeOfWork, timeOfRestBetweenRounds, timeOfRestBetweenExercises);
                soundSwitch = new SoundSwitch(numberOfRounds, numberOfExercises, timeOfWork, timeOfRestBetweenRounds, timeOfRestBetweenExercises);
                break;
            default:
                break;
        }

        startDelayTimer();
    }

    private int convertMillisecondsToSeconds(long timeInMilliseconds) {
        int timeInSeconds = (int) Math.ceil((double)timeInMilliseconds / oneSecond);
        return timeInSeconds;
    }

    private long convertSecondsToMilliseconds(int timeInSeconds) {
        long timeInMilliseconds = (long) timeInSeconds * oneSecond;
        return timeInMilliseconds;
    }

    private void changeBackgroundColor(int remainingTime) {
        if (remainingTime > 0) {
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, colorSwitch.getColor()));
        }
        else {
            // TODO: change getColor() for auto set menu color.
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, colorSwitch.getMenuColor()));
        }
    }

    private void changeButtonIconColor() {
        timerButton.setImageResource(colorSwitch.getButtonIconColor());
    }

    private void playSound() {
        if (soundSwitch.isPlaySound() && soundOn) {
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }
    }

    private void playSoundFinish(boolean isPlay) {
        if (soundOn) {
            soundPool.play(sound, 1, 1, 1, 2, 1);
        }
    }

    //sound play for delay timer
    private void playSound(boolean isPlay){
        if (soundOn){
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }
    }

    private void setSoundPool() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        sound = soundPool.load(this, R.raw.round_beep, 1);
    }

    private void isStartFromRest(){
        if (startFromRest && trainingType == ONE_EXERCISE_TRAINING){
            timeOfWork = training.getTimeOfRestBetweenRounds();
            timeOfRestBetweenRounds = training.getTimeOfWork();
        }
    }

    private void startDelayTimer(){

        //add 500ms to millisInFuture in CountDownTimer for fix CountDownTimer bug.
        //CountDownTimer skip last onTick on api < 26(?)
        delayCountDownTimer = new CountDownTimer(convertSecondsToMilliseconds(delay) + 500, oneSecond){
            @Override
            public void onTick(long timeUntilStart) {
                //subtract 200ms for compensate added 500 milliseconds for correct showing time in textView
                timeUntilStart = convertMillisecondsToSeconds(timeUntilStart - 500);
                // TODO: remake changeBackgroundColor for menuColor.
                changeBackgroundColor(0);
                remainingTimeInfo.setText(String.valueOf(timeUntilStart));
            }

            @Override
            public void onFinish() {
                playSound(true);
                startTimer(totalTime);
                startTextInfo.setVisibility(View.INVISIBLE);
            }
        }.start();
    }


    private void startTimer(final int startTime){
        //add 500ms to millisInFuture in CountDownTimer for fix CountDownTimer bug.
        //CountDownTimer skip last onTick on api < 26(?)
        trainingCountDownTimer = new CountDownTimer(convertSecondsToMilliseconds(startTime) + 500, oneSecond) {
            @Override
            public void onTick(long timeUntilFinished) {
                //subtract 200ms for compensate added 500 milliseconds for correct showing time in textView
                remainingTime = convertMillisecondsToSeconds(timeUntilFinished - 500);
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                showingTime = showingFormat.format(minutes) + ":" + showingFormat.format(seconds);
                if (remainingTime != 1) {
                    playSound();
                }
                changeBackgroundColor(remainingTime);
                changeButtonIconColor();
                remainingTimeInfo.setText(String.valueOf(showingTime));
            }

            @Override
            public void onFinish() {
                finishTimer = true;
                playSoundFinish(true);
                remainingTime = 0; //set zero value for set correctly color in changeBackgroundColor
                changeBackgroundColor(remainingTime);
                changeButtonIconColor();
                remainingTimeInfo.setText(R.string.finishInfo);
            }
        }.start();
        timerButton.show();
    }

    @Override
    public void onBackPressed() {
        DialogFragmentCloseActionActivity dialogFragmentCloseActionActivity = new DialogFragmentCloseActionActivity();
        dialogFragmentCloseActionActivity.show(getSupportFragmentManager(), "closeActionActivityDialog");
    }

    public void onTimerButtonClick(View view) {
        if (!finishTimer) {
            if (!pauseTimer) {
                if (trainingCountDownTimer != null) {
                    trainingCountDownTimer.cancel();
                    pauseTimer = true;
                    timerButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
                }
            } else {
                // TODO: remake ColorSwitch.
                //remainingTimer-- for temporary fix bug in logic of ColorSwitch. Need to remake logic in ColorSwitch.
                remainingTime--;
                startTimer(remainingTime);
                pauseTimer = false;
                timerButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pause));
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerButton.hide();
    }

    @Override
    public void finish() {
        soundPool.stop(sound);
        soundPool.release();
        if (delayCountDownTimer != null) {
            delayCountDownTimer.cancel();
        }
        if (trainingCountDownTimer != null){
            trainingCountDownTimer.cancel();
        }
        super.finish();
    }
}