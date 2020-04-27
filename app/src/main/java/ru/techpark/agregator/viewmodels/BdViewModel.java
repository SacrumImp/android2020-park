package ru.techpark.agregator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.localdata.BDRepo;

public class BdViewModel extends FeedViewModel {

    private BDRepo bdRepo = new BDRepo(getApplication()); //инициализация репозитория
    private LiveData<List<Event>> mResponseData = bdRepo.getBD(); //получение данных для заполнения view

    public BdViewModel(@NonNull Application application) {
        super(application);
    } //конструктор

    @Override
    public void addFeedNextPage(int page) { bdRepo.refresh(); }

    @Override
    public void addSearchNextPage(String searchQuery, int page) { bdRepo.addDataSearch(searchQuery); }

    @Override
    public void insertEventBD(Event event) { bdRepo.insertEventBD(event); } //добавление в базу данных

    @Override
    public LiveData<List<Event>> getEvents() { return mResponseData; } //возврат данных
}
