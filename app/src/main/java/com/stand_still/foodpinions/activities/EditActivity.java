package com.stand_still.foodpinions.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class EditActivity extends AppCompatActivity {

    EditText dishNameEditText;
    EditText restaurantEditText;
    EditText commentEditText;
    Button createFoodPinionButton;
    FoodPinion passedInFoodPinion;
    boolean editOnly;

    ArrayList<EditText> editTextList = new ArrayList<>();


    static final String ADDED = "added";
    static final String EDITED = "edited";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_pinion);
        // Retrieve intent information
        Intent intent = getIntent();
        String restaurantString = intent.getStringExtra(MainActivity.EXTRA_RESTAURANT_VALUE);
        String dishString = intent.getStringExtra(MainActivity.EXTRA_DISH_VALUE);
        boolean passingIn = intent.getExtras().getBoolean(MainActivity.EXTRA_EDITING_BOOLEAN);
        int userID = intent.getIntExtra(MainActivity.EXTRA_USER_ID_VALUE, 0);

        findAndSetViews();
        addTextChangedListeners();
        setDefaultValues(restaurantString, dishString);
        createFoodPinionButton.setEnabled(false);

        ImageView editIcon = (ImageView) findViewById(R.id.editButtonImageView);
        editIcon.setVisibility(View.INVISIBLE);
        passedInFoodPinion = null;
        editOnly = false;

        try {
            if (passingIn) {
                editIcon.setVisibility(View.VISIBLE);
                Restaurant restaurant = AppData.getRestaurant(restaurantString, this);
                Dish dish = AppData.getDish(dishString, restaurant, this);
                if (userID < 1)
                    throw new NoUserIDPassedToEditException();
                User user = AppData.getUser(userID, this);
                passedInFoodPinion = AppData.getFoodPinion(dish, user, this);
                editOnly = true;
                dishNameEditText.setText(passedInFoodPinion.getDish().getName());
                commentEditText.setText(passedInFoodPinion.getComment());

                createFoodPinionButton.setText(getResources().getString(R.string.editFoodPinion)); // make this local
            }
        } catch (NoUserIDPassedToEditException e) {
            // Hopefully won't happen...
            Toast.makeText(
                    getApplicationContext(),
                    "NoUserIDPassedToEditException",
                    Toast.LENGTH_SHORT).show();
        }

        decideFocus();

        // Force keyboard to show
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // Set 'enter' event
        commentEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    confirmFoodPinion();
                    return true;
                }
                return false;
            }
        });
    }

    private void decideFocus() {
        if (restaurantEditText.getText().toString().trim().length() == 0)
            restaurantEditText.requestFocus();
        else if (dishNameEditText.getText().toString().trim().length() == 0)
            dishNameEditText.requestFocus();
        else if (commentEditText.getText().toString().trim().length() == 0)
            commentEditText.requestFocus();
        else restaurantEditText.requestFocus();
    }

    private void setDefaultValues(String restaurantString, String dishString) {
        restaurantEditText.setText(restaurantString);
        dishNameEditText.setText(dishString);
    }

    private void addTextChangedListeners() {
        if (Settings.isRestaurantMandatory())
            restaurantEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isDishMandatory())
            dishNameEditText.addTextChangedListener(newFoodPinionTextWatcher);
        if (Settings.isCommentMandatory())
            commentEditText.addTextChangedListener(newFoodPinionTextWatcher);
    }

    private void findAndSetViews() {
        restaurantEditText = (EditText) findViewById(R.id.restaurant_editText);
        dishNameEditText = (EditText) findViewById(R.id.dish_editText);
        commentEditText = (EditText) findViewById(R.id.comment_editText);
        createFoodPinionButton = (Button) findViewById(R.id.createFoodPinion_button);

//        ArrayList<EditText> editTextList = new ArrayList<>();
        if (editTextList.size() == 0) {
            editTextList.add(restaurantEditText);
            editTextList.add(dishNameEditText);
            editTextList.add(commentEditText);
        }
    }

    public void confirmFoodPinion(View view) {  // Todo: Run when pressing enter
        confirmFoodPinion();
    }

    private void confirmFoodPinion() {
        String addOrEditString;
        String dishName = dishNameEditText.getText().toString();
        String restaurantName = restaurantEditText.getText().toString();
        String comment = commentEditText.getText().toString();

        Restaurant restaurant = new Restaurant(restaurantName);
        Dish dish = new Dish(dishName, restaurant);
//        FoodPinion foodPinion = new FoodPinion(dish, comment, this);

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
                int newDishID = newFoodPinion.getDish().getID();
                boolean equalDishIDs = passedInDishID == newDishID;

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
            confirmationToast(addOrEditString, dishName);
            if (editOnly)
                finish();
            else clearFoodPinionActivity();
        } else {
            afterFieldsNotValid();
        }
    }

    private void afterFieldsNotValid() {
        EditText topInError = null;

//        for (EditText editText :   //Todo: Sort this with ExtEditText
//                editTextList) {
//            if (editText.getText().toString().trim().length() == 0
//                    && Settings.isDishMandatory()) {
//                showError(dishNameEditText);
//                topInError = dishNameEditText;
//            }
//        }

//        dishNameEditText.is

        if (dishNameEditText.getText().toString().trim().length() == 0
                && Settings.isDishMandatory()) {
            showError(dishNameEditText);
            topInError = dishNameEditText;
        }
        if (restaurantEditText.getText().toString().trim().length() == 0
                && Settings.isRestaurantMandatory()) {
            showError(restaurantEditText);
            topInError = restaurantEditText;
        }
        if (commentEditText.getText().toString().trim().length() == 0
                && Settings.isCommentMandatory()) {
            showError(commentEditText);
            topInError = commentEditText;
        }

//        if (topInError != null)
//            topInError.requestFocus();
    }

    private void confirmationToast(String addOrEdit, String name) {
        Toast.makeText(
                getApplicationContext(),
                String.format("FoodPinion \"%s\" %s", name, addOrEdit),
                Toast.LENGTH_SHORT).show();
    }

    private void clearFoodPinionActivity() {
        clearFields();
        decideFocus();
    }

    void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError("Field is empty");
    }

    private boolean checkFieldsAreValid() {
        boolean returnValue = true;
        if (dishNameEditText.getText().toString().trim().length() == 0
                && Settings.isDishMandatory())
            returnValue = false;
        if (restaurantEditText.getText().toString().trim().length() == 0
                && Settings.isRestaurantMandatory())
            returnValue = false;
        if (commentEditText.getText().toString().trim().length() == 0
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
                    createFoodPinionButton.setText(getResources().getString(R.string.createFoodPinion));
                } else
                    createFoodPinionButton.setText(getResources().getString(R.string.editFoodPinion));
            }
        }
    };
}