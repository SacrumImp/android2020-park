package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.BdViewModel;

public class BdFeedFragment extends FeedFragment {

    @Override
    void getFromAdapter(int id) {
        navigator.openBDDetailedFragment(id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedViewModel = new ViewModelProvider(this).get(BdViewModel.class);
        //при первом запуске фрагмента запрашиваем ленту
        if (savedInstanceState == null) {
            feedViewModel.addFeedNextPage(pageCounter);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFilter.setVisibility(View.GONE);
    }

    @Override
    void loadNextPage() {
        showLoadingProgress();
        if (isSearch) {
            feedViewModel.addSearchNextPage(searchQuery, pageCounter);
        }
        else feedViewModel.addFeedNextPage(pageCounter);
    }

    @Override
    protected void showEmptyState() {
        Toast.makeText(getContext(), R.string.error_find, Toast.LENGTH_SHORT).show();
    }


    @Override
    void handleObserverError() {
        hideLoadingProgress();
        Toast.makeText(getContext(), "Ошибка в БД", Toast.LENGTH_SHORT).show();
    }

}
