package ru.techpark.agregator;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.Objects;

import ru.techpark.agregator.fragments.ApiDetailedFragment;
import ru.techpark.agregator.fragments.ApiFeedFragment;
import ru.techpark.agregator.fragments.BdDetailedFragment;
import ru.techpark.agregator.fragments.BdFeedFragment;
import ru.techpark.agregator.fragments.SettingsFragment;


public class MainActivity extends AppCompatActivity implements FragmentNavigator, SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentManager manager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(updateBaseContextLocale(newBase));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("switch_language") || key.equals("dark_theme")) {
            recreate();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.getDefaultSharedPreferences(getApplication())
                .registerOnSharedPreferenceChangeListener(this);

        manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction()
                    .add(R.id.fragment_container, new ApiFeedFragment())
                    .commit();
        }

        Intent intent = getIntent();
        if (Objects.equals(intent.getAction(), NotificationWorker.ACTION_TO_OPEN)) {
            int id = intent.getIntExtra(NotificationWorker.OPEN_FRAGMENT_ID, 0);
            openApiDetailedFragment(id);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            manager.popBackStack();
            switch (item.getItemId()) {
                case R.id.action_feed:
                    manager.beginTransaction()
                            .replace(R.id.fragment_container, new ApiFeedFragment())
                            .commit();
                    break;
                case R.id.action_liked:
                    manager.beginTransaction()
                            .replace(R.id.fragment_container, new BdFeedFragment())
                            .commit();
                    break;
                case R.id.action_settings:
                    manager.beginTransaction()
                            .replace(R.id.fragment_container, new SettingsFragment())
                            .commit();
                    break;
            }
            return true;
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(getApplication())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void openApiDetailedFragment(int id) {
        manager.beginTransaction()
                .replace(R.id.fragment_container, ApiDetailedFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openBDDetailedFragment(int id) {
        manager.beginTransaction()
                .replace(R.id.fragment_container, BdDetailedFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openDBFeed() {
        manager.popBackStack();

    }

    private Context updateBaseContextLocale(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Locale loc;
        if (prefs.getBoolean("switch_language", false))
            loc = new Locale("en");
        else
            loc = new Locale("ru", "rRu");
        Locale.setDefault(loc);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(loc);

        if (prefs.getBoolean("dark_theme", false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        return context.createConfigurationContext(configuration);
    }

}
