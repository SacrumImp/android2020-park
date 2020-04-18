package ru.techpark.agregator.event;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ru.techpark.agregator.network.ApiRepo;
import ru.techpark.agregator.network.EventApi;

public class EventRepo {
    private final static MutableLiveData<List<Event>> mEvents = new MutableLiveData<>();
    private static final String TAG = "EventRepo";
    private List<Event> eventsInFeed = new ArrayList<>();
    private final static MutableLiveData<Event> mEvent = new MutableLiveData<>();

    static {
        mEvents.setValue(Collections.<Event>emptyList());
        mEvent.setValue(null);

    }

    private final Context mContext;

    public EventRepo(Context context) {
        mContext = context;
        //todo вот это надо вставить перед открытием фрагмента, посмотри, как сделано в 6 лекции(я про рефреш)
        refresh();
    }


    public LiveData<List<Event>> getEvents() {
        return mEvents;
    }
    public LiveData<Event> getEvent() {
        return mEvent;
    }

    public void addData(final int page) {
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        api.getFeedEvents(page).enqueue(new Callback<EventApi.FeedInfo>() {
            @Override
            @EverythingIsNonNull
            public void onResponse( Call<EventApi.FeedInfo> call, Response<EventApi.FeedInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (page == 1)
                        eventsInFeed.clear();
                    transform(response.body());
                    mEvents.postValue(eventsInFeed);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventApi.FeedInfo> call, Throwable t) {
                Log.e(TAG, "Failed to load", t);
            }
        });
    }

    private void transform(EventApi.FeedInfo feedInfo) {
        for (EventApi.Event feedEvent : feedInfo.results) {
            Event event = map(feedEvent);
            eventsInFeed.add(event);
        }
    }

    //todo здесь map было писать не обязаельно(так как мы один раз это делаем)
    private static Event transform(EventApi.DetailedEvent detailedEvent) {
        List<Image> images = new ArrayList<>();
        if (detailedEvent.images.size() > 0)
            images.add(new Image(detailedEvent.images.get(0).image));
        List<Date> dates = new ArrayList<>();
        if (detailedEvent.dates.size()>0)
            dates.add(new Date(detailedEvent.dates.get(0).getStart_date(), detailedEvent.dates.get(0).getStart_time()));
        Log.d(TAG, "id" + detailedEvent.id);
        return new Event(
                detailedEvent.id,
                detailedEvent.title,
                images,
                detailedEvent.description,
                detailedEvent.body_text, detailedEvent.price, dates, detailedEvent.location, detailedEvent.place
        );
    }

    private static Event map(EventApi.Event feedEvent) {

        List<Image> images = new ArrayList<>();
        if (feedEvent.images.size() > 0)
            images.add(new Image(feedEvent.images.get(0).image));

        return new Event(
                feedEvent.id,
                feedEvent.title,
                images,
                feedEvent.description
        );
    }

    public void getCertainEvent(int id){
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        //  final Event[] event = new Event[1];
        Log.d(TAG, "try");
        api.getDetailedEvent(id).enqueue(new Callback<EventApi.DetailedEvent>() {
            @Override
            public void onResponse(Call<EventApi.DetailedEvent> call, Response<EventApi.DetailedEvent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mEvent.postValue(transform(response.body()));
                    Log.d(TAG, "post");
                }
            }
            @Override
            public void onFailure(Call<EventApi.DetailedEvent> call, Throwable t) {
                Log.e(TAG, "Failed to load event", t);

            }
        });
    }

}