package ru.techpark.agregator.localdata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EventTable.class}, version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EventTableDao getDao();

}
