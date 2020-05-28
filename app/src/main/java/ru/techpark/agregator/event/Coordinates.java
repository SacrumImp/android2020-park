package ru.techpark.agregator.event;

public class Coordinates {
    private String lat;
    private String lon;

    public Coordinates(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLatitude() {
        return lat;
    }

    public void setLatitude(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return lon;
    }

    public void setLongitude(String lon) {
        this.lon = lon;
    }
}
