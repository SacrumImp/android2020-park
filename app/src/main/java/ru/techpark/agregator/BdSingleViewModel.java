package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ru.techpark.agregator.event.DetailedEventRepo;
import ru.techpark.agregator.event.Event;

public class BdSingleViewModel extends SingleViewModel {

    //TODO Изменить значение переменных под бд
    private DetailedEventRepo detailedEventRepo = new DetailedEventRepo(getApplication());
    private LiveData<Event> mResponseEvent = detailedEventRepo.getEvent();

    public BdSingleViewModel(@NonNull Application application) {
        super(application);
    }

    //TODO Реализовать класс
    @Override
    public LiveData<Event> getEvent() {
        return null;
    }

    //TODO Реализовать класс
    @Override
    public void getDetailedEvent(int id) {

    }
}
