package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.ApiViewModel;

public class ApiFeedFragment extends FeedFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        //при первом запуске фрагмента запрашиваем ленту
        if (savedInstanceState == null) {
            feedViewModel.addFeedNextPage(pageCounter);
        }
    }

    @Override
    void handleObserverError() {
        hideLoadingProgress();
        Toast.makeText(getContext(), "Нет соеинения с интернетом", Toast.LENGTH_SHORT).show();
    }

    @Override
    void getFromAdapter(int id) {
        navigator.openApiDetailedFragment(id);
    }

    @Override
    void loadNextPage() {
        showLoadingProgress();
        if (isSearch) {
            feedViewModel.addSearchNextPage(searchQuery, pageCounter);
            if (feedViewModel.isEmpty()) Toast.makeText(getContext(), R.string.error_find, Toast.LENGTH_SHORT).show();
        }

        else feedViewModel.addFeedNextPage(pageCounter);
    }

}
