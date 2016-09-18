package com.stand_still.foodpinions.classes;

import java.util.Date;

public class FoodPinion {
    String name;
    String restaurant;
    String comment;
    float rating;
    Date date;

    public FoodPinion(String name, String restaurant, String comment, float rating) {
        editFoodPinion(name, restaurant, comment, rating);
    }

    public void editFoodPinion(String name, String restaurant, String comment, float rating) {
        this.name = name;
        this.restaurant = restaurant;
        this.comment = comment;
        this.rating = rating;
        date = new Date();
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }

    public String getRestaurant() {
        return restaurant;
    }
}
