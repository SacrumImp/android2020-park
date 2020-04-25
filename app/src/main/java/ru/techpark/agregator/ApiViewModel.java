package ru.techpark.agregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.event.EventRepo;

public class ApiViewModel extends ViewModel {

    private EventRepo eventRepo = new EventRepo(getApplication()); //инициализация репозитория
    LiveData<List<Event>> mResponseData = eventRepo.getEvents(); //получение данных для заполнения view

    public ApiViewModel(@NonNull Application application) {
        super(application);
    } //конструктор

    @Override
    void addFeedNextPage(int page) { eventRepo.addDataFeed(page); } //получение новой страницы

    @Override
    void addSearchNextPage(String searchQuery, int page) { eventRepo.addDataSearch(searchQuery, page); } //данные поиска

    @Override
    void insertEventBD(Event event) { }

    @Override
    LiveData<List<Event>> getEvents() { return mResponseData; } //возврат данных
}
