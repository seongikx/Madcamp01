package com.example.bottomtab;

import android.graphics.Bitmap;

public class ImageData {
   // private int img;
    private String img;
    private String img_name;

    public ImageData(String img, String img_name) {
        this.img = img;
        this.img_name = img_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }
}

