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
    private static List<Event> eventsInFeed = new ArrayList<>();

    static {
        mEvents.setValue(Collections.emptyList());
    }

    private final Context mContext;

    public EventRepo(Context context) {
        mContext = context;
    }


    public LiveData<List<Event>> getEvents() {
        return mEvents;
    }


    public void addDataFeed(final int page) {
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        api.getFeedEvents(page).enqueue(new Callback<EventApi.FeedInfo>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventApi.FeedInfo> call, Response<EventApi.FeedInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (page == 1)
                        eventsInFeed.clear();
                    transformFeed(response.body());
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

    private void transformFeed(EventApi.FeedInfo feedInfo) {
        for (EventApi.Event feedEvent : feedInfo.results) {
            Event event = mapEvent(feedEvent);
            eventsInFeed.add(event);
        }
    }

    private static Event mapEvent(EventApi.Event feedEvent) {

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

    public void addDataSearch(String searchQuery, int page) {
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        api.getSearchResult(page, searchQuery).enqueue(new Callback<EventApi.SearchInfo>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventApi.SearchInfo> call, Response<EventApi.SearchInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (page == 1)
                        eventsInFeed.clear();
                    transformSearch(response.body());
                    mEvents.postValue(eventsInFeed);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventApi.SearchInfo> call, Throwable t) {
                Log.e(TAG, "Failed to load", t);
            }
        });
    }

    private void transformSearch(EventApi.SearchInfo body) {
        for (EventApi.SearchEvent searchEvent : body.results) {
            Event event = mapSearchEvent(searchEvent);
            eventsInFeed.add(event);
        }
    }

    private static Event mapSearchEvent(EventApi.SearchEvent searchEvent) {

        List<Image> images = new ArrayList<>();
        images.add(new Image(searchEvent.first_image.image));
        return new Event(
                searchEvent.id,
                searchEvent.title,
                images,
                searchEvent.description
        );
    }
}