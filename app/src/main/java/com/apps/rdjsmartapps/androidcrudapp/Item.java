package com.apps.rdjsmartapps.androidcrudapp;

import android.graphics.Bitmap;

/**
 * Created by Harshal on 4/1/2018.
 */

public class Item {

    private int Id, imageId;
    private String movieName;
    private Bitmap image;
    private int rating;

    //Constructor

    public Item(int Id, String movieName, int rating, Bitmap image) {
        this.Id = Id;
        this.movieName = movieName;
        this.rating = rating;
        this.image = image;
    }

    // setters and getters


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = Id;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
