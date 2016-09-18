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
import android.widget.ListView;
import android.widget.Toast;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.User;
import com.stand_still.foodpinions.classes.ViewFoodPinionsArrayAdapter;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAME_VALUE = "com.stand_still.foodpinions.NAME_VALUE";

    EditText searchEditText;

    Button newFoodPinionButton;

    ListView foodPinionsListView;

    ViewFoodPinionsArrayAdapter foodPinionsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText) findViewById(R.id.search_editText);

        newFoodPinionButton = (Button) findViewById(R.id.newFoodPinion_button);

        hideButton();

        searchEditText.addTextChangedListener(searchTextWatcher);

        foodPinionsListView = (ListView) findViewById(R.id.foodPinions_list);

        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, User.getFoodPinions());

        foodPinionsListView.setAdapter(foodPinionsArrayAdapter);

        // MAKE THIS RELEVANT
        foodPinionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String itemName = (String) foodPinionsListView.getItemAtPosition(position);

                FoodPinion foodPinion = User.getFoodPinions().get(position);

                String itemRestaurant = foodPinion.getRestaurant();
                float itemRating = foodPinion.getRating();
                Date itemDate = foodPinion.getDate();

//                Outgoing outgoing = BudgetUser.getOutgoings().get(position);
//                int itemCost = outgoing.getCost();
//                int itemFrequency = outgoing.getFrequency();

                Toast.makeText(
                        getApplicationContext(),
                        String.format("Name: %s, Restaurant: %s, Rating: %s, Date: %s",
                                itemName, itemRestaurant, itemRating, itemDate),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        foodPinionsArrayAdapter = new ViewFoodPinionsArrayAdapter(this, User.getFoodPinions());
        foodPinionsListView.setAdapter(foodPinionsArrayAdapter);
    }

    public void newFoodPinion(View view) {
        moveToNewFoodPinion();
    }

    private void moveToNewFoodPinion() {
        Intent intent = new Intent(this, EditFoodPinionActivity.class);
        String searchValue = searchEditText.getText().toString();
        intent.putExtra(EXTRA_NAME_VALUE, searchValue);
        startActivity(intent);
    }

    //    }
//        startActivity(intent);
//        Intent intent = new Intent(this, MainBudgetActivity.class);
//    }
//        startActivity(intent);
//        intent.putExtra(EXTRA_MESSAGE, message);
//        String message = editText.getText().toString();
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//    public void sendMessage(View view) {
//    // In order for this to work: Must be public, must return void, must have View as only parameter

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
            if (editable.length() > 0) {
                showButton();
            } else {
                hideButton();
            }
        }
    };

    private void hideButton() {
        newFoodPinionButton.setEnabled(false);
        newFoodPinionButton.setVisibility(View.INVISIBLE);
    }

    private void showButton() {
        newFoodPinionButton.setEnabled(true);
        newFoodPinionButton.setVisibility(View.VISIBLE);
    }
}