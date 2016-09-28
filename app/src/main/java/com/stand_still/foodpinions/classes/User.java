package com.stand_still.foodpinions.classes;

import java.util.Collections;
import java.util.Comparator;

public class User {
    protected static String name;

    public User(String name){
        User.name = name;
    }

    static FoodPinionArrayList foodPinions = new FoodPinionArrayList();

    public static void addFoodPinion(FoodPinion foodPinion){
        foodPinions.add(foodPinion);
    }

    public static FoodPinionArrayList getFoodPinions() {
        Collections.sort(foodPinions, new Comparator<FoodPinion>() {
            @Override
            public int compare(FoodPinion x, FoodPinion y) {
                int timeComparison = y.getDate().compareTo(x.getDate());
                return timeComparison != 0 ? timeComparison
                        : x.getName().compareTo(y.getName());
            }
        });
        return foodPinions;
    }

    public static FoodPinion getFoodPinion(String name){
        return getFoodPinions().get(name);
    }
}