package ru.techpark.agregator.localdata;

import android.content.Context;

import androidx.room.Room;

class DBHelper {

    private static DBHelper ourInstance;
    private final AppDatabase appDatabase;

    static DBHelper getInstance(Context context){
        if(ourInstance == null){
            ourInstance = new DBHelper(context);
        }
        return ourInstance;
    }

    AppDatabase getDb(){
        return appDatabase;
    }

    // создание таблицы
    private DBHelper(Context context){
        appDatabase = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "event_table.db")
                .fallbackToDestructiveMigration()
                .build();
    }
}
