package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.event.EventRepo;
import ru.techpark.agregator.localdata.BDRepo;

public class FeedViewModel extends AndroidViewModel {
    EventRepo eventRepo = new EventRepo(getApplication());

    private LiveData<List<Event>> mResponseData = eventRepo.getEvents();
    private LiveData<Event> mResponseEvent = eventRepo.getEvent();

    //Отображение бд в view model
    BDRepo bdRepo = new BDRepo(getApplication());
    private LiveData<List<Event>> mResponseBD = bdRepo.getBD();
    //

    public FeedViewModel(@NonNull Application application) {
        super(application);

    }
    LiveData<Event> getEvent(){
        return  mResponseEvent;
    }
    public void refresh(){
        eventRepo.refresh();
    }
    void getDetailedEvent(int id) {
        eventRepo.getCertainEvent(id);
    }
    LiveData<List<Event>> getEvents() {
        return mResponseData;
    }

    //Обращение к view model за бд
    LiveData<List<Event>> getBD(){ return mResponseBD; }    //получение данных
    void insertEventBD(Event event) { bdRepo.insertEventBD(event); }    //ввод данных
    //
}