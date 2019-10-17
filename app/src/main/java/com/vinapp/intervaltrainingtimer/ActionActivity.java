package com.vinapp.intervaltrainingtimer;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class ActionActivity extends Activity {

    private ConstraintLayout constraintLayout;
    private TextView infoAboutRemainingTime;

    private int numberOfRounds;
    private int numberOfExercises;
    private int timeOfWork;
    private int timeOfRestBetweenRounds;
    private int timeOfRestBetweenExercises;
    private int totalTime;
    private int timeOfLap; //one lap is sum of one round and one rest
    private int oneSecond = 1000; //1 second is 1000 milliseconds
    private boolean startFromRest;
    private int delay;
    private int trainingType;
    private final int ONE_EXERCISE_TRAINING = 0;
    private final int SOME_EXERCISES_TRAINING = 1;

    Training training;

    ColorSwitch colorSwitch;
    SoundSwitch soundSwitch;
    SoundPool soundPool;
    private int sound;

    CountDownTimer delayCountDownTimer;
    CountDownTimer trainingCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        infoAboutRemainingTime = findViewById(R.id.remainingTimeText);

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
        int timeInSeconds = (int) timeInMilliseconds / oneSecond;
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
            // TODO: change getColor() for auto set menu color
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, colorSwitch.getMenuColor()));
        }
    }

    private void playSound() {
        if (soundSwitch.isPlaySound()) {
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }
    }

    private void playSound(boolean isPlay){
        if (isPlay){
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }
    }

    private void setSoundPool() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
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
        delayCountDownTimer = new CountDownTimer(convertSecondsToMilliseconds(delay), oneSecond){
            @Override
            public void onTick(long timeUntilStart) {
                timeUntilStart = convertMillisecondsToSeconds(timeUntilStart);
                // TODO: change changeBackgroundColor for menuColor
                changeBackgroundColor(0);
                // TODO: Add new text view for "ready title" and set visibility
                infoAboutRemainingTime.setText("Start\n   in\n   " + (timeUntilStart + 1));
            }

            @Override
            public void onFinish() {
                playSound(true);
                startTimer();
            }
        }.start();
    }

    private void startTimer(){
        trainingCountDownTimer = new CountDownTimer(convertSecondsToMilliseconds(totalTime), oneSecond) {
            int remainingTime = totalTime;

            @Override
            public void onTick(long timeUntilFinished) {
                playSound();
                changeBackgroundColor(remainingTime);
                infoAboutRemainingTime.setText("" + remainingTime);
                remainingTime--;
            }

            @Override
            public void onFinish() {
                playSound();
                changeBackgroundColor(remainingTime);
                infoAboutRemainingTime.setText("Finish");
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        if (delayCountDownTimer != null) {
            delayCountDownTimer.cancel();
        }
        if (trainingCountDownTimer != null){
            trainingCountDownTimer.cancel();
        }
        this.finish();
    }
}