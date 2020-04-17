package ru.techpark.agregator.localdata;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface EventTableDao {

        // добавление строки
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insertAll(EventTable ... tableEvents);

        // получение всех строк
        @Query("SELECT * FROM event_table")
        List<EventTable> getAllEvents();

        // удаление записи
        @Delete
        void delete(EventTable ... tableEvents);
}
