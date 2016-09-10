package com.stand_still.foodpinions.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stand_still.foodpinions.R;

import java.util.ArrayList;

/**
 * Created by Luke on 10/09/2016.
 */
public class CustomArrayAdapter extends ArrayAdapter {
    private final Context context;
    //    private final String[] values;
    private final ArrayList<String> values;
    private final OutgoingsList outgoingsList;

//    public CustomArrayAdapter(Context context, String[] values) {
//        super(context, R.layout.list_custom, values);
//        this.context = context;
//        this.values = values;
//    }

    public CustomArrayAdapter(Context context, OutgoingsList outgoings) {
        super(context, R.layout.list_custom, outgoings.getNames());
        this.context = context;
        this.values = outgoings.getNames();
        this.outgoingsList = outgoings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_custom, parent, false);
        TextView nameColumn = (TextView) rowView.findViewById(R.id.name_column);
        TextView costColumn = (TextView) rowView.findViewById(R.id.cost_column);
        TextView frequencyColumn = (TextView) rowView.findViewById(R.id.frequency_column);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        nameColumn.setText(outgoingsList.get(position).getName());
        costColumn.setText(Integer.toString(outgoingsList.get(position).getCost()));
        frequencyColumn.setText(Integer.toString(outgoingsList.get(position).getFrequency()));

//        // Change icon based on name
//        String s = values[position];
//
//        System.out.println(s);
//
//        if (s.equals("WindowsMobile")) {
//            imageView.setImageResource(R.drawable.windowsmobile_logo);
//        } else if (s.equals("iOS")) {
//            imageView.setImageResource(R.drawable.ios_logo);
//        } else if (s.equals("Blackberry")) {
//            imageView.setImageResource(R.drawable.blackberry_logo);
//        } else {
//            imageView.setImageResource(R.drawable.android_logo);
//        }

        return rowView;
    }
}
