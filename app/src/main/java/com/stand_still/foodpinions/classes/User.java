package com.stand_still.foodpinions.classes;

import com.stand_still.foodpinions.exceptions.NonUniqueFoodPinionException;

import java.util.Collections;
import java.util.Comparator;

public class User {
    protected static String name;
    int id;

    public User(String name){
        User.name = name;
    }

    static FoodPinionArrayList foodPinions = new FoodPinionArrayList();

    public User(int id, String userName) {
        this.id = id;
        this.name = userName;
    }

    public static void addFoodPinion(FoodPinion foodPinion){
        foodPinions.add(foodPinion);
    }

    public static FoodPinionArrayList getFoodPinions() {
        Collections.sort(foodPinions, new Comparator<FoodPinion>() {
            @Override
            public int compare(FoodPinion x, FoodPinion y) {
                int timeComparison = y.getDate().compareTo(x.getDate());
                return timeComparison != 0 ? timeComparison
                        : x.getDishName().compareTo(y.getDishName());
            }
        });
        return foodPinions;
    }

    public static FoodPinionArrayList getFoodPinionByName(String name){
        return getFoodPinions().getFoodPinionsWithName(name);
    }

    public static FoodPinionArrayList getFoodPinionByRestaurant(String restaurant){
        return getFoodPinions().getFoodPinionsWithRestaurant(restaurant);
    }

    public static FoodPinion getFoodPinionByPair(String nameString, String restaurantString){
        FoodPinionArrayList doubleFilteredList = getFoodPinions().getFoodPinionsWithName(nameString).getFoodPinionsWithRestaurant(restaurantString);
        if (doubleFilteredList.size() > 1)
            try {
                throw new NonUniqueFoodPinionException();
            } catch (NonUniqueFoodPinionException e) {
//                e.printStackTrace();
                return null;
            }
        else if (doubleFilteredList.size() < 1)
            return null;
        else return doubleFilteredList.get(0);
    }

    public static boolean foodPinionExists(FoodPinion foodPinion) {
        // Todo: Make this correct (react correctly if more than one FoodPinion)
         return getFoodPinionByPair(foodPinion.getDishName(), foodPinion.getRestaurant()) != null;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }
}