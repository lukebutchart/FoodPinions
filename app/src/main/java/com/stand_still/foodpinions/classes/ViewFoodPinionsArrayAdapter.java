package com.stand_still.foodpinions.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stand_still.foodpinions.R;

public class ViewFoodPinionsArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final FoodPinionArrayList foodPinionsList;

    public ViewFoodPinionsArrayAdapter(Context context, FoodPinionArrayList foodPinions) {  // Todo: Recreate so that an array is returned onClick
        super(context, R.layout.list_view_food_pinions, foodPinions.getNames());
        this.context = context;
        this.foodPinionsList = foodPinions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_food_pinions, parent, false);

        // Find views
        TextView dishNameColumn = (TextView) rowView.findViewById(R.id.name_foodpinion_column);
        TextView restaurantNameColumn = (TextView) rowView.findViewById(R.id.restaurant_foodpinion_column);

        // Set values
        dishNameColumn.setText(foodPinionsList.get(position).getDishName());
        restaurantNameColumn.setText(foodPinionsList.get(position).getRestaurantName());

        return rowView;
    }

    @Override
    public Object getItem(int i) {
        return super.getItem(i);
    }
}