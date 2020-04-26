package ru.techpark.agregator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.techpark.agregator.event.Event;

public class ApiFeedFragment extends FeedFragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView feed = view.findViewById(R.id.list_of_events);
        loadingProgress = view.findViewById(R.id.loading_progress);
        adapter = new FeedFragment.FeedAdapter();
        feed.setAdapter(adapter);
        feed.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Observer<List<Event>> observer = Events -> {
            if (Events != null) {
                hideLoadingProgress();
                adapter.setEvents(Events);
            } else {
                hideLoadingProgress();
                Toast.makeText(getContext(), "Нет соеинения с интернетом", Toast.LENGTH_SHORT).show();
            }
        };
        feedViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        feedViewModel.getEvents()
                .observe(getViewLifecycleOwner(), observer);

        searchField = view.findViewById(R.id.search_query);
        Log.d(TAG, "is search " + isSearch);
        exitSearch = view.findViewById(R.id.exit_search);
        if (isSearch)
            exitSearch.setVisibility(View.VISIBLE);
        exitSearch.setOnClickListener((l) -> {
            isSearch = false;
            isAllEvents = false;
            pageCounter = 1;
            loadNextPage();
            exitSearch.setVisibility(View.GONE);
            searchField.setText("");
        });
        startSearch = view.findViewById(R.id.start_search);
        startSearch.setOnClickListener((l) -> {
            searchQuery = searchField.getText().toString();
            if (!searchQuery.equals("")) {
                pageCounter = 1;
                isAllEvents = false;
                isSearch = true;
                exitSearch.setVisibility(View.VISIBLE);
                loadNextPage();
            }
        });
    }

    @Override
    void getFromAdapter(int id) {
        navigator.navigateToAnotherFragment(id);
    }

    @Override
    protected void loadNextPage() {
        showLoadingProgress();
        if (isSearch)
            feedViewModel.addSearchNextPage(searchQuery, pageCounter);
        else feedViewModel.addFeedNextPage(pageCounter);
    }
}
