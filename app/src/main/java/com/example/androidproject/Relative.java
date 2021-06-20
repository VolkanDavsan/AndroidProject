package com.example.androidproject;

import android.net.Uri;

public class Relative {

    private String RelativeName;
    private String ImagePath;

    public Relative(){
        //needed empty constructor
    }
    public Relative(String relativeName, String imageUrl) {
        this.ImagePath = imageUrl;
        this.RelativeName = relativeName;
    }

    public String getRelativeName() {
        return RelativeName;
    }

    public void setImage(String image) {
        this.ImagePath = image;
    }

    public String getImage() {
        return ImagePath;
    }

    public void setRelativeName(String relativeName) {
        this.RelativeName = relativeName;
    }
}
