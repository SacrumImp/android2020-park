package ru.techpark.agregator.event;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ru.techpark.agregator.R;
import ru.techpark.agregator.network.ApiRepo;
import ru.techpark.agregator.network.EventApi;

public class EventRepo {
    private final static MutableLiveData<List<Event>> mEvents = new MutableLiveData<>();
    private static final String TAG = "EventRepo";
    private static List<Event> eventsInFeed = new ArrayList<>();


    private final Context mContext;

    public EventRepo(Context context) {
        mContext = context;
    }


    public LiveData<List<Event>> getEvents() {
        return mEvents;
    }


    public void addDataFeed(final int page) {
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        String city = getLocation();
        String filter = mContext.getSharedPreferences(mContext.getString(R.string.pref_feed_file), Context.MODE_PRIVATE)
                .getString(mContext.getString(R.string.preference_filter), "");
        api.getFeedEvents(page, city, filter).enqueue(new Callback<EventApi.FeedInfo>() {
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
                mEvents.postValue(null);
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
        List<Date> dates = new ArrayList<>();
        if (feedEvent.images.size() > 0)
            images.add(new Image(feedEvent.images.get(0).image));
        if (feedEvent.dates.size() > 0)
            dates.add(new Date(feedEvent.dates.get(0).getStart_date(), feedEvent.dates.get(0).getStart_time(),
                    feedEvent.dates.get(0).getStart(), feedEvent.dates.get(0).getEnd()));

        return new Event(
                feedEvent.id,
                feedEvent.title.substring(0, 1).toUpperCase() + feedEvent.title.substring(1),
                images,
                feedEvent.description,
                dates
        );
    }

    public void addDataSearch(String searchQuery, int page) {
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        String city = getLocation();
        api.getSearchResult(page, searchQuery, city).enqueue(new Callback<EventApi.SearchInfo>() {
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

    private String getLocation() {
        if (!PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("enable_online", false))
            return "online";
        else
            return PreferenceManager.getDefaultSharedPreferences(mContext).getString("city_list", "");
    }

    private static Event mapSearchEvent(EventApi.SearchEvent searchEvent) {

        List<Image> images = new ArrayList<>();
        images.add(new Image(searchEvent.first_image.image));
        return new Event(
                searchEvent.id,
                searchEvent.title.substring(0, 1).toUpperCase() + searchEvent.title.substring(1),
                images,
                searchEvent.description
        );
    }

    public boolean isEmpty() {
        return eventsInFeed.isEmpty();
    }
}