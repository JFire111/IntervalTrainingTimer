package com.vinapp.intervaltrainingtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragmentSavedTimerLimit extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder savedTimerLimitDialog = new AlertDialog.Builder(getActivity());
        savedTimerLimitDialog.setTitle(R.string.savedTimersLimitAlertTitle)
                .setMessage(R.string.savedTimersLimitAlertMessage)
                .setPositiveButton(R.string.savedTimersLimitAlertOkButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return savedTimerLimitDialog.create();
    }
}
