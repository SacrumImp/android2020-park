package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ru.techpark.agregator.event.Event;

abstract public class SingleViewModel extends AndroidViewModel {

    public SingleViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract LiveData<Event> getEvent();
    public abstract void getDetailedEvent(int id);
}
