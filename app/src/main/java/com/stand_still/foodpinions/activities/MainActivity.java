package com.stand_still.foodpinions.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.Dish;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.AppData;
import com.stand_still.foodpinions.classes.FoodPinionArrayList;
import com.stand_still.foodpinions.classes.Restaurant;
import com.stand_still.foodpinions.classes.User;
import com.stand_still.foodpinions.classes.ViewFoodPinionsArrayAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_VALUE = "com.stand_still.foodpinions.RESTAURANT_VALUE";
    public static final String EXTRA_NAME_VALUE = "com.stand_still.foodpinions.NAME_VALUE";

    EditText searchEditText;
    Button newFoodPinionButton;
    ListView foodPinionsListView;
    ViewFoodPinionsArrayAdapter foodPinionsArrayAdapter;
    LinearLayout listHeadersLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ===== TEST

//        List<FoodPinion> foodPinions = AppData.getAllFoodPinions(this);

        Restaurant restaurant = new Restaurant("Nandos");
        Dish dish = new Dish("Chips", restaurant);
        FoodPinion foodPinion = new FoodPinion(dish, "com", this);
        AppData.addFoodPinion(foodPinion, this);

//        List<FoodPinion> foodPinions1 = AppData.getAllFoodPinions(this);

        Restaurant restaurant1 = new Restaurant("GBK");
        Dish dish1 = new Dish("Burger", restaurant1);
        FoodPinion foodPinion1 = new FoodPinion(dish1, "com1", this);
        AppData.addFoodPinion(foodPinion1, this);

//        List<FoodPinion> foodPinions2 = AppData.getAllFoodPinions(this);

        Restaurant restaurant2 = new Restaurant("McDonalds");   // Todo: Test what happens when the restaurant and dish are the same
        Dish dish2 = new Dish("Big Mac", restaurant2);
        FoodPinion foodPinion2 = new FoodPinion(dish2, "com2", this);
        AppData.addFoodPinion(foodPinion2, this);

//        List<FoodPinion> foodPinions3 = AppData.getAllFoodPinions(this);

        // ===== TEST END

        // Find views
        listHeadersLinearLayout = (LinearLayout) findViewById(R.id.list_headers_linearLayout);
        searchEditText = (EditText) findViewById(R.id.search_editText);
        newFoodPinionButton = (Button) findViewById(R.id.newFoodPinion_button);
        foodPinionsListView = (ListView) findViewById(R.id.foodPinions_list);

        // Collect data
//        List<FoodPinion> foodPinionList = AppData.getAllFoodPinions(this);
        FoodPinionArrayList foodPinionArrayList = AppData.getAllFoodPinionsArrayList(this); // Todo: Check why the list doesn't appear in the app

        FoodPinionArrayList foodPinionArrayList1 = new FoodPinionArrayList();
        foodPinionArrayList1.add(foodPinion1);

        // Modify views
        searchEditText.addTextChangedListener(searchTextWatcher);
        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, foodPinionArrayList);
        foodPinionsListView.setAdapter(foodPinionsArrayAdapter);

        hideButton();
        decideHeadersVisible();

        foodPinionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String itemName = (String) foodPinionsListView.getItemAtPosition(position);
                FoodPinion foodPinion = User.getFoodPinions().get(position);

                moveToEditFoodPinion(foodPinion);
            }
        });
    }

    private void moveToEditFoodPinion(FoodPinion foodPinion) {
        Intent intent = new Intent(this, EditFoodPinionActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_VALUE, foodPinion.getRestaurantName());
        intent.putExtra(EXTRA_NAME_VALUE, foodPinion.getDishName());
        startActivity(intent);
    }

    private void decideHeadersVisible() {
        if (User.getFoodPinions().isEmpty())
            listHeadersLinearLayout.setVisibility(View.INVISIBLE);
        else listHeadersLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // =================== TEST

//        Restaurant restaurant1 = new Restaurant("GBK");
//        Dish dish1 = new Dish("Burger", restaurant1);
//        FoodPinion foodPinion1 = new FoodPinion(dish1, "com1", this);
//        AppData.addFoodPinion(foodPinion1, this);
//
//        FoodPinionArrayList foodPinionArrayList1 = new FoodPinionArrayList();
//        foodPinionArrayList1.add(foodPinion1);

        FoodPinionArrayList foodPinionArrayList = AppData.getAllFoodPinionsArrayList(this);

        // ===================  END TEST

        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, foodPinionArrayList);
        foodPinionsListView.setAdapter(foodPinionsArrayAdapter);
        decideHeadersVisible();
    }

    public void newFoodPinion(View view) {
        moveToNewFoodPinion();
    }

    private void moveToNewFoodPinion() {
        Intent intent = new Intent(this, EditFoodPinionActivity.class);
        String searchValue = searchEditText.getText().toString();
        intent.putExtra(EXTRA_RESTAURANT_VALUE, searchValue);
        startActivity(intent);
    }

    private void hideButton() {
        newFoodPinionButton.setEnabled(false);
        newFoodPinionButton.setVisibility(View.INVISIBLE);
    }

    private void showButton() {
        newFoodPinionButton.setEnabled(true);
        newFoodPinionButton.setVisibility(View.VISIBLE);
    }

    TextWatcher searchTextWatcher = new TextWatcher() {
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
            if (editable.length() > 0)
                showButton();
            else hideButton();
        }
    };
}