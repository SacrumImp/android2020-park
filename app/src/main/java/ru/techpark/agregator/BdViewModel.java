package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.localdata.BDRepo;

public class BdViewModel extends ViewModel {

    private BDRepo bdRepo = new BDRepo(getApplication()); //инициализация репозитория
    LiveData<List<Event>> mResponseData = bdRepo.getBD(); //получение данных для заполнения view

    public BdViewModel(@NonNull Application application) {
        super(application);
    } //конструктор

    @Override
    void addFeedNextPage(int page) { }

    @Override
    void addSearchNextPage(String searchQuery, int page) { }

    @Override
    void insertEventBD(Event event) { bdRepo.insertEventBD(event); } //добавление в базу данных

    @Override
    LiveData<List<Event>> getEvents() { return mResponseData; } //возврат данных
}
