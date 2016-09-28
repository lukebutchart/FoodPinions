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
    String name;
    String restaurant;
    String comment;
    float rating;
    Button createFoodPinionButton;
    ArrayList<EditText> editTextFields = new ArrayList<>();
    final float RATING_DEFAULT = 2.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_pinion);

        Intent intent = getIntent();
        String restaurantString = intent.getStringExtra(MainActivity.EXTRA_RESTAURANT_VALUE);
        String nameString = intent.getStringExtra(MainActivity.EXTRA_NAME_VALUE);

        boolean alreadyExists = nameString != null;
        // Find and set views
        restaurantEditText = (EditText) findViewById(R.id.restaurant_newFoodPinion_editText);
        nameEditText = (EditText) findViewById(R.id.name_newFoodPinion_editText);
        commentEditText = (EditText) findViewById(R.id.comment_newFoodPinion_editText);
        ratingRatingBar = (RatingBar) findViewById(R.id.rating_newFoodPinion_ratingBar);
        createFoodPinionButton = (Button) findViewById(R.id.createFoodPinion_newFoodPinion_button);
        // Add textChangedListeners
        if (Settings.isRestaurantMandatory())
            restaurantEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isNameMandatory())
            nameEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isCommentMandatory())
            commentEditText.addTextChangedListener(newFoodPinionTextWatcher);
        // Set default values
        restaurantEditText.setText(restaurantString);
        ratingRatingBar.setRating(RATING_DEFAULT);
        // Disable the create button
        createFoodPinionButton.setEnabled(false);
        // Add EditTexts to collection
        editTextFields.add(nameEditText);
        editTextFields.add(restaurantEditText);
        editTextFields.add(commentEditText);

        if (alreadyExists) {
            FoodPinion foodPinion = User.getFoodPinionByPair(nameString, restaurantString);
            nameEditText.setText(foodPinion.getName());
            commentEditText.setText(foodPinion.getComment());
            ratingRatingBar.setRating(foodPinion.getRating());
        } else nameEditText.requestFocus();
    }

    public void createFoodPinion(View view) {
        FoodPinion foodPinion;

        name = nameEditText.getText().toString();
        restaurant = restaurantEditText.getText().toString();
        comment = commentEditText.getText().toString();
        rating = ratingRatingBar.getRating();

        foodPinion = new FoodPinion(name, restaurant, comment, rating);

        if (User.foodPinionExists(foodPinion)) {
            Toast.makeText(
                    getApplicationContext(),
                    String.format("A FoodPinion with those details already exists"),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkFieldsAreValid()) {
            clearFields();
            Toast.makeText(
                    getApplicationContext(),
                    String.format("FoodPinion \"%s\" added", name),
                    Toast.LENGTH_SHORT).show();
            nameEditText.requestFocus();
            User.addFoodPinion(foodPinion);
        }
    }

    void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError("Field is empty");
    }

//    private boolean checkFieldsAreValidOnPress() {
//        boolean returnValue = true;
//        for (EditText editTextField : editTextFields)
//            if (editTextField.getText().toString().isEmpty()) {
//                showError(editTextField);
//                returnValue = false;
//            }
//        return returnValue;
//    }

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
        }
    };
}
