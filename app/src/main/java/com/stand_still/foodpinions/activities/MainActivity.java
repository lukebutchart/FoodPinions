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
import android.widget.Toast;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.Dish;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.AppData;
import com.stand_still.foodpinions.classes.FoodPinionArrayList;
import com.stand_still.foodpinions.classes.ListViewAdapter;
import com.stand_still.foodpinions.classes.Restaurant;
import com.stand_still.foodpinions.classes.User;
import com.stand_still.foodpinions.classes.ViewFoodPinionsArrayAdapter;

import static com.stand_still.foodpinions.classes.Constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_VALUE = "com.stand_still.foodpinions.RESTAURANT_VALUE";
    public static final String EXTRA_NAME_VALUE = "com.stand_still.foodpinions.NAME_VALUE";
    public static final String EXTRA_USER_ID_VALUE = "com.stand_still.foodpinions.USER_ID_VALUE";

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

        // ===== TEST END

        // Find views
        listHeadersLinearLayout = (LinearLayout) findViewById(R.id.list_headers_linearLayout);
        searchEditText = (EditText) findViewById(R.id.search_editText);
        newFoodPinionButton = (Button) findViewById(R.id.newFoodPinion_button);
        foodPinionsListView = (ListView) findViewById(R.id.foodPinions_list);

        // Collect data
        final FoodPinionArrayList foodPinionArrayList = AppData.getAllFoodPinionsArrayList(this);

        // Modify views
        searchEditText.addTextChangedListener(searchTextWatcher);
//        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, foodPinionArrayList);
//        foodPinionsListView.setAdapter(foodPinionsArrayAdapter);


//        ArrayList<HashMap<String, String>> list = new ArrayList<>();
//        HashMap<String, String> temp = new HashMap<>();
//        temp.put(FIRST_COLUMN, "Ankit Karia");
//        temp.put(SECOND_COLUMN, "Male");
//        temp.put(THIRD_COLUMN, "22");
//        temp.put(FOURTH_COLUMN, "Unmarried");
//        list.add(temp);
//        HashMap<String, String> temp2 = new HashMap<>();
//        temp2.put(FIRST_COLUMN, "Rajat Ghai");
//        temp2.put(SECOND_COLUMN, "Male");
//        temp2.put(THIRD_COLUMN, "25");
//        temp2.put(FOURTH_COLUMN, "Unmarried");
//        list.add(temp2);
//        HashMap<String, String> temp3 = new HashMap<>();
//        temp3.put(FIRST_COLUMN, "Karina Kaif");
//        temp3.put(SECOND_COLUMN, "Female");
//        temp3.put(THIRD_COLUMN, "31");
//        temp3.put(FOURTH_COLUMN, "Unmarried");
//        list.add(temp3);

        ArrayList<HashMap<String, String>> list =  AppData.getAllFoodPinionsHashMapList(this);

        ListViewAdapter adapter = new ListViewAdapter(this, list);
        foodPinionsListView.setAdapter(adapter);
        foodPinionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

//                FoodPinion listFoodPinion =

                // Todo: Make sure checks are done for this
                HashMap<String, String> foodPinionHashMap = (HashMap<String, String>) foodPinionsListView.getItemAtPosition(position);

                FoodPinion listFoodPinion = AppData.getFoodPinionFromHashMap(foodPinionHashMap);

//                int pos = position + 1;
//                Toast.makeText(MainActivity.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
            }

        });


        hideButton();
        decideHeadersVisible();

//        foodPinionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String itemName = (String) foodPinionsListView.getItemAtPosition(position); // Todo: get this to return both values, so that the FoodPinion can be found in EditFoodPinionActivity
//                FoodPinion foodPinion = foodPinionArrayList.get(position);
//
//                moveToEditFoodPinion(foodPinion);
//            }
//        });
    }

    private void moveToEditFoodPinion(FoodPinion foodPinion) {  // Todo: Fix so that changing the non-comment fields actually updates
        Intent intent = new Intent(this, EditFoodPinionActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_VALUE, foodPinion.getRestaurantName());
        intent.putExtra(EXTRA_NAME_VALUE, foodPinion.getDishName());
        intent.putExtra(EXTRA_USER_ID_VALUE, foodPinion.getUser().getID());
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

//        FoodPinionArrayList foodPinionArrayList = AppData.getAllFoodPinionsArrayList(this);

//        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, foodPinionArrayList);
//        foodPinionsListView.setAdapter(foodPinionsArrayAdapter);

        ArrayList<HashMap<String, String>> foodPinionsHashMapList =  AppData.getAllFoodPinionsHashMapList(this);

        ListViewAdapter adapter = new ListViewAdapter(this, foodPinionsHashMapList);
        foodPinionsListView.setAdapter(adapter);

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