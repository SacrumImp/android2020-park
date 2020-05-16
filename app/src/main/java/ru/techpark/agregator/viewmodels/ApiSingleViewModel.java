package ru.techpark.agregator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ru.techpark.agregator.event.DetailedEventRepo;
import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.localdata.BDRepo;

public class ApiSingleViewModel extends SingleViewModel {


    private BDRepo bdRepo = new BDRepo(getApplication());
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

    @Override
    public void insertEventBD(Event event) {  bdRepo.insertEventBD(event); }
}
