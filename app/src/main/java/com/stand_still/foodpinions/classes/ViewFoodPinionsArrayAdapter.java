package com.stand_still.foodpinions.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stand_still.foodpinions.R;

import java.util.ArrayList;

public class ViewFoodPinionsArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final FoodPinionArrayList foodPinionsList;

    public ViewFoodPinionsArrayAdapter(Context context, FoodPinionArrayList foodPinions) {
        super(context, R.layout.list_view_food_pinions, foodPinions.getNames());
        this.context = context;
        this.foodPinionsList = foodPinions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_food_pinions, parent, false);
        TextView nameColumn = (TextView) rowView.findViewById(R.id.name_foodpinion_column);
        TextView restaurantColumn = (TextView) rowView.findViewById(R.id.restaurant_foodpinion_column);
        nameColumn.setText(foodPinionsList.get(position).getName());
        restaurantColumn.setText(foodPinionsList.get(position).getRestaurant());
        return rowView;
    }
}
