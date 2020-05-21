package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.ApiViewModel;

public class ApiFeedFragment extends FeedFragment {

    private static final String TAG = "ApiFeed";

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

        checkFilter();
        chipsLayout.setOnCheckedChangeListener((group, checkedId) -> {
            Log.d(TAG, checkedId + " checked " + group.getCheckedChipId());
            switch (checkedId % 8) {
                case 2:
                    Log.d(TAG, "concert");
                    break;
                case 3:
                    Log.d(TAG, "exhibition");
                    break;
                case 4:
                    Log.d(TAG, "education");
                    break;
                case 5:
                    Log.d(TAG, "kids");
                    break;
                case 6:
                    Log.d(TAG, "theater");
                    break;
                case 7:
                    Log.d(TAG, "festival");
                    break;
                case 0:
                    Log.d(TAG, "recreation");
                    break;
                //вместо 1 и -1
                default:
                    Log.d(TAG, "");
                    group.check(group.findViewById(R.id.chip_all).getId());
                    break;
            }
            setHomeState();
        });
    }

    private void checkFilter() {
        int id;
        //todo тут доставать shared pref
        switch ("concert") {
            case "concert":
                Log.d(TAG, "concertx");
                id = 1;
                break;
            case "exhibition":
                Log.d(TAG, "exhibitionx");
                id = 2;
                break;
            case "education":
                Log.d(TAG, "educationx");
                id = 3;
                break;
            case "kids":
                Log.d(TAG, "kidsx");
                id = 4;
                break;
            case "theater":
                Log.d(TAG, "theaterx");
                id = 5;
                break;
            case "festival":
                Log.d(TAG, "festivalx");
                id = 6;
                break;
            case "recreation":
                Log.d(TAG, "recreationx");
                id = 7;
                break;
            //вместо 1 и -1
            default:
                Log.d(TAG, "allx");
                id = 0;
                break;
        }
        chipsLayout.check(chipsLayout.findViewById(R.id.chip_all).getId() + id);
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
            if (feedViewModel.isEmpty()) Toast.makeText(getContext(), R.string.error_find, Toast.LENGTH_SHORT).show();
        }

        else feedViewModel.addFeedNextPage(pageCounter);
    }

}
