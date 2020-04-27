package ru.techpark.agregator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ru.techpark.agregator.event.DetailedEventRepo;
import ru.techpark.agregator.event.Event;

public class ApiSingleViewModel extends SingleViewModel {

    private DetailedEventRepo detailedEventRepo = new DetailedEventRepo(getApplication());
    private LiveData<Event> mResponseEvent = detailedEventRepo.getEvent();

    public ApiSingleViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<Event> getEvent() {
        return  mResponseEvent;
    }

    @Override
    public void getDetailedEvent(int id) { detailedEventRepo.getCertainEvent(id); }
}
