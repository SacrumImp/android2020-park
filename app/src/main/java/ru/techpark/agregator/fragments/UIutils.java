package ru.techpark.agregator.fragments;

import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.techpark.agregator.event.Event;

public class UIutils {

    public static void setTimeInformation(Event event, TextView time_start, TextView date_start) {
        time_start.setVisibility(View.VISIBLE);
        date_start.setVisibility(View.VISIBLE);
        GregorianCalendar startTime = new GregorianCalendar();
        startTime.setTimeInMillis(event.getDates().get(0).getStart() * 1000L + 10800000L);
        String month;
        String minute;
        String day;
        int correctMonth = startTime.get(Calendar.MONTH) + 1;
        if (correctMonth < 10)
            month = "0" + correctMonth;
        else
            month = String.valueOf(correctMonth);
        startTime.get(Calendar.MINUTE);
        if (startTime.get(Calendar.MINUTE) < 10)
            minute = "0" + startTime.get(Calendar.MINUTE);
        else
            minute = String.valueOf(startTime.get(Calendar.MINUTE));
        startTime.get(Calendar.DAY_OF_MONTH);
        if (startTime.get(Calendar.DAY_OF_MONTH) < 10)
            day = "0" + startTime.get(Calendar.DAY_OF_MONTH);
        else
            day = String.valueOf(startTime.get(Calendar.DAY_OF_MONTH));
        date_start.setText(day + "." + month + "." + startTime.get(Calendar.YEAR));
        time_start.setText(startTime.get(Calendar.HOUR_OF_DAY) + ":" + minute);
    }

    public static boolean hasTime(Event event) {
        return event.getDates()!= null && event.getDates().get(0)!= null &&
                event.getDates().get(0).getStart_date() != null &&
                event.getDates().get(0).getStart_time() != null &&
                event.getDates().get(0).getStart() != 0 &&
                !(event.getDates().get(0).getStart_date().equals("null")  || event.getDates().get(0).getStart_time().equals("null"));
    }
}
