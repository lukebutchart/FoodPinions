package com.stand_still.foodpinions.classes;

public class Dish extends FoodPinionProperty {
    private Restaurant restaurant;

    public Dish() {
        super();
    }

    public Dish(int id, String name, Restaurant restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }

    public Dish(String dishName, Restaurant restaurant) {
        super();
        this.name = dishName;
        this.restaurant = restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}