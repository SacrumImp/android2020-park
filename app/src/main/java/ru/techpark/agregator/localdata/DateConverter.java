package ru.techpark.agregator.localdata;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

import ru.techpark.agregator.event.Date;

class DateConverter {

    @TypeConverter  // перевод из Date в String
    public String fromDate (List<Date> dates){
        return dates.get(0).getStart() + "," + dates.get(0).getEnd();
    }

    @TypeConverter  // перевод из String в Date
    public List<Date> toDate (String dates){
        String[] datesLine = dates.split(",");
        List<Date> dateRet = new ArrayList<>();
        dateRet.add(new Date(datesLine[0], datesLine[1]));
        return dateRet;
    }
}
