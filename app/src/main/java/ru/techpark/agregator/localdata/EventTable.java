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
    public int id;     // id каждой записи (увеличивается автоматически)

    @TypeConverters({DateConverter.class})
    public Date dates;       // дата события
    public String title;   // название
    public String description;     // краткое описание
    public String body_text;       // подробное описание
    public String price;       // цена
    @TypeConverters({ImageConverter.class})
    public List<Image> images;     // изображения (адрес string)

    public EventTable(Date dates, String title, String description, String body_text, String price, List<Image> images) {
        this.dates = dates;
        this.title = title;
        this.description = description;
        this.body_text = body_text;
        this.price = price;
        this.images = images;
    }
}
