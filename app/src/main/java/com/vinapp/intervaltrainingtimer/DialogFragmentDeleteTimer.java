package com.vinapp.intervaltrainingtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragmentDeleteTimer extends AppCompatDialogFragment {

    String TAG = "DeleteDialog";
    MainActivity.DataProviderSavedTimers dataProvider;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder deleteTimerDialog = new AlertDialog.Builder(getActivity());
        deleteTimerDialog.setTitle(R.string.deleteTimerDialogTitle)
                .setMessage(R.string.deleteTimerDialogMessage)
                .setPositiveButton(R.string.deleteTimerDialogOkButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.deleteTimer();
                        activity.updateFragment(activity.SAVED_TIMERS_FRAGMENT_ID);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.deleteTimerDialogCancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return deleteTimerDialog.create();
    }

    public void setDataProvider(MainActivity.DataProviderSavedTimers dataProvider) {
        this.dataProvider = dataProvider;
    }
}