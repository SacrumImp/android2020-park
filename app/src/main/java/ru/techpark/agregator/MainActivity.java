
package ru.techpark.agregator;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentNavigator{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            new ApiViewModel(getApplication()).addFeedNextPage(1);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new ApiFeedFragment());
            transaction.commit();
        }
        Intent intent = getIntent();
        if (intent.getAction() == NotificationWorker.ACTION_TO_OPEN){
            int id = intent.getIntExtra(NotificationWorker.OPEN_FRAGMENT_ID, 0);
            navigateToAnotherFragment(id);
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch(item.getItemId()){
                    case R.id.action_feed:
                        if (savedInstanceState == null) {
                            new ApiViewModel(getApplication()).addFeedNextPage(1);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, new ApiFeedFragment());
                            transaction.commit();
                        }
                        intent = getIntent();
                        if (intent.getAction() == NotificationWorker.ACTION_TO_OPEN){
                            int id = intent.getIntExtra(NotificationWorker.OPEN_FRAGMENT_ID, 0);
                            navigateToAnotherFragment(id);
                        }
                        break;
                    case R.id.action_liked:
                        Toast.makeText(MainActivity.this, "Liked", Toast.LENGTH_SHORT).show();

                        if (savedInstanceState == null) {
                            new BdViewModel(getApplication()).addFeedNextPage(1);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, new BdFeedFragment());
                            transaction.commit();
                        }
                        intent = getIntent();
                        if (intent.getAction() == NotificationWorker.ACTION_TO_OPEN){
                            int id = intent.getIntExtra(NotificationWorker.OPEN_FRAGMENT_ID, 0);
                            navigateToAnotherFragment(id);
                        }

                        break;
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }



    @Override
    public void navigateToAnotherFragment(int id) {
        new ApiSingleViewModel(getApplication()).getDetailedEvent(id);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,ApiDetailedFragment.newInstance(id))
                .addToBackStack(null)
                .commit();// all transactions before commit are added to backstack
    }



}
