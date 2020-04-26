package ru.techpark.agregator.event;

import java.util.List;

public class Event {
    private int id;
    private String title;
    private String description;
    private String body_text;
    private String price;
    private List<Image> images;
    private Location location;
    private List<Date> dates;
    private Place place;

    Event(int id, String title, List<Image> images, String description) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.description = description;
    }
    public Event(int id, String title, List<Image> images, String description,
          String body_text, String price, List<Date> dates, Location location, Place place) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.description = description;
        this.body_text = body_text;
        this.price = price;
        this.dates = dates;
        this.location = location;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody_text() {
        return body_text;
    }

    public void setBody_text(String body_text) {
        this.body_text = body_text;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Place getPlace() {
        return place;
    }
}