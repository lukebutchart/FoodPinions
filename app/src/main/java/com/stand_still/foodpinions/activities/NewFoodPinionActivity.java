package com.stand_still.foodpinions.activities;

import android.content.Intent;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.User;

public class NewFoodPinionActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText commentEditText;
    RatingBar ratingRatingBar;
    String name;
    String comment;
    float rating;
    FoodPinion foodPinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food_pinion);

        Intent intent = getIntent();
        String searchValue = intent.getStringExtra(MainActivity.EXTRA_SEARCH_VALUE);

        nameEditText = (EditText) findViewById(R.id.name_newFoodPinion_editText);
        commentEditText = (EditText) findViewById(R.id.comment_newFoodPinion_editText);
        ratingRatingBar = (RatingBar) findViewById(R.id.rating_newFoodPinion_ratingBar);

        nameEditText.setText(searchValue);

//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(searchValue);
//
//        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_new_food_pinion);
//        layout.addView(textView);
    }

    public void createFoodPinion(View view) {
        name = nameEditText.getText().toString();
        comment = commentEditText.getText().toString();
        rating = ratingRatingBar.getRating();

        foodPinion = new FoodPinion(name, comment, rating);

        User.addFoodPinion(foodPinion);
    }
}
