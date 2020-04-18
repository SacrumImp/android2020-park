package ru.techpark.agregator.localdata;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface EventTableDao {

        // добавление множества строки
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insertAll(EventTable ... tableEvents);

        // добавление строки
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(EventTable tableEvent);

        // получение всех строк
        @Query("SELECT * FROM event_table")
        List<EventTable> getAllEvents();

        // удаление записи
        @Delete
        void delete(EventTable ... tableEvents);
}
