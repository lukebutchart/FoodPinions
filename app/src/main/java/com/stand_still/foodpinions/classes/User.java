package com.stand_still.foodpinions.classes;

public class User {
    protected static String name;

//    public User(){}

    public User(String name){
        User.name = name;
    }

    static FoodPinionArrayList foodPinions = new FoodPinionArrayList();

    public static void addFoodPinion(FoodPinion foodPinion){
        foodPinions.add(foodPinion);
    }
}
