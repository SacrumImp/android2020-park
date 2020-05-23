package ru.techpark.agregator;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import ru.techpark.agregator.fragments.ApiDetailedFragment;
import ru.techpark.agregator.fragments.ApiFeedFragment;
import ru.techpark.agregator.fragments.BdDetailedFragment;
import ru.techpark.agregator.fragments.BdFeedFragment;
import ru.techpark.agregator.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements FragmentNavigator {

    public static final String TAG = "MainActivity";
    private FragmentManager manager;
    SharedPreferences prefs;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(updateBaseContextLocale(newBase));
        if(prefs.getBoolean("dark_theme", false)) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        prefs = PreferenceManager.getDefaultSharedPreferences(newBase);
        prefs.registerOnSharedPreferenceChangeListener((sharedPref, key) -> {
            Log.d(TAG, "enter");
            if(key.equals("dark_theme")) {
                if (sharedPref.getBoolean("dark_theme", false)) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            if(key.equals("switch_language")){
                recreate();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction()
                    .add(R.id.fragment_container, new ApiFeedFragment())
                    .commit();
        }

        Intent intent = getIntent();
        if (intent.getAction().equals(NotificationWorker.ACTION_TO_OPEN)) {
            int id = intent.getIntExtra(NotificationWorker.OPEN_FRAGMENT_ID, 0);
            openApiDetailedFragment(id);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
    public void openApiDetailedFragment(int id) {

        manager.beginTransaction()
                .replace(R.id.fragment_container, ApiDetailedFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openBDDetailedFragment(int id) {
        manager
                .beginTransaction()
                .replace(R.id.fragment_container, BdDetailedFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openDBFeed() {
        manager.beginTransaction()
                .replace(R.id.fragment_container, new BdFeedFragment())
                .addToBackStack(null)
                .commit();
    }

    private Context updateBaseContextLocale(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Locale loc;
        if(prefs.getBoolean("switch_language", false)) loc = new Locale("en");
        else loc = new Locale("ru", "rRu");
        Locale.setDefault(loc);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(loc);
        return context.createConfigurationContext(configuration);
    }

}
