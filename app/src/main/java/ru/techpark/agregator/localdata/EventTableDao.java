package ru.techpark.agregator.localdata;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import ru.techpark.agregator.event.Event;

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

        @Query("SELECT * FROM event_table WHERE (id = :certainId)")
        List<EventTable> getEvent(int certainId);

        @Query("SELECT * FROM event_table WHERE (description LIKE ('%' + :searchQuery + '%'))")
        List<EventTable> getSearchEvent(String searchQuery);

        // удаление записи
        @Delete
        void delete(EventTable ... tableEvents);
}
