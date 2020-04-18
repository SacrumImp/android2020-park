package ru.techpark.agregator.localdata;

import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;
import ru.techpark.agregator.event.Image;


class ImageConverter {

    @TypeConverter  // перевод списка Images в String
    public String fromImage(List<Image> images){
        StringBuilder imageRet = new StringBuilder();
        for(Image img : images){
            imageRet.append(img.getImageUrl());
            imageRet.append(",");
        }
        return imageRet.substring(0, imageRet.length()-1);
    }

    @TypeConverter  //заполнение списка Images из String
    public List<Image> toImage(String images){
        String[] img = images.split(",");
        List<Image> imagesRet = new ArrayList<>();
        Image addImg;
        for(String imageUrl : img){
            addImg = new Image(imageUrl);
            imagesRet.add(addImg);
        }
        return imagesRet;
    }
}
