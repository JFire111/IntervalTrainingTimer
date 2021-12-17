package com.vinapp.intervaltrainingtimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SavedTimersAdapter extends ArrayAdapter <Training> {

    private final Context context;
    private ArrayList<Training> trainings;
    private boolean[] selections;
    private boolean isSelected;

    private final DecimalFormat showingFormat = new DecimalFormat("00");

    private static final String TAG = "SAVED ADAPTER LOG";

    public SavedTimersAdapter(@NonNull Context context, ArrayList<Training> trainings) {
        super(context, R.layout.saved_timers_list_item, trainings);
        this.context = context;
        this.trainings = trainings;
        this.selections = new boolean[trainings.size()];
    }

    public void selectItem(int position) {
        if (position < trainings.size()) {
            selections[position] = !selections[position];
            for (int i = 0; i < trainings.size(); i++) {
                if (i != position) {
                    selections[i] = false;
                }
            }
            notifyDataSetChanged();
        }
    }

    public Training getSelectedItem() {
        for (int i = 0; i < selections.length; i++) {
            if (selections[i]) {
                return trainings.get(i);
            }
        }
        return null;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.saved_timers_list_item, parent, false);

        isSelected = selections[position];
        TextView totalTimeTextView = view.findViewById(R.id.totalTimeTextView);
        TextView savedTimerNameTextView = view.findViewById(R.id.savedTimerNameTextView);
        TextView typeOfTimerTextView = view.findViewById(R.id.typeOfTimerTextView);
        TextView numberOfSavedTimerTextView = view.findViewById(R.id.numberOfSavedTimerTextView);


        int totalTime = trainings.get(position).getTotalTime();
        int minutes = totalTime / 60;
        int seconds = totalTime % 60;
        String showingTime = showingFormat.format(minutes) + ":" + showingFormat.format(seconds);
        totalTimeTextView.setText(showingTime);
        savedTimerNameTextView.setText(trainings.get(position).getTrainingName());
        numberOfSavedTimerTextView.setText((position + 1) + "/" + trainings.size());
        switch (trainings.get(position).getTrainingType()) {
            case 0:
                typeOfTimerTextView.setText(R.string.typeOneExercise);
                break;
            case 1:
                typeOfTimerTextView.setText(R.string.typeSomeExercise);
                break;
            default:
                break;
        }
        if (isSelected) {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkGray));
        }
        return view;
    }

}
