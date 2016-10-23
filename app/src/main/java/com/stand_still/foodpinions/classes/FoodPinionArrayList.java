package com.stand_still.foodpinions.classes;

import java.util.ArrayList;
import java.util.Comparator;

public class FoodPinionArrayList extends ArrayList<FoodPinion> implements Comparator<FoodPinion> {
    ArrayList<String> names;
    ArrayList<String> restaurants;

    public ArrayList<String> getNames() {
        names = new ArrayList<>();
        for (FoodPinion foodPinion : this)
            names.add(foodPinion.getDishName());
        return names;
    }

    @Override
    public int compare(FoodPinion x, FoodPinion y) {
        int timeComparison = x.getDate().compareTo(y.getDate());
        return timeComparison != 0 ? timeComparison
                : x.getDishName().compareTo(y.getDishName());
    }

    public boolean containsWithName(String nameString) {
        return getNames().contains(nameString);
    }

    public FoodPinion getByName(String name) {
        for (FoodPinion foodPinion : this)
            if (foodPinion.getDishName().equals(name))
                return foodPinion;
        return null;
    }

    public FoodPinion getByRestaurant(String restaurant) {
        for (FoodPinion foodPinion : this)
            if (foodPinion.getRestaurantName().equals(restaurant))
                return foodPinion;
        return null;
    }

    public boolean containsWithRestaurant(String restaurantString) {
        return getRestaurants().contains(restaurantString);
    }

    public ArrayList<String> getRestaurants() {
        restaurants = new ArrayList<>();
        for (FoodPinion foodPinion : this)
            restaurants.add(foodPinion.getRestaurantName());
        return restaurants;
    }

    public FoodPinionArrayList getFoodPinionsWithName(String name){
        FoodPinionArrayList foodPinionArrayList = new FoodPinionArrayList();
        for (FoodPinion foodPinion : this)
            if (foodPinion.getDishName().equals(name))
                foodPinionArrayList.add(foodPinion);
        return foodPinionArrayList;
    }

    public FoodPinionArrayList getFoodPinionsWithRestaurant(String restaurant){
        FoodPinionArrayList foodPinionArrayList = new FoodPinionArrayList();
        for (FoodPinion foodPinion : this)
            if (foodPinion.getRestaurantName().equals(restaurant))
                foodPinionArrayList.add(foodPinion);
        return foodPinionArrayList;
    }

//    private int compare(Date date, Date date1) {
//        return date.compareTo() < date1 ? -1
//                : date > date1 ? 1
//                : 0;
//    }


}
