package ru.techpark.agregator.localdata;

import androidx.room.TypeConverter;
import ru.techpark.agregator.event.Date;

class DateConverter {

    @TypeConverter  // перевод из Date в String
    public String fromDate (Date dates){
        return dates.getStart() + "," + dates.getEnd();
    }

    @TypeConverter  // перевод из String в Date
    public Date toDate (String dates){
        String[] datesLine = dates.split(",");
        return new Date(datesLine[0], datesLine[1]);
    }
}
