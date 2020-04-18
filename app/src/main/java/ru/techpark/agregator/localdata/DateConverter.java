package ru.techpark.agregator.localdata;

import androidx.room.TypeConverter;
import ru.techpark.agregator.event.Date;

public class DateConverter {

    @TypeConverter  // перевод из Date в String
    public String fromDate (Date dates){
        return dates.getStart() + "," + dates.getEnd();
    }

    @TypeConverter  // перевод из String в Date
    public Date toDate (String dates){
        String[] datesLine = dates.split(",");
        Date dateRet = new Date();
        dateRet.setStart(Integer.parseInt(datesLine[0]));
        dateRet.setEnd(Integer.parseInt(datesLine[1]));
        return dateRet;
    }
}
