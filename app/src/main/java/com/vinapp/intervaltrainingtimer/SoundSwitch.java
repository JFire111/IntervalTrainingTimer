package com.vinapp.intervaltrainingtimer;

public class SoundSwitch {

    public SoundSwitch(int numberOfRounds, int timeOfWork, int timeOfRestBetweenRounds) {
        trainingType = ONE_EXERCISE_TRAINING;
        this.numberOfRounds = numberOfRounds;
        this.timeOfWork = timeOfWork;
        this.timeOfRestBetweenRounds = timeOfRestBetweenRounds;
        this.remainingNumberOfRounds = numberOfRounds;
    }

    public SoundSwitch(int numberOfRounds, int numberOfExercises, int timeOfWork, int timeOfRestBetweenRounds, int timeOfRestBetweenExercises) {
        trainingType = SOME_EXERCISES_TRAINING;
        this.numberOfRounds = numberOfRounds;
        this.numberOfExercises = numberOfExercises;
        this.timeOfWork = timeOfWork;
        this.timeOfRestBetweenRounds = timeOfRestBetweenRounds;
        this.timeOfRestBetweenExercises = timeOfRestBetweenExercises;
        this.remainingNumberOfRounds = numberOfRounds;
    }

    private int numberOfRounds;
    private int numberOfExercises;
    private int timeOfWork;
    private int timeOfRestBetweenRounds;
    private int timeOfRestBetweenExercises;
    private int remainingNumberOfRounds;

    private boolean check = true;
    private int counter = 0;

    private int workCounter = 0;
    private int restBetweenExercisesCounter = 0;
    private int restBetweenRoundsCounter = 0;
    private int exercisesCounter = 0;

    private int trainingType; //0 for one exercise, 1 for some exercises
    private final int ONE_EXERCISE_TRAINING = 0;
    private final int SOME_EXERCISES_TRAINING = 1;

    public boolean isPlaySound() {
        boolean isPlay = false;
        switch (trainingType) {
            case ONE_EXERCISE_TRAINING: {
                if (check) {
                    if (counter == timeOfWork) {
                        check = false;
                        counter = 0;
                        isPlay = true;
                    } else {
                        isPlay = false;
                    }
                } else {
                    if (counter == timeOfRestBetweenRounds) {
                        check = true;
                        counter = 0;
                        isPlay = true;
                    } else {
                        isPlay = false;
                    }
                }
                counter++;
                return isPlay;
            }
            case SOME_EXERCISES_TRAINING: {
                if (check) {
                    if (workCounter == timeOfWork) {
                        check = false;
                        workCounter = 0;
                        exercisesCounter++;
                        isPlay = true;
                    } else {
                        workCounter++;
                        isPlay = false;
                    }
                } else {
                    if (exercisesCounter == numberOfExercises) {
                        restBetweenRoundsCounter++;
                        if (restBetweenRoundsCounter == timeOfRestBetweenRounds) {
                            check = true;
                            workCounter++;
                            exercisesCounter = 0;
                            restBetweenRoundsCounter = 0;
                            isPlay = true;
                        }
                    } else {
                        restBetweenExercisesCounter++;
                        if (restBetweenExercisesCounter == timeOfRestBetweenExercises) {
                            check = true;
                            workCounter++;
                            restBetweenExercisesCounter = 0;
                            isPlay = true;
                        }
                    }
                }
            }
                break;
            default:
                return isPlay;
        }
        return isPlay;
    }
}
