package ru.techpark.agregator.localdata;


import android.content.Context;

import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.techpark.agregator.event.Event;

public class BDRepo {

    private final static MutableLiveData<List<Event>> sEvents = new MutableLiveData<>();

    static{
        sEvents.setValue(Collections.<Event>emptyList());
    }

    private final Context mContext;

    public BDRepo(Context context){
        mContext = context;
        refresh();
    }

    public LiveData<List<Event>> getBD(){
        return sEvents;
    }

    public void refresh(){

    }

    public void insertEventBD(Event event){
        new Thread(new Runnable() {
            public void run() {
                EventTable eventDb = new EventTable(event);
                DBHelper.getInstance(mContext).getDb().getDao().insert(eventDb);
            }
        }).start();
    }
}
