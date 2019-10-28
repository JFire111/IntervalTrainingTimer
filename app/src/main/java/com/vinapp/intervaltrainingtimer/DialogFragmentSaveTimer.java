package com.vinapp.intervaltrainingtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragmentSaveTimer extends AppCompatDialogFragment {

    private EditText nameOfTimerTextInput;
    private String nameOfTimer;
    private Training training;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder saveTimerDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_save_timer, null);
        nameOfTimerTextInput = (EditText) view.findViewById(R.id.dialogSaveTimerTextInput);
        saveTimerDialog.setView(view)
                .setTitle(R.string.saveTimerDialogTitle)
                .setPositiveButton(R.string.saveTimerDialogOkButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity activity = (MainActivity) getActivity();
                        nameOfTimer = String.valueOf(nameOfTimerTextInput.getText());
                        training.setTrainingName(nameOfTimer);
                        activity.saveTimer(training);
                        activity.updateFragment(activity.SAVED_TIMERS_FRAGMENT_ID);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.saveTimerDialogCancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return saveTimerDialog.create();
    }

    public void setTraining(Training training){
        this.training = training;
    }
}
