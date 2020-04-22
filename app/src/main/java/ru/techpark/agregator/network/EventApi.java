package ru.techpark.agregator.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.techpark.agregator.event.Date;
import ru.techpark.agregator.event.Location;
import ru.techpark.agregator.event.Place;

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
        public String body_text;
        public String price;
        public List<Date> dates;
        public Location location;
        public Place place;
    }

    class SearchInfo {
        public int count;
        public String next;
        public String previous;
        public List<SearchEvent> results;
    }

    class SearchEvent {
        public int id;
        public String title;
        public Image first_image;
        public String description;
    }

    @GET("events/?fields=images,id,title,description&order_by=-publication_date")
    Call<FeedInfo> getFeedEvents(@Query("page") int page);

    @GET("events/{event_id}/?expand=location,dates,place&fields=images,id,title,description,location,body_text,price,dates,place")
    Call<DetailedEvent> getDetailedEvent(@Path("event_id") int id);

    @GET("search/?ctype=event&order_by=-publication_date")
    Call<SearchInfo> getSearchResult(@Query("page") int page, @Query("q") String searchQuery);
}