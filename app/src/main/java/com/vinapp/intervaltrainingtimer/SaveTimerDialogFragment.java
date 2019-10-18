package com.vinapp.intervaltrainingtimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SaveTimerDialogFragment extends AppCompatDialogFragment {

    private Button saveButton;
    private Button cancelButton;
    private EditText nameOfTimerTextInput;
    private String nameOfTimer;
    private Training training;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_save_timer, null);
        saveButton = (Button) view.findViewById(R.id.dialogSaveTimerOkButton);
        cancelButton = (Button) view.findViewById(R.id.dialogSaveTimerCancelButton);
        nameOfTimerTextInput = (EditText) view.findViewById(R.id.dialogSaveTimerTextInput);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                nameOfTimer = String.valueOf(nameOfTimerTextInput.getText());
                training.setTrainingName(nameOfTimer);
                activity.saveTimer(training);
                activity.updateFragment(activity.SAVED_TIMERS_FRAGMENT_ID);
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setTraining(Training training){
        this.training = training;
    }
}
