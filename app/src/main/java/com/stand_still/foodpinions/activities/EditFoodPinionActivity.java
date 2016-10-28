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
import com.stand_still.foodpinions.classes.AppData;
import com.stand_still.foodpinions.classes.Dish;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.Restaurant;
import com.stand_still.foodpinions.classes.Settings;
import com.stand_still.foodpinions.classes.User;
import com.stand_still.foodpinions.exceptions.NoUserIDPassedToEditException;

import java.util.List;

public class EditFoodPinionActivity extends AppCompatActivity {

    EditText dishNameEditText;
    EditText restaurantEditText;
    EditText commentEditText;
    Button createFoodPinionButton;
    FoodPinion passedInFoodPinion;
    boolean editOnly;

    static final String ADDED = "added";
    static final String EDITED = "edited";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_pinion);
        // Retrieve intent information
        Intent intent = getIntent();
        String restaurantString = intent.getStringExtra(MainActivity.EXTRA_RESTAURANT_VALUE);
        String nameString = intent.getStringExtra(MainActivity.EXTRA_NAME_VALUE);
        int userID = intent.getIntExtra(MainActivity.EXTRA_USER_ID_VALUE, 0);

        boolean alreadyExists = nameString != null;
        findAndSetViews();
        addTextChangedListeners();
        setDefaultValues(restaurantString);
        createFoodPinionButton.setEnabled(false);

        ImageView editIcon = (ImageView) findViewById(R.id.editButtonImageView);
        editIcon.setVisibility(View.INVISIBLE);
        passedInFoodPinion = null;
        editOnly = false;

        try {
            if (alreadyExists) {
                editIcon.setVisibility(View.VISIBLE);
                Restaurant restaurant = AppData.getRestaurant(restaurantString, this);
                Dish dish = AppData.getDish(nameString, restaurant, this);
                if (userID < 1)
                    throw new NoUserIDPassedToEditException();
                User user = AppData.getUser(userID, this);
                passedInFoodPinion = AppData.getFoodPinion(dish, user, this);
                editOnly = true;
                dishNameEditText.setText(passedInFoodPinion.getDish().getName());
                commentEditText.setText(passedInFoodPinion.getComment());

                createFoodPinionButton.setText(getResources().getString(R.string.editFoodPinion_newFoodPinion_button)); // make this local
            } else dishNameEditText.requestFocus();
        } catch (NoUserIDPassedToEditException e) {
            // Hopefully won't happen...
            Toast.makeText(
                    getApplicationContext(),
                    String.format("NoUserIDPassedToEditException"),
                    Toast.LENGTH_SHORT).show();
        }
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
        createFoodPinionButton = (Button) findViewById(R.id.createFoodPinion_newFoodPinion_button);
    }

    public void confirmFoodPinion(View view) {

        String addOrEditString = ADDED;

        String dishName = dishNameEditText.getText().toString();
        String restaurantName = restaurantEditText.getText().toString();
        String comment = commentEditText.getText().toString();

        Restaurant restaurant = new Restaurant(restaurantName);
        Dish dish = new Dish(dishName, restaurant);
        FoodPinion foodPinion = new FoodPinion(dish, comment, this);

        if (checkFieldsAreValid()) {
            User user = Settings.getUser(this);
            // Just change the FoodPinion
            Restaurant newRestaurant = AppData.getRestaurant(restaurantName, this);

            if (newRestaurant == null) {
                newRestaurant = new Restaurant(restaurantName);
                AppData.addRestaurant(newRestaurant, this);
            }

            Dish newDish = AppData.getDish(dishName, restaurant, this);

            if (newDish == null) {
                newDish = new Dish(dishName, restaurant);
                AppData.addDish(newDish, this);
            }

            FoodPinion newFoodPinion = AppData.getFoodPinion(dish, user, this);

            if (newFoodPinion == null) {
                newFoodPinion = new FoodPinion(dish, comment, this);
                AppData.addFoodPinion(newFoodPinion, this);

                addOrEditString = ADDED;
            } else {
                newFoodPinion.setComment(comment);
                AppData.updateFoodPinion(newFoodPinion, this);
                addOrEditString = EDITED;
            }


            if (passedInFoodPinion != null) { // Then MAY need to delete it from the DB
                // First: Check whether it may be obsolete
                int passedInRestaurantID = passedInFoodPinion.getDish().getRestaurant().getID();
                int passedInDishID = passedInFoodPinion.getDish().getID();
                int passedInUserID = passedInFoodPinion.getUser().getID();

                int newRestaurantID = newFoodPinion.getDish().getRestaurant().getID();
                int newDishID = newFoodPinion.getDish().getID();
                int newUserID = newFoodPinion.getUser().getID();

                boolean equalRestaurantIDs = passedInRestaurantID == newRestaurantID;
                boolean equalDishIDs = passedInDishID == newDishID;
                boolean equalUserIDs = passedInUserID == newUserID;

                if (!equalDishIDs) { // MAY be obsolete
                    // Second: Check whether dish in use elsewhere
                    if (AppData.getAllFoodPinionsWithDish(passedInDishID, this).size() == 1) {
                        AppData.deleteFoodPinion(passedInFoodPinion, this);
                        AppData.deleteDish(passedInFoodPinion.getDish(), this);
                        if (AppData.getAllDishesWithRestaurant(passedInRestaurantID, this).size() == 0) {
                            AppData.deleteRestaurant(passedInFoodPinion.getDish().getRestaurant(), this);
                        }
                    }
                }
            }

            List<FoodPinion> fl = AppData.getAllFoodPinions(this);
            List<Restaurant> rl = AppData.getAllRestaurants(this);
            List<Dish> dl = AppData.getAllDishes(this);

//                passedInFoodPinion.editFoodPinion(dishName, restaurantName, comment);
//                AppData.updateFoodPinion(passedInFoodPinion, this);
            confirmationToast(addOrEditString, dishName);
            finish();
            return;
//            } else if (checkFoodPinion != null) {
//                checkFoodPinion.editFoodPinion(dishName, restaurantName, comment);
//                AppData.updateFoodPinion(checkFoodPinion, this);
//                addOrEditString = "edited";
//            } else {
//                AppData.addFoodPinion(foodPinion, this); //User.addFoodPinion(foodPinion);
//            }
//            confirmationToast(addOrEditString, dishName);
//            clearFoodPinionActivity(addOrEditString, dishName); Todo: Bring this back
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
            if (!editOnly) {
                if (User.getFoodPinionByPair(dishNameEditText.getText().toString(), restaurantEditText.getText().toString()) == null) {
                    createFoodPinionButton.setText(getResources().getString(R.string.createFoodPinion_newFoodPinion_button)); //"Create FoodPinion");
                } else
                    createFoodPinionButton.setText(getResources().getString(R.string.editFoodPinion_newFoodPinion_button));
            }
        }
    };
}