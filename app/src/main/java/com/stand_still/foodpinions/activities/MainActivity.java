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

        listHeadersLinearLayout = (LinearLayout) findViewById(R.id.list_headers_linearLayout);
        searchEditText = (EditText) findViewById(R.id.search_editText);
        newFoodPinionButton = (Button) findViewById(R.id.newFoodPinion_button);
        searchEditText.addTextChangedListener(searchTextWatcher);
        foodPinionsListView = (ListView) findViewById(R.id.foodPinions_list);
        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, User.getFoodPinions());
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
        intent.putExtra(EXTRA_RESTAURANT_VALUE, foodPinion.getRestaurant());
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
        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, User.getFoodPinions());
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