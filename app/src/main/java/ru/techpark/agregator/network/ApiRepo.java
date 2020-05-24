package ru.techpark.agregator.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import ru.techpark.agregator.AgregatorApp;

public class ApiRepo {

    private final EventApi mEventApi;
    private final OkHttpClient mOkHttpClient;
    private static final String BASE_URL = "https://kudago.com/public-api/v1.4/";


    public ApiRepo() {
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .build();

        mEventApi = retrofit.create(EventApi.class);
    }

    public EventApi getEventApi() {
        return mEventApi;
    }

    public static ApiRepo from(Context context) {
        return AgregatorApp.from(context).getApis();
    }

}
