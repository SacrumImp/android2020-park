package ru.techpark.agregator.localdata;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import ru.techpark.agregator.event.Date;
import ru.techpark.agregator.event.Image;

@Entity(tableName = "event_table")      // таблица всех ивентов
public class EventTable {

    @PrimaryKey(autoGenerate = true)
    public int id;     // id каждой записи (увеличивается автоматически)

    public Date dates;     // дата события (дата начала и дата конца)
    public String title;   // название
    public String description;     // краткое описание
    public String body_text;       // подробное описание
    public String price;       // цена
    List<Image> images;     // изображения (адрес string)

}
