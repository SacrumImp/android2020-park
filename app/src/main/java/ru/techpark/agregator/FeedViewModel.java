package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.event.EventRepo;

public class FeedViewModel extends AndroidViewModel {

    private LiveData<List<Event>> mResponseData = new EventRepo(getApplication()).getEvents();

    public FeedViewModel(@NonNull Application application) {
        super(application);

    }

    LiveData<List<Event>> getEvents() {
        return mResponseData;
    }


}
