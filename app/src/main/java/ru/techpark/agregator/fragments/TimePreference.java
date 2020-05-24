package ru.techpark.agregator.fragments;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

import ru.techpark.agregator.R;


public class TimePreference extends DialogPreference {
    public TimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.time_picker);
    }

}
