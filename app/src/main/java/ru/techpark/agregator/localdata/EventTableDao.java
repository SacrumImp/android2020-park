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
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(EventTable tableEvent);

        // получение всех строк
        @Query("SELECT * FROM event_table ORDER BY position DESC")
        List<EventTable> getAllEvents();

        @Query("SELECT * FROM event_table WHERE (id = :certainId)")
        List<EventTable> getEvent(int certainId);

        @Query("SELECT * FROM event_table WHERE ((description LIKE :searchQuery) OR (title LIKE :searchQuery))")
        List<EventTable> getSearchEvent(String searchQuery);

        // удаление записи
        @Query("DELETE FROM event_table WHERE id == :idTableEvent")
        void delete(int idTableEvent);
}
