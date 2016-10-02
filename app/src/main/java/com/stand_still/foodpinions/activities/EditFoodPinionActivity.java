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
import android.widget.Toast;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.DatabaseHandler;
import com.stand_still.foodpinions.classes.Dish;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.Restaurant;
import com.stand_still.foodpinions.classes.Settings;
import com.stand_still.foodpinions.classes.User;

public class EditFoodPinionActivity extends AppCompatActivity {

    EditText dishNameEditText;
    EditText restaurantEditText;
    EditText commentEditText;
//    RatingBar ratingRatingBar;
    Button createFoodPinionButton;
//    final float RATING_DEFAULT = 2.5f;
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
            dishNameEditText.setText(currentFoodPinion.getDishName());
            commentEditText.setText(currentFoodPinion.getComment());
//            ratingRatingBar.setRating(currentFoodPinion.getRating());

            createFoodPinionButton.setText(getResources().getString(R.string.editFoodPinion_newFoodPinion_button)); // make this local
        } else dishNameEditText.requestFocus();
    }

    private void setDefaultValues(String restaurantString) {
        restaurantEditText.setText(restaurantString);
//        ratingRatingBar.setRating(RATING_DEFAULT);
    }

    private void addTextChangedListeners() {
        if (Settings.isRestaurantMandatory())
            restaurantEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isNameMandatory())
            dishNameEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isCommentMandatory())
            commentEditText.addTextChangedListener(newFoodPinionTextWatcher);
    }

    private void findAndSetViews() {
        restaurantEditText = (EditText) findViewById(R.id.restaurant_newFoodPinion_editText);
        dishNameEditText = (EditText) findViewById(R.id.name_newFoodPinion_editText);
        commentEditText = (EditText) findViewById(R.id.comment_newFoodPinion_editText);
//        ratingRatingBar = (RatingBar) findViewById(R.id.rating_newFoodPinion_ratingBar);
        createFoodPinionButton = (Button) findViewById(R.id.createFoodPinion_newFoodPinion_button);
    }

    public void confirmFoodPinion(View view) {

        String addOrEdit = "added";

        String dishName = dishNameEditText.getText().toString();
        String restaurantName = restaurantEditText.getText().toString();
        String comment = commentEditText.getText().toString();

        Restaurant restaurant = new Restaurant(restaurantName);
        Dish dish = new Dish(dishName, restaurant);
        FoodPinion foodPinion = new FoodPinion(dish, comment);

        if (checkFieldsAreValid()) {
            FoodPinion checkFoodPinion = User.getFoodPinionByPair(dishName, restaurantName);

            // Todo: TESTING!
            DatabaseHandler databaseHandler = new DatabaseHandler(this);
            databaseHandler.addRestaurant(restaurant);
//            Restaurant restaurant0 = databaseHandler.getRestaurant(0);
            Restaurant restaurant1 = databaseHandler.getRestaurant(10);
            // =============

            if (editOnly) {
                currentFoodPinion.editFoodPinion(dishName, restaurantName, comment/*, rating*/);
                addOrEdit = "edited";
                confirmationToast(addOrEdit, dishName);
                finish();
                return;
            } else if (checkFoodPinion != null) {
                checkFoodPinion.editFoodPinion(dishName, restaurantName, comment/*, rating*/);
                addOrEdit = "edited";
            } else {
                User.addFoodPinion(foodPinion);
            }
            confirmationToast(addOrEdit, dishName);
            clearFoodPinionActivity(addOrEdit, dishName);
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
        dishNameEditText.requestFocus();
    }

    void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError("Field is empty");
    }

    private boolean checkFieldsAreValid() {
        boolean returnValue = true;
        if (dishNameEditText.getText().toString().isEmpty()
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
        dishNameEditText.getText().clear();
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
                if (User.getFoodPinionByPair(dishNameEditText.getText().toString(), restaurantEditText.getText().toString()) == null){
                    createFoodPinionButton.setText(getResources().getString(R.string.createFoodPinion_newFoodPinion_button)); //"Create FoodPinion");
                } else createFoodPinionButton.setText(getResources().getString(R.string.editFoodPinion_newFoodPinion_button));
            }
        }
    };
}