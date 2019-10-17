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

    public Button saveButton;
    Button cancelButton;
    EditText nameOfTimerTextInput;
    String nameOfTimer;
    Training training;
    MainActivity.DataProviderSavedTimers dataProvider;

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
                nameOfTimer = String.valueOf(nameOfTimerTextInput.getText());
                dataProvider.setTimerName(training, nameOfTimer);
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

    public void setDataProvider(MainActivity.DataProviderSavedTimers dataProvider) {
        this.dataProvider = dataProvider;
    }
}
