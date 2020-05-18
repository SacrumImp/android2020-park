package ru.techpark.agregator.event;

public class Date {
    private long start;
    private long end;
    private String start_date;
    private String start_time;

    public Date(String start_date, String start_time, long start, long end) {
        this.start_date = start_date;
        this.start_time = start_time;
        this.start = start;
        this.end = end;
    }
    public Date(String start_date, String start_time) {
        this.start_date = start_date;
        this.start_time = start_time;
    }


    public long getStart() {
        return  start;
    }
    public void setStart(int start) {
        this.start = start;
    }

    public String getStart_date(){return  start_date;}
    public String getStart_time(){return  start_time;}

    public void setStart_date(String start_date){this.start_date=start_date;}
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public long getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
}
