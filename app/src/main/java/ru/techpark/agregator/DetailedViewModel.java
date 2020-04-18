package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ru.techpark.agregator.event.DetailedEventRepo;
import ru.techpark.agregator.event.Event;

public class DetailedViewModel extends AndroidViewModel {
    DetailedEventRepo detailedEventRepo = new DetailedEventRepo(getApplication());

    private LiveData<Event> mResponseEvent = detailedEventRepo.getEvent();


    public DetailedViewModel(@NonNull Application application) {
        super(application);
    }
    LiveData<Event> getEvent(){
        return  mResponseEvent;
    }

    void getDetailedEvent(int id) {
        detailedEventRepo.getCertainEvent(id);
    }
}
