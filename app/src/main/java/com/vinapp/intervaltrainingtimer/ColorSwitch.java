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
                                color = R.color.colorOfAction;
                            } else {
                                color = R.color.colorOfRestBetweenRounds;
                            }
                            workCounter = 1;
                            restBetweenRoundsCounter = 0;
                        } else {
                            //check for start from rest
                            if (!invertedColors) {
                                color = R.color.colorOfRestBetweenRounds;
                            } else {
                                color = R.color.colorOfAction;
                            }
                            restBetweenRoundsCounter++;
                        }
                    } else {
                        //check for start from rest
                        if (!invertedColors) {
                            color = R.color.colorOfAction;
                        } else {
                            color = R.color.colorOfRestBetweenRounds;
                        }
                        workCounter++;
                    }
                break;
            case SOME_EXERCISES_TRAINING:
                if (workCounter == timeOfWork) {
                    if (exercisesCounter == numberOfExercises - 1) {
                        color = R.color.colorOfRestBetweenRounds;
                        if (restBetweenRoundsCounter == timeOfRestBetweenRounds) {
                            color = R.color.colorOfAction;
                            exercisesCounter = 0;
                            workCounter = 1;
                            restBetweenRoundsCounter = 0;
                        } else {
                            restBetweenRoundsCounter++;
                        }
                    } else {
                        color = R.color.colorOfRestBetweenExercises;
                        if (restBetweenExercisesCounter == timeOfRestBetweenExercises) {
                            color = R.color.colorOfAction;
                            exercisesCounter++;
                            workCounter = 1;
                            restBetweenExercisesCounter = 0;
                        } else {
                            restBetweenExercisesCounter++;
                        }
                    }
                } else {
                    color = R.color.colorOfAction;
                    workCounter++;
                }
                break;
            default:
                break;
        }
    }

    public void setMenuColor() {
        color = R.color.colorOfMenu;
    }

    public int getColor() {
        toggleColor();
        return color;
    }

    public int getMenuColor() {
        color = R.color.colorOfMenu;
        return color;
    }

    public int getButtonIconColor() {
        int buttonIconColor = R.drawable.pause;
        switch (color) {
            case R.color.colorOfAction:
                buttonIconColor = R.drawable.pause_action_color;
                break;
            case R.color.colorOfRestBetweenRounds:
                buttonIconColor = R.drawable.pause_rest_r_color;
                break;
            case R.color.colorOfRestBetweenExercises:
                buttonIconColor = R.drawable.pause_rest_e_color;
                break;
            case R.color.colorOfMenu:
                buttonIconColor = R.drawable.done;
                break;
        }
        return buttonIconColor;
    }
}
