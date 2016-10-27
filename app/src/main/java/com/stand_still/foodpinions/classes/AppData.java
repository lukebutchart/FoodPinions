package com.stand_still.foodpinions.classes;

import android.content.ContentValues;
import android.content.Context;
import android.widget.LinearLayout;

import com.stand_still.foodpinions.activities.MainActivity;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppData {

    // === Add ===

    public static void addRestaurant(Restaurant restaurant, Context context) {
        String restaurantName = restaurant.getName();

        DatabaseHandler dbHandler = new DatabaseHandler(context);

        Restaurant restaurantByName = dbHandler.getRestaurantByName(restaurantName);

        if (restaurantByName == null) {
            dbHandler.addRestaurant(restaurant);
        } else {
            restaurant.setID(restaurantByName.getID());
            dbHandler.updateRestaurant(restaurant);
        }

        Restaurant addedRestaurant = dbHandler.getRestaurantByName(restaurantName);
        restaurant.setID(addedRestaurant.getID());
    }

    public static void addDish(Dish dish, Context context) {
        String dishName = dish.getName();
        Restaurant dishRestaurant = dish.getRestaurant();

        DatabaseHandler dbHandler = new DatabaseHandler(context);

        Dish dishByPair = dbHandler.getDishByPair(dishName, dishRestaurant);

        if (dishByPair == null) {
            dbHandler.addDish(dish);
        } else {
            dish.setID(dishByPair.getID());
            dbHandler.updateDish(dish);
        }

        Dish addedDish = dbHandler.getDishByPair(dishName, dishRestaurant);
        dish.setID(addedDish.getID());
    }

    public static void addFoodPinion(FoodPinion foodPinion, Context context) {
        Dish dish = foodPinion.getDish();
        User user = foodPinion.getUser();

        DatabaseHandler dbHandler = new DatabaseHandler(context);

        FoodPinion foodPinionByPair = dbHandler.getFoodPinionByPair(dish, user);

        if (foodPinionByPair == null) {
            dbHandler.addFoodPinion(foodPinion);
        } else {
            foodPinion.setID(foodPinionByPair.getID());
            dbHandler.updateFoodPinion(foodPinion);
        }

        FoodPinion addedFoodPinion = dbHandler.getFoodPinionByPair(
                dish, user);
        foodPinion.setID(addedFoodPinion.getID());
    }

    public static void addUser(User user, Context context) {
        String userName = user.getName();

        DatabaseHandler dbHandler = new DatabaseHandler(context);

        User userByName = dbHandler.getUserByName(userName);

        if (userByName == null) {
            dbHandler.addUser(user);
        } else {
            user.setID(userByName.getID());
            dbHandler.updateUser(user);
        }

        User addedUser = dbHandler.getUserByName(userName);
        user.setID(addedUser.getID());
    }

    // === Count ===

    public static int getRestaurantCount(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getRestaurantsCount();
    }

    public static int getDishCount(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getDishesCount();
    }

    public static int getFoodPinionCount(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getFoodPinionsCount();
    }

    public static int getUserCount(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getUserCount();
    }

    // === List ===

    public static List<Restaurant> getAllRestaurants(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getAllRestaurants();
    }

    public static List<Dish> getAllDishes(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getAllDishes();
    }

    public static List<FoodPinion> getAllFoodPinions(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getAllFoodPinions();
    }

    public static FoodPinionArrayList getAllFoodPinionsArrayList(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getAllFoodPinionsArrayList();
    }

    public static ArrayList<HashMap<String, String>> getAllFoodPinionsHashMapList(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getAllFoodPinionsHashMapList();
    }

    public static List<User> getAllUsers(Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getAllUsers();
    }

    // === Get ===

    public static Restaurant getRestaurant(int id, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getRestaurant(id);
    }

    public static Restaurant getRestaurant(String name, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getRestaurantByName(name);
    }

    public static Dish getDish(int id, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getDish(id);
    }

    public static Dish getDish(String name, Restaurant restaurant, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getDishByPair(name, restaurant);
    }

    public static FoodPinion getFoodPinion(int id, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getFoodPinion(id);
    }

    public static FoodPinion getFoodPinion(Dish dish, User user, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getFoodPinionByPair(dish, user);
    }

    public static User getUser(int id, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getUser(id);
    }

    public static User getUser(String name, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.getUserByName(name);
    }

    // === Update ===

    public static int updateRestaurant(Restaurant restaurant, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.updateRestaurant(restaurant);
    }

    public static int updateDish(Dish dish, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.updateDish(dish);
    }

    public static int updateFoodPinion(FoodPinion foodPinion, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.updateFoodPinion(foodPinion);
    }

    public static int updateUser(User user, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        return dbHandler.updateUser(user);
    }

    // === Delete ===

    public static void deleteRestaurant(Restaurant restaurant, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.deleteRestaurant(restaurant);
    }

    public static void deleteDish(Dish dish, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.deleteDish(dish);
    }

    public static void deleteFoodPinion(FoodPinion foodPinion, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.deleteFoodPinion(foodPinion);
    }

    public static void deleteUser(User user, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.deleteUser(user);
    }
}