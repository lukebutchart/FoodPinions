package com.stand_still.foodpinions.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.Settings;
import com.stand_still.foodpinions.classes.User;

import java.util.ArrayList;

public class EditFoodPinionActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText restaurantEditText;
    EditText commentEditText;
    RatingBar ratingRatingBar;
    Button createFoodPinionButton;
    final float RATING_DEFAULT = 2.5f;
    FoodPinion currentFoodPinion;
    boolean editOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_pinion);
        // Retrieve intent information
        Intent intent = getIntent();
        String restaurantString = intent.getStringExtra(MainActivity.EXTRA_RESTAURANT_VALUE);
        String nameString = intent.getStringExtra(MainActivity.EXTRA_NAME_VALUE);

        boolean alreadyExists = nameString != null;
        findAndSetViews();
        addTextChangedListeners();
        setDefaultValues(restaurantString);
        createFoodPinionButton.setEnabled(false);

        ImageView editIcon = (ImageView) findViewById(R.id.editButtonImageView);
        editIcon.setVisibility(View.INVISIBLE);
        currentFoodPinion = null;
        editOnly = false;

        if (alreadyExists) {
            editIcon.setVisibility(View.VISIBLE);
            currentFoodPinion = User.getFoodPinionByPair(nameString, restaurantString);
            editOnly = true;
            nameEditText.setText(currentFoodPinion.getName());
            commentEditText.setText(currentFoodPinion.getComment());
            ratingRatingBar.setRating(currentFoodPinion.getRating());

            createFoodPinionButton.setText("Edit FoodPinion");
        } else nameEditText.requestFocus();
    }

    private void setDefaultValues(String restaurantString) {
        restaurantEditText.setText(restaurantString);
        ratingRatingBar.setRating(RATING_DEFAULT);
    }

    private void addTextChangedListeners() {
        if (Settings.isRestaurantMandatory())
            restaurantEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isNameMandatory())
            nameEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isCommentMandatory())
            commentEditText.addTextChangedListener(newFoodPinionTextWatcher);
    }

    private void findAndSetViews() {
        restaurantEditText = (EditText) findViewById(R.id.restaurant_newFoodPinion_editText);
        nameEditText = (EditText) findViewById(R.id.name_newFoodPinion_editText);
        commentEditText = (EditText) findViewById(R.id.comment_newFoodPinion_editText);
        ratingRatingBar = (RatingBar) findViewById(R.id.rating_newFoodPinion_ratingBar);
        createFoodPinionButton = (Button) findViewById(R.id.createFoodPinion_newFoodPinion_button);
    }

    public void confirmFoodPinion(View view) {
        FoodPinion foodPinion;
        String addOrEdit = "added";

        String name = nameEditText.getText().toString();
        String restaurant = restaurantEditText.getText().toString();
        String comment = commentEditText.getText().toString();
        float rating = ratingRatingBar.getRating();

        foodPinion = new FoodPinion(name, restaurant, comment, rating);

        if (checkFieldsAreValid()) {
            FoodPinion checkFoodPinion = User.getFoodPinionByPair(name, restaurant);

            if (editOnly) {
                currentFoodPinion.editFoodPinion(name, restaurant, comment, rating);
                addOrEdit = "edited";
                confirmationToast(addOrEdit, name);
                finish();
                return;
            } else if (checkFoodPinion != null) {
                checkFoodPinion.editFoodPinion(name, restaurant, comment, rating);
                addOrEdit = "edited";
            } else {
                User.addFoodPinion(foodPinion);
            }
            confirmationToast(addOrEdit, name);
            clearFoodPinionActivity(addOrEdit, name);
        }
    }

    private void confirmationToast(String addOrEdit, String name) {
        Toast.makeText(
                getApplicationContext(),
                String.format("FoodPinion \"%s\" %s", name, addOrEdit),
                Toast.LENGTH_SHORT).show();
    }

    private void clearFoodPinionActivity(String addOrEdit, String name) {
        clearFields();
        nameEditText.requestFocus();
    }

    void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError("Field is empty");
    }

    private boolean checkFieldsAreValid() {
        boolean returnValue = true;
        if (nameEditText.getText().toString().isEmpty()
                && Settings.isNameMandatory())
            returnValue = false;
        if (restaurantEditText.getText().toString().isEmpty()
                && Settings.isRestaurantMandatory())
            returnValue = false;
        if (commentEditText.getText().toString().isEmpty()
                && Settings.isCommentMandatory())
            returnValue = false;
        return returnValue;
    }

    private void clearFields() {
        nameEditText.getText().clear();
        commentEditText.getText().clear();
    }

    TextWatcher newFoodPinionTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            // Action to perform before text changed
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            // While changing(?)
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Action after changed
            if (checkFieldsAreValid())
                createFoodPinionButton.setEnabled(true);
            else createFoodPinionButton.setEnabled(false);
            if (!editOnly){
                if (User.getFoodPinionByPair(nameEditText.getText().toString(), restaurantEditText.getText().toString()) == null){
                    createFoodPinionButton.setText("Create FoodPinion");
                } else createFoodPinionButton.setText("Edit FoodPinion");
            }
        }
    };
}