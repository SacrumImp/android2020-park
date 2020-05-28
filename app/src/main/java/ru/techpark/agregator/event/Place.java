package ru.techpark.agregator.event;

public class Place {
    private String title;
    private String address;
    private String phone;
    private Coordinates coords;

    public Place(String title, String address, String phone, String latitude, String longitude) {
        this.title = title;
        this.address = address;
        this.phone = phone;
        coords = new Coordinates(latitude, longitude);
    }

    public Coordinates getCoordinates() {
        return coords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

}
