package com.stand_still.foodpinions.classes;

public class FoodPinionProperty {
    int id;
    String name;

    public FoodPinionProperty(){}

    public FoodPinionProperty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public int getID() {
        return id;
    }
}