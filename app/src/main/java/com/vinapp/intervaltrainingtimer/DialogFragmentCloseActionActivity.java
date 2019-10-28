package com.vinapp.intervaltrainingtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragmentCloseActionActivity extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder closeActionActivityDialog = new AlertDialog.Builder(getActivity());

        closeActionActivityDialog.setTitle(R.string.closeActionActivityDialogTitle)
                .setMessage(R.string.closeActionActivityDialogMessage)
                .setPositiveButton(R.string.closeActionActivityDialogYesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActionActivity activity = (ActionActivity) getActivity();
                        activity.finish();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.closeActionActivityDialogNoButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return closeActionActivityDialog.create();
    }
}
