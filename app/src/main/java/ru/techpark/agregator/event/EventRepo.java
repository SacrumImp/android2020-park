package ru.techpark.agregator.event;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.techpark.agregator.network.ApiRepo;
import ru.techpark.agregator.network.EventApi;

public class EventRepo {
    private final static MutableLiveData<List<Event>> mEvents = new MutableLiveData<>();
    private static final String TAG = "EventRepo";

    static {
        mEvents.setValue(Collections.<Event>emptyList());

    }

    private final Context mContext;

    public EventRepo(Context context) {
        mContext = context;
        refresh();
    }

    public LiveData<List<Event>> getEvents() {
        return mEvents;
    }

    public void refresh() {
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        api.getFeedEvents().enqueue(new Callback<EventApi.FeedInfo>() {
            @Override
            public void onResponse(Call<EventApi.FeedInfo> call, Response<EventApi.FeedInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mEvents.postValue(transform(response.body()));
                }
            }

            @Override
            public void onFailure(Call<EventApi.FeedInfo> call, Throwable t) {
                Log.e(TAG, "Failed to load", t);
            }
        });
    }

    private static List<Event> transform(EventApi.FeedInfo feedInfo) {
        List<Event> result = new ArrayList<>();
        for (EventApi.Event feedEvent : feedInfo.results) {
            Event lesson = map(feedEvent);
            result.add(lesson);
            Log.d(TAG, "Loaded " + lesson.getTitle() + " #" + lesson.getId());
        }
        return result;
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

}
