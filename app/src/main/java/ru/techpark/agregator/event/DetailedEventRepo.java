package ru.techpark.agregator.event;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ru.techpark.agregator.network.ApiRepo;
import ru.techpark.agregator.network.EventApi;

public class DetailedEventRepo {
    private static final String TAG = "DetailedEventRepo";
    private final MutableLiveData<Event> mEvent = new MutableLiveData<>();

    private final Context mContext;

    public DetailedEventRepo (Context context) {
        mContext = context;
    }


    public LiveData<Event> getEvent() {
        return mEvent;
    }


    private static Event transform(EventApi.DetailedEvent detailedEvent) {
        List<Image> images = new ArrayList<>();
        if (detailedEvent.images.size() > 0)
            images.add(new Image(detailedEvent.images.get(0).image));
        List<Date> dates = new ArrayList<>();
        if (detailedEvent.dates.size()>0)
            dates.add(new Date(detailedEvent.dates.get(0).getStart_date(), detailedEvent.dates.get(0).getStart_time(),
                    detailedEvent.dates.get(0).getStart(),detailedEvent.dates.get(0).getEnd()));
        Log.d(TAG, "id" + detailedEvent.id);
        return new Event(
                detailedEvent.id,
                detailedEvent.title,
                images,
                detailedEvent.description,
                detailedEvent.body_text, detailedEvent.price, dates, detailedEvent.location, detailedEvent.place
        );
    }
    public void getCertainEvent(int id){
        final EventApi api = ApiRepo.from(mContext).getEventApi();
        //  final Event[] event = new Event[1];
        Log.d(TAG, "try");
        api.getDetailedEvent(id).enqueue(new Callback<EventApi.DetailedEvent>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventApi.DetailedEvent> call, Response<EventApi.DetailedEvent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mEvent.postValue(transform(response.body()));
                    Log.d(TAG, "post");
                }
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventApi.DetailedEvent> call, Throwable t) {
                mEvent.postValue(null);
                Log.e(TAG, "Failed to load event", t);
            }
        });
    }
}
