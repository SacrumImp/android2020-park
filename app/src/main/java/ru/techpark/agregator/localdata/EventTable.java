package ru.techpark.agregator.localdata;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import ru.techpark.agregator.event.Date;
import ru.techpark.agregator.event.Image;
import ru.techpark.agregator.event.Location;
import ru.techpark.agregator.event.Place;

@Entity(tableName = "event_table")      // таблица всех ивентов
public class EventTable {

    @PrimaryKey(autoGenerate = true)
    public int id;     // id каждой записи (увеличивается автоматически)

    public String title;   // название
    public String description;     // краткое описание
    public String body_text;       // подробное описание
    public String price;       // цена
    @TypeConverters({ImageConverter.class})
    public List<Image> images;     // изображения (адрес string)
    @TypeConverters({LocationConverter.class})
    public Location location;
    @TypeConverters({DateConverter.class})
    public Date dates;       // дата события
    @TypeConverters({PlaceConverter.class})
    public Place place;

    public EventTable(String title, String description, String body_text, String price, List<Image> images, Location location, Date dates, Place place) {
        this.dates = dates;
        this.title = title;
        this.description = description;
        this.body_text = body_text;
        this.price = price;
        this.images = images;
        this.location = location;
        this.place = place;
    }
}
