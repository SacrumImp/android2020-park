package ru.techpark.agregator.event;

import android.graphics.Bitmap;

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

