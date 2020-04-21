package ru.techpark.agregator.localdata;


import android.content.Context;

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
                List<EventTable> eventList = db.getDao().getAllEvents();
                //sEvents.postValue();
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
}
