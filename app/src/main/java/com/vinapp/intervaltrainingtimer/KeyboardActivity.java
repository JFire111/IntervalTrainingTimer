package com.vinapp.intervaltrainingtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class KeyboardActivity extends Activity {

    private TextView displayTextView;
    private TextView titleTextView;
    private TextView hintMinutesTextView;
    private TextView hintSecondsTextView;
    private TextView hintNumbersTextView;

    private char enteredValue;
    private String DATA_TYPE;
    private String title;

    String defaultResult;
    private char[] currentValue;
    private boolean[] changedCharsPosition;
    private String result;

    private boolean valueIsChanged;

    Spannable spannableText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_keyboard);

        displayTextView = findViewById(R.id.displayTextView);
        titleTextView = findViewById(R.id.intervalNameTextView);
        hintMinutesTextView = findViewById(R.id.hintMinutesTextView);
        hintSecondsTextView = findViewById(R.id.hintSecondsTextView);
        //hintNumbersTextView = findViewById(R.id.hintNumbersTextView);

        DATA_TYPE = getIntent().getStringExtra("DATA_TYPE");
        title = getIntent().getStringExtra("title");
        valueIsChanged = getIntent().getBooleanExtra("valueIsChanged", false);

        switch (DATA_TYPE) {
            case "number":
                hintMinutesTextView.setVisibility(View.INVISIBLE);
                hintSecondsTextView.setVisibility(View.INVISIBLE);
                break;
            case "time":
                hintNumbersTextView.setVisibility(View.INVISIBLE);
                break;
            default:
                hintMinutesTextView.setVisibility(View.INVISIBLE);
                hintSecondsTextView.setVisibility(View.INVISIBLE);
                hintNumbersTextView.setVisibility(View.INVISIBLE);
                break;
        }

        setDefaultValues();
        displayTextView.setText(getCurrentValues());
        textViewColoring();

        titleTextView.setText(title);
    }

    private void setDefaultValues() {
        char[] defaultValue;
        boolean[] defaultChangedCharsPosition;
        defaultResult = getIntent().getStringExtra("defaultValue");
        switch (DATA_TYPE) {
            case "number":
                int numberStringLength = 2;
                if (defaultResult.length() < numberStringLength){
                }
                defaultValue = (defaultResult + "-").toCharArray();
                break;
            case "time":
                defaultValue = defaultResult.replaceAll(":", "").toCharArray();
                break;
            default:
                defaultValue = new char[0];
                break;
        }

        defaultChangedCharsPosition = new boolean[defaultValue.length];
        for (int i = 0; i < defaultChangedCharsPosition.length; i++) {
            if (valueIsChanged){
                defaultChangedCharsPosition[i] = true;
            } else {
                defaultChangedCharsPosition[i] = false;
            }
        }

        this.currentValue = defaultValue;
        this.changedCharsPosition = defaultChangedCharsPosition;
        this.result = defaultResult;
    }

    private String getCurrentValues() {
        result = String.valueOf(currentValue);
        switch (DATA_TYPE) {
            case "number":
                result = result.replaceAll("-", "");
                break;
            case "time":
                result = result.substring(0, 2) + ":" + result.substring(2);
                break;
            default:
                break;
        }
        return result;
    }

    private void changeValue(char newValue) {
        switch (DATA_TYPE) {
            case "number":
                if (!changedCharsPosition[0]) {
                    currentValue[0] = newValue;
                    changedCharsPosition[0] = true;
                    changedCharsPosition[1] = false;
                } else {
                    if (!changedCharsPosition[1]) {
                        currentValue[1] = newValue;
                        changedCharsPosition[1] = true;
                    }
                }
                break;
            case "time":
                for (int i = 0; i < currentValue.length; i++){
                    if (!changedCharsPosition[i]){
                        currentValue[i] = newValue;
                        changedCharsPosition[i] = true;
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    private void deleteValue() {
        switch (DATA_TYPE) {
            case "number":
                if (currentValue[1] == '-') {
                    if (changedCharsPosition[0]) {
                        currentValue[0] = '0';
                        changedCharsPosition[0] = false;
                    }
                } else {
                    currentValue[1] = '-';
                }
                break;
            case "time":
                for (int i = currentValue.length - 1; i >= 0; i--){
                    if (changedCharsPosition[i]) {
                        currentValue[i] = '0';
                        changedCharsPosition[i] = false;
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    private void textViewColoring() {
        spannableText = new SpannableString(displayTextView.getText());
        switch (DATA_TYPE) {
            case "number":
                if (changedCharsPosition[0]) {
                    spannableText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.mainAccent)), 0, spannableText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                break;
            case "time":
                int i;
                for (i = 0; i < changedCharsPosition.length; i++){
                    if (!changedCharsPosition[i]){
                        break;
                    }
                }
                if (i <= 2) {
                    spannableText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.mainAccent)), 0, i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.mainAccent)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.mainAccent)), 3, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                break;
            default:
                break;

        }
        displayTextView.setText(spannableText);
    }

    /*public void onClickKeyboardButton(View view) {
        switch (view.getId()){
            case R.id.keyboardButton0:
                enteredValue = '0';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton1:
                enteredValue = '1';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton2:
                enteredValue = '2';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton3:
                enteredValue = '3';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton4:
                enteredValue = '4';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton5:
                enteredValue = '5';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton6:
                enteredValue = '6';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton7:
                enteredValue = '7';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton8:
                enteredValue = '8';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButton9:
                enteredValue = '9';
                changeValue(enteredValue);
                break;
            case R.id.keyboardButtonOk:
                valueIsChanged = true;
                Intent data = new Intent();
                data.putExtra("buttonText", getCurrentValues());
                data.putExtra("ValueIsChanged", valueIsChanged);
                setResult(RESULT_OK, data);
                finish();
                break;
            case R.id.keyboardButtonCancel:
                Intent defaultData = new Intent();
                defaultData.putExtra("buttonText", defaultResult);
                defaultData.putExtra("ValueIsChanged", valueIsChanged);
                setResult(RESULT_OK, defaultData);
                finish();
                break;
            case R.id.keyboardButtonDelete:
                deleteValue();
                break;
            default:
                break;
        }
        displayTextView.setText(getCurrentValues());
        textViewColoring();
    }*/

    @Override
    public void onBackPressed() {
        Intent defaultData = new Intent();
        defaultData.putExtra("buttonText", defaultResult);
        defaultData.putExtra("ValueIsChanged", valueIsChanged);
        setResult(RESULT_OK, defaultData);
        finish();
    }
}
