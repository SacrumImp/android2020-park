package ru.techpark.agregator.localdata;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.techpark.agregator.event.Event;

public class BDRepo {

    private final static MutableLiveData<List<Event>> sEvents = new MutableLiveData<>();

    static {
        sEvents.setValue(Collections.emptyList());
    }

    private final MutableLiveData<Event> sEvent = new MutableLiveData<>();
    private List<Event> eventList = new ArrayList<>();
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppDatabase db;

    public BDRepo(Context context) {
        db = DBHelper.getInstance(context).getDb();
    }

    public LiveData<List<Event>> getBD() {
        return sEvents;
    }

    public LiveData<Event> getEvent() {
        return sEvent;
    }

    public void refresh() {
        executor.execute(() -> {
            eventList = transform(db.getDao().getAllEvents());
            sEvents.postValue(eventList);
        });
    }

    public void insertEventBD(Event event) {
        executor.execute(() -> {
            EventTable eventBd = new EventTable(event);
            db.getDao().insert(eventBd);
        });
    }

    public void deleteEventBD(Event event) {
        executor.execute(() -> {
            db.getDao().delete(event.getId());
        });
        refresh();
    }

    public void getCertainEvent(int id) {
        executor.execute(() -> {
            List<Event> certainEvent = transform(db.getDao().getEvent(id));
            if (certainEvent.size() > 0)
                sEvent.postValue(certainEvent.get(0));
        });
    }

    public void addDataSearch(String searchQuery) {
        executor.execute(() -> {
            eventList = transform(db.getDao().getSearchEvent('%' + searchQuery + '%'));
            sEvents.postValue(eventList);
        });
    }

    private List<Event> transform(List<EventTable> events) {
        List<Event> retList = new ArrayList<>();
        Event event;
        for (EventTable eventTbl : events) {
            event = new Event(eventTbl.id, eventTbl.title, eventTbl.getImages(), eventTbl.description, eventTbl.body_text, eventTbl.price, eventTbl.getDates(), eventTbl.location, eventTbl.place, eventTbl.site_url);
            retList.add(event);
        }
        return retList;
    }

    public boolean isEmpty() {
        return eventList.isEmpty();
    }

}
