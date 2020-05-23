package ru.techpark.agregator.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import ru.techpark.agregator.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);


        ListPreference listPreference = findPreference("city_list");
        listPreference.setSummaryProvider((Preference.SummaryProvider<ListPreference>) preference ->  preference.getEntry());
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof TimePreference) {
            DialogFragment dialogFragment = TimePreferenceDialog.newInstance(preference.getKey());
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), null);
        }
        else super.onDisplayPreferenceDialog(preference);
    }

}
