package ru.techpark.agregator.localdata;


import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.techpark.agregator.event.Event;

public class BDRepo {

    private final static MutableLiveData<List<Event>> sEvents = new MutableLiveData<>();

    static{
        sEvents.setValue(Collections.<Event>emptyList());
    }

    private final Context mContext;
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppDatabase db;

    public BDRepo(Context context){
        mContext = context;
        db = DBHelper.getInstance(context).getDb();
        refresh();
    }

    public LiveData<List<Event>> getBD(){
        return sEvents;
    }

    public void refresh(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Event> eventList = transform(db.getDao().getAllEvents());
                sEvents.postValue(eventList);
            }
        });
    }

    public void insertEventBD(Event event){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                EventTable eventDb = new EventTable(event);
                db.getDao().insert(eventDb);
            }
        });
    }

    public List<Event> transform(List<EventTable> events){
        List<Event> retList = new ArrayList<>();
        Event event;
        for(EventTable eventTbl: events){
            event = new Event(eventTbl.id, eventTbl.title, eventTbl.getImages(), eventTbl.description, eventTbl.body_text, eventTbl.price, eventTbl.getDates(), eventTbl.location, eventTbl.place);
            retList.add(event);
        }
        return retList;
    }

}
