package ru.techpark.agregator.event;

public class Image {
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

