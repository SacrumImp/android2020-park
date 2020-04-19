package ru.techpark.agregator.localdata;

import androidx.room.TypeConverter;
import ru.techpark.agregator.event.Place;

public class PlaceConverter {

    @TypeConverter
    public String fromPlace(Place place){
        if(place == null) return "";
        else return place.getTitle() + "," + place.getAddress() + "," + place.getPhone();
    }

    @TypeConverter
    public Place toPlace(String line){
        String[] arr = line.split(",");
        return new Place(arr[0], arr[1], arr[2]);
    }

}
