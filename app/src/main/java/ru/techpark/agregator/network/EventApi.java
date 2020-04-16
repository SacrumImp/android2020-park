package ru.techpark.agregator.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import ru.techpark.agregator.event.Date;
import ru.techpark.agregator.event.Image;

public interface EventApi {


     class Event {
        public int id;
        public String title;
        public List<Image> images;
        public String description;
    }
     class Image {
        public String image;
    }
     class FeedInfo {
        public int count;
        public String next;
        public String previous;
        public List<Event> results;
    }
    class DetailedEvent {
        public int id;
        public String title;
        public List<Image> images;
        public String description;
        public Date date;
        public String location;
        public String body_text;
        public String price;

    }

    @GET("events/?fields=images,id,title,description&order_by=-publication_date")
    Call<FeedInfo> getFeedEvents();

    @GET("events/{event_id}/?fields=images,id,title,description,dates,location,body_text,price")
    Call<DetailedEvent> getDetailedEvent(@Path("event_id") int id);

    @GET
    Call<ResponseBody> getImageBody(@Url String url);

}
