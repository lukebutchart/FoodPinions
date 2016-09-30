package com.stand_still.foodpinions.classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodPinion {
    int id;
    Dish dish;
    String comment;
    Date date;

    //    int dishID;
    Restaurant restaurant;
    //    int restaurantID;
    DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

//    // Used outside of dbhandler
//    public FoodPinion(Dish dish, String comment, String dateTimeString) {
//        this.dish = dish;
//        this.comment = comment;
//        try {
//            this.date = dateFormat.parse(dateTimeString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    public FoodPinion(Dish dish, String comment) {
        this.dish = dish;
        this.comment = comment;
        this.date = new Date();
    }

    // Only used in DbHandler
    public FoodPinion(int id, Dish dish, String comment, String dateTimeString) {
        this.id = id;
        this.dish = dish;
        this.comment = comment;
        try {
            this.date = dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    public FoodPinion(int id, int dishID /*String name, String restaurant*/, String comment/*, float rating*/) {
//        DatabaseHandler databaseHandler = new DatabaseHandler();
//        Dish dish = databaseHandler.getDish(dishID);
//        editFoodPinion(dish.getName(), dish.getRestaurant(), comment/*, rating*/);
//    }
//
//    public FoodPinion(int id, int dishID, String comment, String dateTimeString) {
//        DatabaseHandler databaseHandler = new DatabaseHandler();
//        Dish dish = databaseHandler.getDish(dishID);
//        editFoodPinion(dish.getName(), dish.getRestaurant(), comment/*, rating*/);
//    }

    public FoodPinion() {

    }

    public void editFoodPinion(String name, String restaurant, String comment/*, float rating*/) {
        editFoodPinion(name, restaurant, comment, new Date().toString());
    }

    public void editFoodPinion(String name, String restaurant, String comment, String dateTimeString) {
        this.dish.setName(name);
        this.restaurant.setName(restaurant);
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

//    public float getRating() {
//        return rating;
//    }

    public Date getDate() {
        return date;
    }

    public String getRestaurant() {
        return restaurant.getName();
    }

    public String getComment() {
        return comment;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant.setName(restaurant);
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

//    public void setDishID(int dishID) {
//        this.name = getDish(dishID);
//    }

    public String getDateTimeString() {
        return date.toString();
    }

    public int getID() {
        return id;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

//    public void setRating(float rating) {
//        this.rating = rating;
//    }
}
