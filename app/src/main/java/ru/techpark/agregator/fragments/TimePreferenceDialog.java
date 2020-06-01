package ru.techpark.agregator.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.preference.PreferenceDialogFragmentCompat;
import androidx.preference.PreferenceManager;

import ru.techpark.agregator.R;

public class TimePreferenceDialog extends PreferenceDialogFragmentCompat {

    private SharedPreferences sharedPreferences;
    private NumberPicker minuteNumberPicker;
    private NumberPicker hourNumberPicker;

    static TimePreferenceDialog newInstance(String key) {
        final TimePreferenceDialog fragment = new TimePreferenceDialog();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if(positiveResult) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("notification_minute_time", minuteNumberPicker.getValue());
            editor.putInt("notification_hour_time", hourNumberPicker.getValue());
            editor.apply();
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        minuteNumberPicker = view.findViewById(R.id.mNumberPicker);
        minuteNumberPicker.setMaxValue(60);
        minuteNumberPicker.setMinValue(0);
        hourNumberPicker = view.findViewById(R.id.chNumberPicker);
        hourNumberPicker.setMaxValue(30);
        hourNumberPicker.setMinValue(0);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        minuteNumberPicker.setValue(sharedPreferences.getInt("notification_minute_time", 0));
        hourNumberPicker.setValue(sharedPreferences.getInt("notification_hour_time", 1));
    }
}
