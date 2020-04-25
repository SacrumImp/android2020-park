
package ru.techpark.agregator;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
