package com.stand_still.foodpinions.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

public class FoodPinionArrayList extends ArrayList<FoodPinion> implements Comparator<FoodPinion> {
    ArrayList<String> names;
    ArrayList<String> restaurants;

    public ArrayList<String> getNames() {
        names = new ArrayList<>();
        for (FoodPinion foodPinion : this)
            names.add(foodPinion.getName());
        return names;
    }

    @Override
    public int compare(FoodPinion x, FoodPinion y) {
        int timeComparison = x.getDate().compareTo(y.getDate());
        return timeComparison != 0 ? timeComparison
                : x.getName().compareTo(y.getName());
    }

    public boolean containsWithName(String nameString) {
        return getNames().contains(nameString);
    }

    public FoodPinion getByName(String name) {
        for (FoodPinion foodPinion : this)
            if (foodPinion.getName().equals(name))
                return foodPinion;
        return null;
    }

    public FoodPinion getByRestaurant(String restaurant) {
        for (FoodPinion foodPinion : this)
            if (foodPinion.getRestaurant().equals(restaurant))
                return foodPinion;
        return null;
    }

    public boolean containsWithRestaurant(String restaurantString) {
        return getRestaurants().contains(restaurantString);
    }

    public ArrayList<String> getRestaurants() {
        restaurants = new ArrayList<>();
        for (FoodPinion foodPinion : this)
            restaurants.add(foodPinion.getRestaurant());
        return restaurants;
    }

    public FoodPinionArrayList getFoodPinionsWithName(String name){
        FoodPinionArrayList foodPinionArrayList = new FoodPinionArrayList();
        for (FoodPinion foodPinion : this)
            if (foodPinion.getName().equals(name))
                foodPinionArrayList.add(foodPinion);
        return foodPinionArrayList;
    }

    public FoodPinionArrayList getFoodPinionsWithRestaurant(String restaurant){
        FoodPinionArrayList foodPinionArrayList = new FoodPinionArrayList();
        for (FoodPinion foodPinion : this)
            if (foodPinion.getRestaurant().equals(restaurant))
                foodPinionArrayList.add(foodPinion);
        return foodPinionArrayList;
    }

//    private int compare(Date date, Date date1) {
//        return date.compareTo() < date1 ? -1
//                : date > date1 ? 1
//                : 0;
//    }


}
