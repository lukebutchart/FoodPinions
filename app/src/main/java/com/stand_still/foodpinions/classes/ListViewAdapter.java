package com.stand_still.foodpinions.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stand_still.foodpinions.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView textFirst;
    TextView textSecond;
    TextView textThird;
    TextView textFourth;

    public ListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = activity.getLayoutInflater();
        if (convertView == null){
            convertView = inflater.inflate(R.layout.column_row, null);
            textFirst = (TextView) convertView.findViewById(R.id.dish);
            textSecond = (TextView) convertView.findViewById(R.id.restaurant);
            textThird = (TextView) convertView.findViewById(R.id.comment);
            textFourth = (TextView) convertView.findViewById(R.id.datetime);
        }
        HashMap<String, String> map = list.get(position);
        textFirst.setText(map.get(Constants.DISH_NAME_COLUMN));
        textSecond.setText(map.get(Constants.RESTAURANT_NAME_COLUMN));
        textThird.setText(map.get(Constants.COMMENT_COLUMN));
        textFourth.setText(map.get(Constants.DATE_TIME_COLUMN));
        return convertView;
    }
}
