package com.vinapp.intervaltrainingtimer;

import android.os.Parcel;
import android.os.Parcelable;

//Training is a settings of timer
public class Training implements Parcelable {

    private int numberOfRounds;
    private int numberOfExercises;
    private int timeOfRestBetweenExercises;
    private int timeOfWork;
    private int timeOfRestBetweenRounds;
    private int totalTime;
    private int delay;
    private boolean startFromRest;
    private String trainingName; //Not used, needed for future versions

    private int trainingType; //0 for one exercise, 1 for some exercises
    private final int ONE_EXERCISE_TRAINING = 0;
    private final int SOME_EXERCISES_TRAINING = 1;

    public Training(int numberOfRounds, int timeOfWork, int timeOfRest, int delay, boolean startFromRest){
        trainingType = ONE_EXERCISE_TRAINING;
        this.numberOfRounds = numberOfRounds;
        this.timeOfWork = timeOfWork;
        this.timeOfRestBetweenRounds = timeOfRest;
        this.delay = delay;
        this.startFromRest = startFromRest;
    }

    public Training(int numberOfRounds, int numberOfExercises, int timeOfWork, int timeOfRestBetweenExercises, int timeOfRestBetweenRounds, int delay){
        trainingType = SOME_EXERCISES_TRAINING;
        this.numberOfRounds = numberOfRounds;
        this.numberOfExercises = numberOfExercises;
        this.timeOfWork = timeOfWork;
        this.timeOfRestBetweenExercises = timeOfRestBetweenExercises;
        this.timeOfRestBetweenRounds = timeOfRestBetweenRounds;
        this.delay = delay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(trainingType);
        parcel.writeInt(numberOfRounds);
        parcel.writeInt(numberOfExercises);
        parcel.writeInt(timeOfRestBetweenExercises);
        parcel.writeInt(timeOfWork);
        parcel.writeInt(timeOfRestBetweenRounds);
        parcel.writeInt(delay);
        parcel.writeByte((byte) (startFromRest ? 1 : 0));
        parcel.writeInt(totalTime);
        parcel.writeString(trainingName);
    }

    public static final Parcelable.Creator<Training> CREATOR = new Parcelable.Creator<Training>() {
        @Override
        public Training createFromParcel(Parcel parcel) {
            int trainingType = parcel.readInt();
            int numberOfRounds = parcel.readInt();
            int numberOfExercises = parcel.readInt();
            int timeOfRestBetweenExercises = parcel.readInt();
            int timeOfWork = parcel.readInt();
            int timeOfRestBetweenRounds = parcel.readInt();
            int delay = parcel.readInt();
            boolean startFromRest = parcel.readByte() != 0;
            int totalTime = parcel.readInt();
            String trainingName = parcel.readString();

            switch (trainingType) {
                case 0:
                    return new Training(numberOfRounds, timeOfWork, timeOfRestBetweenRounds, delay, startFromRest);
                case 1:
                    return new Training(numberOfRounds, numberOfExercises, timeOfWork, timeOfRestBetweenExercises, timeOfRestBetweenRounds, delay);
                default:
                    return null;
            }
        }

        @Override
        public Training[] newArray(int size) {
            return new Training[size];
        }
    };

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getTimeOfWork() {
        return timeOfWork;
    }

    public int getTimeOfRestBetweenRounds() {
        return timeOfRestBetweenRounds;
    }

    public int getTotalTime() {
        switch (trainingType) {
            case ONE_EXERCISE_TRAINING:
                totalTime  = (timeOfWork + timeOfRestBetweenRounds) * numberOfRounds;
                break;
            case SOME_EXERCISES_TRAINING:
                totalTime = (((timeOfWork * numberOfExercises) + (timeOfRestBetweenExercises * (numberOfExercises - 1))) * numberOfRounds) + (timeOfRestBetweenRounds * (numberOfRounds - 1));
                break;
            default:
                break;
        }
        return totalTime;
    }

    public int getDelay() {
        return delay;
    }

    public boolean isStartFromRest() {
        return startFromRest;
    }

    public int getNumberOfExercises() {
        return numberOfExercises;
    }

    public int getTimeOfRestBetweenExercises() {
        return timeOfRestBetweenExercises;
    }

    public int getTrainingType() {
        return trainingType;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String name) {
        this.trainingName = name;
    }
}
