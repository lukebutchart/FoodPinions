package com.stand_still.foodpinions.classes;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodPinion {
    private int id;
    private Dish dish;
    private String comment;
    private Date date;  // Todo: Store date in DB as number instead of string
    private User user;

    DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    public FoodPinion() {
    }

    public FoodPinion(Dish dish, String comment, Context context) {
        this.dish = dish;
        this.comment = comment;
        this.date = new Date();
        this.user = Settings.getUser(context);
    }

    // Only used in DbHandler
    public FoodPinion(int id, Dish dish, String comment, String dateTimeString, User user) {
        setFields(id, dish, comment, dateTimeString);
        this.user = user;
    }

    private void setFields(int id, Dish dish, String comment, String dateTimeString) {
        this.id = id;
        this.dish = dish;
        this.comment = comment;
        try {
            this.date = dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void editFoodPinion(String name, String restaurant, String comment) {
        editFoodPinion(name, restaurant, comment, new Date().toString());
    }

    public void editFoodPinion(String dishName, String restaurantName, String comment, String dateTimeString) {
        this.dish.setName(dishName);
        this.dish.getRestaurant().setName(restaurantName);
        this.comment = comment;
        try {
            date = dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDishName() {
        return dish.getName();
    }

    public Date getDate() {
        return date;
    }

    public String getRestaurantName() {
        return dish.getRestaurant().getName();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public void setDateTime(String dateTime) {
        try {
            this.date = dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDateTimeString() {
        return date.toString();
    }

    public int getID() {
        return id;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public User getUser() {
        return user;
    }

    public Dish getDish() {
        return dish;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
