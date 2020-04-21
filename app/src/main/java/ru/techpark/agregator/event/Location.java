package ru.techpark.agregator.event;

public class Location {
    private String slug;
    private  String name;
    private  String timezone;

    public Location(String name) {
        this.name = name;
    }

    public Location(String slug, String name, String timezone){
        this.slug = slug;
        this.name = name;
        this.timezone = timezone;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
