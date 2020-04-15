package ru.techpark.agregator;

import android.app.Application;
import android.content.Context;

import ru.techpark.agregator.network.ApiRepo;

public class AgregatorApp extends Application {

    private ApiRepo mApiRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiRepo = new ApiRepo();
    }

    public ApiRepo getApis() {
        return mApiRepo;
    }

    public static AgregatorApp from(Context context) {
        return (AgregatorApp) context.getApplicationContext();
    }


}
