package ru.techpark.agregator.localdata;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ru.techpark.agregator.event.Date;
import ru.techpark.agregator.event.Image;

@Entity(tableName = "event_table")      // таблица всех ивентов
public class EventTable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;     // id каждой записи (увеличивается автоматически)

    @TypeConverters({DateConverter.class})
    public Date dates;       // дата события
    public String title;   // название
    public String description;     // краткое описание
    public String body_text;       // подробное описание
    public String price;       // цена
    @TypeConverters({ImageConverter.class})
    public List<Image> images;     // изображения (адрес string)

    public  EventTable(){

    }

}
