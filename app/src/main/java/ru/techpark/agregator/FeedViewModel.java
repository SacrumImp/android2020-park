package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;

abstract class FeedViewModel extends AndroidViewModel {

    FeedViewModel(@NonNull Application application) {
        super(application);
    }

    abstract void addFeedNextPage(int page);
    abstract void addSearchNextPage(String searchQuery, int page);
    abstract void insertEventBD(Event event);
    abstract LiveData<List<Event>> getEvents();

}
