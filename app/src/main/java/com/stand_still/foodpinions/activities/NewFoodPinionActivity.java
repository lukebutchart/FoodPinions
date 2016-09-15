package com.stand_still.foodpinions.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.User;

import java.util.ArrayList;

public class NewFoodPinionActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText commentEditText;
    RatingBar ratingRatingBar;
    String name;
    String comment;
    float rating;
    FoodPinion foodPinion;

    ArrayList<EditText> editTextFields = new ArrayList<>();

    final float RATING_DEFAULT = 2.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food_pinion);

        Intent intent = getIntent();
        String searchValue = intent.getStringExtra(MainActivity.EXTRA_SEARCH_VALUE);

        nameEditText = (EditText) findViewById(R.id.name_newFoodPinion_editText);
        editTextFields.add(nameEditText);
        commentEditText = (EditText) findViewById(R.id.comment_newFoodPinion_editText);
        editTextFields.add(commentEditText);
        ratingRatingBar = (RatingBar) findViewById(R.id.rating_newFoodPinion_ratingBar);
        ratingRatingBar.setRating(RATING_DEFAULT);

        nameEditText.setText(searchValue);
    }

    public void createFoodPinion(View view) {
        name = nameEditText.getText().toString();
        comment = commentEditText.getText().toString();
        rating = ratingRatingBar.getRating();

        if (checkFieldsAreValid()) {
            foodPinion = new FoodPinion(name, comment, rating);
            clearFields();
            Toast.makeText(
                    getApplicationContext(),
                    String.format("FoodPinion \"%s\" added", name),
                    Toast.LENGTH_SHORT).show();
        }


        User.addFoodPinion(foodPinion);

        clearFields();
    }

    void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError("Field is empty");
    }

    private boolean checkFieldsAreValid() {
        boolean returnValue = true;
        for (EditText editTextField : editTextFields) {
            if (editTextField.getText().toString().isEmpty()) {
                showError(editTextField);
                returnValue = false;
            }
        }
        return returnValue;
    }

    private void clearFields() {
        nameEditText.getText().clear();
        commentEditText.getText().clear();
        ratingRatingBar.setRating(RATING_DEFAULT);
    }
}
