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
    private NumberPicker mNumberPicker;
    private NumberPicker chNumberPicker;

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
            editor.putInt("notif_min", mNumberPicker.getValue());
            editor.putInt("notif_ch", chNumberPicker.getValue());
            editor.apply();
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mNumberPicker = view.findViewById(R.id.mNumberPicker);
        mNumberPicker.setMaxValue(60);
        mNumberPicker.setMinValue(0);
        chNumberPicker = view.findViewById(R.id.chNumberPicker);
        chNumberPicker.setMaxValue(30);
        chNumberPicker.setMinValue(0);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mNumberPicker.setValue(sharedPreferences.getInt("notif_min", 0));
        chNumberPicker.setValue(sharedPreferences.getInt("notif_ch", 0));
    }
}
