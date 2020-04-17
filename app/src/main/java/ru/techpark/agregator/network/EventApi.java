package ru.techpark.agregator.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    @GET("events/?fields=images,id,title,description&order_by=-publication_date")
    Call<FeedInfo> getFeedEvents(@Query("page") int page);

    @GET
    Call<ResponseBody> getImageBody(@Url String url);
}
