package ru.techpark.agregator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;

public abstract class FeedViewModel extends AndroidViewModel {

    FeedViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract void addFeedNextPage(int page);
    public abstract void addSearchNextPage(String searchQuery, int page);
    public abstract void insertEventBD(Event event);
    public abstract LiveData<List<Event>> getEvents();

}
