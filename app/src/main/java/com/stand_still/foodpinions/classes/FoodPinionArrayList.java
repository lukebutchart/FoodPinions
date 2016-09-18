package com.stand_still.foodpinions.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class FoodPinionArrayList extends ArrayList<FoodPinion> implements Comparator<FoodPinion> {
    ArrayList<String> names;

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

//    private int compare(Date date, Date date1) {
//        return date.compareTo() < date1 ? -1
//                : date > date1 ? 1
//                : 0;
//    }


}
