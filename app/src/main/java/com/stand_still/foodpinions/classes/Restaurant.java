package com.stand_still.foodpinions.classes;

public class Restaurant extends FoodPinionProperty {
    public Restaurant(int id, String name) {
        super(id, name);
    }

    public Restaurant() {
        super();
    }

    public Restaurant(String restaurantName) {
        super();
        this.name = restaurantName;
    }
}