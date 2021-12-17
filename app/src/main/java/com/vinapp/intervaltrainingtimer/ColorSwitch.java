package com.vinapp.intervaltrainingtimer;

public class ColorSwitch{

    public ColorSwitch(int numberOfRounds, int timeOfWork, int timeOfRestBetweenRounds, boolean invertedColors) {
        this.numberOfRounds = numberOfRounds;
        this.timeOfWork = timeOfWork;
        this.timeOfRestBetweenRounds = timeOfRestBetweenRounds;
        this.invertedColors = invertedColors;
        this.remainingNumberOfRounds = numberOfRounds;
        this.trainingType = ONE_EXERCISE_TRAINING;
    }

    public ColorSwitch(int numberOfRounds, int numberOfExercises, int timeOfWork, int timeOfRestBetweenRounds, int timeOfRestBetweenExercises) {
        this.numberOfRounds = numberOfRounds;
        this.numberOfExercises = numberOfExercises;
        this.timeOfWork = timeOfWork;
        this.timeOfRestBetweenRounds = timeOfRestBetweenRounds;
        this.timeOfRestBetweenExercises = timeOfRestBetweenExercises;
        this.remainingNumberOfRounds = numberOfRounds;
        this.trainingType = SOME_EXERCISES_TRAINING;
    }

    private int numberOfRounds;
    private int numberOfExercises;
    private int timeOfWork;
    private int timeOfRestBetweenRounds;
    private int timeOfRestBetweenExercises;
    private int remainingNumberOfRounds;
    private boolean invertedColors;

    private int workCounter = 0;
    private int restBetweenExercisesCounter = 0;
    private int restBetweenRoundsCounter = 0;
    private int exercisesCounter = 0;

    private int color;

    private int trainingType; //0 for one exercise, 1 for some exercises
    private final int ONE_EXERCISE_TRAINING = 0;
    private final int SOME_EXERCISES_TRAINING = 1;

    //If in ActionActivity press on pause/resume button then here still execute toggleColor method. Need to fix it.
    public void toggleColor() {
        switch (trainingType) {
            case ONE_EXERCISE_TRAINING:
                    if (workCounter >= timeOfWork) {
                        if (restBetweenRoundsCounter >= timeOfRestBetweenRounds) {
                            //check for start from rest
                            if (!invertedColors) {
                                color = R.color.orange;
                            } else {
                                color = R.color.green;
                            }
                            workCounter = 1;
                            restBetweenRoundsCounter = 0;
                        } else {
                            //check for start from rest
                            if (!invertedColors) {
                                color = R.color.green;
                            } else {
                                color = R.color.orange;
                            }
                            restBetweenRoundsCounter++;
                        }
                    } else {
                        //check for start from rest
                        if (!invertedColors) {
                            color = R.color.orange;
                        } else {
                            color = R.color.green;
                        }
                        workCounter++;
                    }
                break;
            case SOME_EXERCISES_TRAINING:
                if (workCounter == timeOfWork) {
                    if (exercisesCounter == numberOfExercises - 1) {
                        color = R.color.green;
                        if (restBetweenRoundsCounter == timeOfRestBetweenRounds) {
                            color = R.color.orange;
                            exercisesCounter = 0;
                            workCounter = 1;
                            restBetweenRoundsCounter = 0;
                        } else {
                            restBetweenRoundsCounter++;
                        }
                    } else {
                        color = R.color.yellow;
                        if (restBetweenExercisesCounter == timeOfRestBetweenExercises) {
                            color = R.color.orange;
                            exercisesCounter++;
                            workCounter = 1;
                            restBetweenExercisesCounter = 0;
                        } else {
                            restBetweenExercisesCounter++;
                        }
                    }
                } else {
                    color = R.color.orange;
                    workCounter++;
                }
                break;
            default:
                break;
        }
    }

    public void setMenuColor() {
        color = R.color.white;
    }

    public int getColor() {
        toggleColor();
        return color;
    }

    public int getMenuColor() {
        color = R.color.white;
        return color;
    }

    public int getButtonIconColor() {
        int buttonIconColor = R.drawable.pause_white;
        switch (color) {
            case R.color.orange:
                buttonIconColor = R.drawable.pause_red;
                break;
            case R.color.green:
                buttonIconColor = R.drawable.pause_green;
                break;
            case R.color.yellow:
                buttonIconColor = R.drawable.pause_yellow;
                break;
            case R.color.white:
                buttonIconColor = R.drawable.done;
                break;
        }
        return buttonIconColor;
    }
}
