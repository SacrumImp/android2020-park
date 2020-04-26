package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ru.techpark.agregator.event.DetailedEventRepo;
import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.localdata.BDRepo;

public class BdSingleViewModel extends SingleViewModel {

    private BDRepo detailedBdRepo = new BDRepo(getApplication());
    private LiveData<Event> mResponseEvent = detailedBdRepo.getEvent();

    public BdSingleViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<Event> getEvent() { return mResponseEvent; }

    @Override
    public void getDetailedEvent(int id) { detailedBdRepo.getCertainEvent(id); }
}
