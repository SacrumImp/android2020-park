package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.event.EventRepo;

public class FeedViewModel extends AndroidViewModel {
    private EventRepo eventRepo = new EventRepo(getApplication());
    private LiveData<List<Event>> mResponseData = eventRepo.getEvents();

    public FeedViewModel(@NonNull Application application) {
        super(application);
        eventRepo.addData(1);
    }

    public void addNextPage(int page) {
        eventRepo.addData(page);
    }

    LiveData<List<Event>> getEvents() {
        return mResponseData;
    }


}
