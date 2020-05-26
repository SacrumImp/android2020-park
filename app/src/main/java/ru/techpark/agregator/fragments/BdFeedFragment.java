package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.BdViewModel;

public class BdFeedFragment extends FeedFragment {

    private static final String TAG = "BDFeedFr";

    @Override
    void getFromAdapter(int id) {
        navigator.openBDDetailedFragment(id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedViewModel = new ViewModelProvider(this).get(BdViewModel.class);
            feedViewModel.addFeedNextPage(0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFilter.setVisibility(View.GONE);
    }

    @Override
    protected void showEmptyState() {
        feed.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        if (isSearch) {
            errorText.setText(R.string.error_find);
            errorImage.setImageResource(R.drawable.ic_empty_search);
        } else {
            errorText.setText(R.string.empty_feed);
            errorImage.setImageResource(R.drawable.ic_database_empty);
        }
    }


    @Override
    void handleObserverError() {
        hideLoadingProgress();
        feed.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorText.setText(R.string.database_error);
        errorImage.setImageResource(R.drawable.ic_database_error);
    }
}
