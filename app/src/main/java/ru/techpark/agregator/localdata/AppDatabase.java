package ru.techpark.agregator.localdata;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {EventTable.class}, version = 4,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EventTableDao getDao();

}
