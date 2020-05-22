package ru.techpark.agregator.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import ru.techpark.agregator.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPref, key) -> {
            if(sharedPref.getBoolean(getString(R.string.preference_theme_key), false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Log.d("theme", "night");
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Log.d("theme", "light");
            }
        });

        ListPreference listPreference = findPreference(getString(R.string.preference_city_key));
        listPreference.setSummaryProvider((Preference.SummaryProvider<ListPreference>) preference ->  preference.getEntry());
    }

}
