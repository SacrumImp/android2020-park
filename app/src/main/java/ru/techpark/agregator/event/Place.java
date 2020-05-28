package ru.techpark.agregator.event;

public class Place {
    private String title;
    private String address;
    private String phone;
    private Coordinates coords;

    public Place(String title, String address, String phone) {
        this.title = title;
        this.address = address;
        this.phone = phone;
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
