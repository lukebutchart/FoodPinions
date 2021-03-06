package com.stand_still.foodpinions.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.AppData;
import com.stand_still.foodpinions.classes.Dish;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.FoodPinionArrayList;
import com.stand_still.foodpinions.classes.ListViewAdapter;
import com.stand_still.foodpinions.classes.Restaurant;
import com.stand_still.foodpinions.exceptions.IncompleteFoodPinionHashMapException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_VALUE = "com.stand_still.foodpinions.RESTAURANT_VALUE";
    public static final String EXTRA_EDITING_BOOLEAN = "com.stand_still.foodpinions.EDITING_BOOLEAN";
    public static final String EXTRA_DISH_VALUE = "com.stand_still.foodpinions.DISH_VALUE";
    public static final String EXTRA_USER_ID_VALUE = "com.stand_still.foodpinions.USER_ID_VALUE";

    Button newFoodPinionByRestaurantButton;
    Button newFoodPinionByDishButton;
    ListView foodPinionsListView;
    AutoCompleteTextView searchTextView;
    LinearLayout searchAndButton;
    LinearLayout listAndHeaders;

    LinearLayout mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Establish Database
//        if (AppData.getAllFoodPinions(this).size() < 1)
//            establishDataBase();

        final Context context = this;

        // Find views
        newFoodPinionByRestaurantButton = (Button) findViewById(R.id.new_restaurant_button);
        newFoodPinionByDishButton = (Button) findViewById(R.id.new_dish_button);
        foodPinionsListView = (ListView) findViewById(R.id.foodPinions_list);
        searchTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteSearchFoodPinion);
        searchAndButton = (LinearLayout) findViewById(R.id.search_and_button);
        listAndHeaders = (LinearLayout) findViewById(R.id.list_and_headers);
        mainActivity = (LinearLayout) findViewById(R.id.mainActivity);


        // Collect data
        final FoodPinionArrayList foodPinionArrayList = AppData.getAllFoodPinionsArrayList(this);

        // Modify views
        searchTextView.addTextChangedListener(searchTextWatcher);
        searchTextView.requestFocus();
        listAndHeaders.setVisibility(View.INVISIBLE);

        // Set 'enter' event
        searchTextView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    moveToNewFoodPinion(searchTextView.getText().toString(), null);
                    return true;
                }
                return false;
            }
        });

        // TODO: Look up Android Styles & Themes tutorial.

        // Set up Most Recent list
        setUpFoodPinionList();

        // Set onClick
        foodPinionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // Todo: Make sure checks are done for this
                HashMap<String, String> foodPinionHashMap = (HashMap<String, String>) foodPinionsListView.getItemAtPosition(position);

                try {
                    FoodPinion listFoodPinion = AppData.getFoodPinion(foodPinionHashMap, context);
                    moveToEditFoodPinion(listFoodPinion);
                } catch (IncompleteFoodPinionHashMapException e) {
                    e.printStackTrace();
                }
            }

        });

        // Set up autoCompleteTextView
        setUpAutoCompleteTextView();

        hideButtons();

        // Force keyboard to show
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onContentChanged(){
        super.onContentChanged();

        View empty = findViewById(R.id.empty);
        foodPinionsListView = (ListView) findViewById(R.id.foodPinions_list);
        foodPinionsListView.setEmptyView(empty);
    }

    private void setUpAutoCompleteTextView() {
        String[] dishNames = AppData.getAllDishNames(this).toArray(new String[0]);
        String[] restaurantNames = AppData.getAllRestaurantNames(this).toArray(new String[0]);
        String[] autoCompleteArray = concatenate(dishNames, restaurantNames);
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, autoCompleteArray);
        searchTextView.setAdapter(autoCompleteAdapter);
    }

    public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    private void establishDataBase() {
        Restaurant restaurant = new Restaurant("Nandos");
        Dish dish = new Dish("Chips", restaurant);
        FoodPinion foodPinion = new FoodPinion(dish, "com", this);
        AppData.addFoodPinion(foodPinion, this);

        Restaurant restaurant1 = new Restaurant("GBK");
        Dish dish1 = new Dish("Burger", restaurant1);
        FoodPinion foodPinion1 = new FoodPinion(dish1, "com1", this);
        AppData.addFoodPinion(foodPinion1, this);

        Restaurant restaurant2 = new Restaurant("McDonalds");
        Dish dish2 = new Dish("Big Mac", restaurant2);
        FoodPinion foodPinion2 = new FoodPinion(dish2, "com2", this);
        AppData.addFoodPinion(foodPinion2, this);
    }

    private void moveToEditFoodPinion(FoodPinion foodPinion) {  // Todo: Fix so that changing the non-comment fields actually updates
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_VALUE, foodPinion.getRestaurantName());
        intent.putExtra(EXTRA_DISH_VALUE, foodPinion.getDishName());
        intent.putExtra(EXTRA_EDITING_BOOLEAN, true);
        intent.putExtra(EXTRA_USER_ID_VALUE, foodPinion.getUser().getID());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpFoodPinionList();

        setUpAutoCompleteTextView();
    }

    private void setUpFoodPinionList() {
        ArrayList<HashMap<String, String>> foodPinionsHashMapList = AppData.getAllFoodPinionsHashMapList(this);
        ListViewAdapter adapter = new ListViewAdapter(this, foodPinionsHashMapList);
        foodPinionsListView.setAdapter(adapter);
    }

    private void setUpFoodPinionList(ArrayList<HashMap<String, String>> newList) {
        ListViewAdapter adapter = new ListViewAdapter(this, newList);
        foodPinionsListView.setAdapter(adapter);
    }

    public void newFoodPinionByRestaurant(View view) {
        moveToNewFoodPinion(searchTextView.getText().toString(), null);
    }

    public void newFoodPinionByDish(View view) {
        moveToNewFoodPinion(null, searchTextView.getText().toString());
    }

    public void searchFoodPinion() {
        String searchText = searchTextView.getText().toString();
        ArrayList<HashMap<String, String>> newList = AppData.getAllFoodPinionBySearch(searchText, this);
        setUpFoodPinionList(newList);
    }

    public void searchFoodPinion(View view) {
        searchFoodPinion();
    }

    private void moveToNewFoodPinion(String restaurantName, String dishName) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_VALUE, restaurantName);
        intent.putExtra(EXTRA_DISH_VALUE, dishName);
        startActivity(intent);
    }

    private void hideButtons() {
        newFoodPinionByRestaurantButton.setEnabled(false);
        newFoodPinionByRestaurantButton.setVisibility(View.INVISIBLE);
        newFoodPinionByDishButton.setEnabled(false);
        newFoodPinionByDishButton.setVisibility(View.INVISIBLE);
    }

    private void showButtons() {
        newFoodPinionByRestaurantButton.setEnabled(true);
        newFoodPinionByRestaurantButton.setVisibility(View.VISIBLE);
        newFoodPinionByDishButton.setEnabled(true);
        newFoodPinionByDishButton.setVisibility(View.VISIBLE);
    }

    TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            // Action to perform before text changed
//            Toast.makeText(getApplicationContext(), "beforeTextChanged", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            // While changing(?)
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                showButtons();
                listAndHeaders.setVisibility(View.VISIBLE);
                searchFoodPinion();
            } else {
                hideButtons();
                listAndHeaders.setVisibility(View.INVISIBLE);
            }
        }
    };
}