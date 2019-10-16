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

import java.util.ArrayList;

public class SavedTimersAdapter extends ArrayAdapter <Training> {

    private final Context context;
    private ArrayList<Training> trainings;
    private static final String TAG = "SAVED ADAPTER LOG";
    private boolean[] selections;
    private boolean isSelected;

    public SavedTimersAdapter(@NonNull Context context, ArrayList<Training> trainings) {
        super(context, R.layout.saved_timers_list_item, trainings);
        this.context = context;
        this.trainings = trainings;
        this.selections = new boolean[trainings.size()];
    }

    public void selectItem(int position) {
        selections[position] = !selections[position];
        for (int i = 0; i < trainings.size(); i++) {
            if (i != position) {
                selections[i] = false;
            }
        }
        notifyDataSetChanged();
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

        totalTimeTextView.setText(String.valueOf(trainings.get(position).getTotalTime()));
        savedTimerNameTextView.setText("asdasdas asdasd");
        numberOfSavedTimerTextView.setText((position + 1) + "/" + trainings.size());
        switch (trainings.get(position).getTrainingType()) {
            case 0:
                typeOfTimerTextView.setText("One exercise");
                break;
            case 1:
                typeOfTimerTextView.setText("Some exercises");
                break;
            default:
                break;
        }
        if (isSelected) {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkThemeColorPrimaryDark));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkThemeColorPrimary));
        }
        return view;
    }
}
