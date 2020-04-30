package ru.techpark.agregator;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.techpark.agregator.fragments.ApiDetailedFragment;
import ru.techpark.agregator.fragments.ApiFeedFragment;
import ru.techpark.agregator.fragments.BdDetailedFragment;
import ru.techpark.agregator.fragments.BdFeedFragment;

public class MainActivity extends AppCompatActivity implements FragmentNavigator {

    public static final String TAG = "MainActivity";
    private FragmentManager manager;

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
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
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


}
