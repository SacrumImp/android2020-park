package ru.techpark.agregator.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.ApiViewModel;

import static android.content.Context.MODE_PRIVATE;

public class ApiFeedFragment extends FeedFragment {

    private static final String TAG = "ApiFeed";
    private Chip chipAll;
    private Chip chipConcert;
    private Chip chipExhibitions;
    private Chip chipEducation;
    private Chip chipKids;
    private Chip chipTheatre;
    private Chip chipFestivals;
    private Chip chipPastime;
    private SharedPreferences preferences;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFilter.setOnClickListener(l -> setFilterState());
        chipAll = chipsLayout.findViewById(R.id.chip_all);
        chipConcert = chipsLayout.findViewById(R.id.chip_concerts);
        chipExhibitions = chipsLayout.findViewById(R.id.chip_exhibitions);
        chipEducation = chipsLayout.findViewById(R.id.chip_education);
        chipKids = chipsLayout.findViewById(R.id.chip_kids);
        chipTheatre = chipsLayout.findViewById(R.id.chip_theater);
        chipFestivals = chipsLayout.findViewById(R.id.chip_festivals);
        chipPastime = chipsLayout.findViewById(R.id.chip_pastime);

        preferences = getContext().getSharedPreferences(getString(R.string.pref_feed_file),
                MODE_PRIVATE);

        if (savedInstanceState == null)
            checkFilter();
        chipsLayout.setOnCheckedChangeListener((group, checkedId) -> {
            Log.d(TAG, checkedId + " checked ");
            String filter = "";
            if (checkedId == -1) {
                group.check(chipAll.getId());
                filter = "";
            } else {
                Log.d(TAG, "chip id = " + checkedId);
                if (checkedId == chipConcert.getId()) {
                    Log.d(TAG, "concert");
                    filter = "concert";
                } else if (checkedId == chipExhibitions.getId()) {
                    Log.d(TAG, "exhibition");
                    filter = "exhibition";
                } else if (checkedId == chipEducation.getId()) {
                    Log.d(TAG, "education");
                    filter = "education";
                } else if (checkedId == chipKids.getId()) {
                    Log.d(TAG, "kids");
                    filter = "kids";
                } else if (checkedId == chipTheatre.getId()) {
                    Log.d(TAG, "theater");
                    filter = "theater";
                } else if (checkedId == chipFestivals.getId()) {
                    Log.d(TAG, "festival");
                    filter = "festival";
                } else if (checkedId == chipPastime.getId()) {
                    Log.d(TAG, "recreation");
                    filter = "recreation";
                } else if (checkedId == chipAll.getId()) {
                    Log.d(TAG, "all");
                    filter = "";
                }
            }
            preferences.edit().putString(getString(R.string.preference_filter), filter).apply();
            isSearch = false;
            isAllEvents = false;
            pageCounter = 1;
            loadNextPage();
            setHomeState();
        });
    }

    private void checkFilter() {
        int id;
        String filter = preferences.getString(getString(R.string.preference_filter), "");
        switch (filter) {
            case "concert":
                Log.d(TAG, "concertx");
                id = chipConcert.getId();
                break;
            case "exhibition":
                Log.d(TAG, "exhibitionx");
                id = chipExhibitions.getId();
                break;
            case "education":
                Log.d(TAG, "educationx");
                id = chipEducation.getId();
                break;
            case "kids":
                Log.d(TAG, "kidsx");
                id = chipKids.getId();
                break;
            case "theater":
                Log.d(TAG, "theaterx");
                id = chipTheatre.getId();
                break;
            case "festival":
                Log.d(TAG, "festivalx");
                id = chipFestivals.getId();
                break;
            case "recreation":
                Log.d(TAG, "recreationx");
                id = chipPastime.getId();
                break;
            default:
                Log.d(TAG, "allx");
                id = chipAll.getId();
                break;
        }
        chipsLayout.check(id);
    }

    private void setFilterState() {
        exitSearch.setVisibility(View.GONE);
        setFilter.setVisibility(View.GONE);
        startSearch.setVisibility(View.GONE);
        searchField.setVisibility(View.GONE);
        exitSearch.setVisibility(View.VISIBLE);
        chipsLayout.setVisibility(View.VISIBLE);
        searchField.setText("");
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
        } else {
            feedViewModel.addFeedNextPage(pageCounter);
        }
    }

    @Override
    protected void showEmptyState() {
        Toast.makeText(getContext(), R.string.error_find, Toast.LENGTH_SHORT).show();
    }

}
