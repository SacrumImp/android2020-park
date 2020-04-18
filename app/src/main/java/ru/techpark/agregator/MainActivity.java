
package ru.techpark.agregator;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentNavigator{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            new FeedViewModel(getApplication()).addNextPage(1);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container,   new MainFragment());
            transaction.commit();
        }
    }



    @Override
    public void navigateToAnotherFragment(int num) {
        new FeedViewModel(getApplication()).getDetailedEvent(num);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,DetailedEventFragment.newInstance(num))
                .addToBackStack(null)
                .commit();// all transactions before commit are added to backstack
    }



}
