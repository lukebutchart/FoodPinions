package com.stand_still.foodpinions.classes;

import java.sql.Time;
import java.util.Date;

public class FoodPinion {
    String name;
    String comment;
    float rating;
    Date date;

    public FoodPinion(String name, String comment, float rating){
        this.name = name;
        this.comment = comment;
        this.rating = rating;
        date = new Date();
    }

    public void editFoodPinion(String name, String comment, float rating){
        this.name = name;
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
}
