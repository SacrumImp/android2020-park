package ru.techpark.agregator.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import ru.techpark.agregator.AgregatorApp;

public class ApiRepo {

    private static final String TAG = "ApiRepo";
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
    public Bitmap getImage(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            final Response response = mOkHttpClient.newCall(request).execute();
            ResponseBody bodyWithImage = response.body();
            if (response.isSuccessful() && bodyWithImage != null)
                return BitmapFactory.decodeStream(bodyWithImage.byteStream());
        } catch (IOException e) {
            Log.d(TAG, "Error in getImage()");
            e.printStackTrace();
        }
        return null;
    }
    public EventApi getEventApi() {
        return mEventApi;
    }

    public static ApiRepo from(Context context) {
        return AgregatorApp.from(context).getApis();
    }

}
