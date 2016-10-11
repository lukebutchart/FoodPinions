package com.stand_still.foodpinions.classes;

import android.content.Context;

import java.util.List;

public class MyDatabase {
    public static void addRestaurant(Restaurant restaurant, Context context){
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.addRestaurant(restaurant);
        Restaurant addedRestaurant = dbHandler.getRestaurantByName(restaurant.getName());
        restaurant.setID(addedRestaurant.getID());
    }

    public static void addDish(Dish dish, Context context){
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.addDish(dish);
        Dish addedDish = dbHandler.getDishByPair(dish.getName(), dish.getRestaurant());
        dish.setID(addedDish.getID());
    }

    public static void addFoodPinion(FoodPinion foodPinion, Context context){
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.addFoodPinion(foodPinion);

        List<Restaurant> restaurants = dbHandler.getAllRestaurants();
        List<Dish> dishes = dbHandler.getAllDishes();
        List<FoodPinion> foodPinions = dbHandler.getAllFoodPinions(); // Todo: Fix this returning null

        FoodPinion addedFoodPinion = dbHandler.getFoodPinionByPair(
                foodPinion.getDish(),
                foodPinion.getUser()
        );
        foodPinion.setID(addedFoodPinion.getID());
    }
}
