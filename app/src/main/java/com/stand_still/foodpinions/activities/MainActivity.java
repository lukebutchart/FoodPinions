package com.stand_still.foodpinions.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.Dish;
import com.stand_still.foodpinions.classes.FoodPinion;
import com.stand_still.foodpinions.classes.AppData;
import com.stand_still.foodpinions.classes.FoodPinionArrayList;
import com.stand_still.foodpinions.classes.ListViewAdapter;
import com.stand_still.foodpinions.classes.Restaurant;
//import com.stand_still.foodpinions.classes.ViewFoodPinionsArrayAdapter;
import com.stand_still.foodpinions.exceptions.IncompleteFoodPinionHashMapException;
import com.stand_still.foodpinions.exceptions.MissingListAndHeadersLayoutException;
import com.stand_still.foodpinions.exceptions.MissingSearchAndButtonLayoutException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_VALUE = "com.stand_still.foodpinions.RESTAURANT_VALUE";
    public static final String EXTRA_NAME_VALUE = "com.stand_still.foodpinions.NAME_VALUE";
    public static final String EXTRA_USER_ID_VALUE = "com.stand_still.foodpinions.USER_ID_VALUE";

    //    EditText searchEditText;
    Button newFoodPinionButton;
    Button searchFoodPinionButton;
    ListView foodPinionsListView;
    //    ViewFoodPinionsArrayAdapter foodPinionsArrayAdapter;
    LinearLayout listHeadersLinearLayout;
    AutoCompleteTextView autoCompleteTextView;
    LinearLayout searchAndButton;
    LinearLayout listAndHeaders;
    LinearLayout mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ===== TEST

        if (AppData.getAllFoodPinions(this).size() < 1)
            establishDataBase();

        // ===== TEST END

        final Context context = this;

        // Find views
        listHeadersLinearLayout = (LinearLayout) findViewById(R.id.table_headers);
        newFoodPinionButton = (Button) findViewById(R.id.newFoodPinion_button);
        searchFoodPinionButton = (Button) findViewById(R.id.searchFoodPinion_button);
        foodPinionsListView = (ListView) findViewById(R.id.foodPinions_list);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteSearchFoodPinion);
        searchAndButton = (LinearLayout) findViewById(R.id.search_and_button);
        listAndHeaders = (LinearLayout) findViewById(R.id.list_and_headers);
        mainActivity = (LinearLayout) findViewById(R.id.mainActivity);

        // Collect data
        final FoodPinionArrayList foodPinionArrayList = AppData.getAllFoodPinionsArrayList(this);

        // Modify views
//        searchEditText.addTextChangedListener(searchTextWatcher);
        autoCompleteTextView.addTextChangedListener(searchTextWatcher);
        listAndHeaders.setVisibility(View.INVISIBLE);

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
        decideHeadersVisible();
    }

    void moveSearchToTop() throws MissingSearchAndButtonLayoutException, MissingListAndHeadersLayoutException {
        View child0 = mainActivity.getChildAt(0);
        View child1 = mainActivity.getChildAt(1);

        View searchAndButton = getSearchAndButtonLayout(child0, child1);
        View listAndHeaders = getListAndHeadersLayout(child0, child1);

        if (child0 != searchAndButton) {
            mainActivity.removeAllViews();

            mainActivity.addView(searchAndButton);
            mainActivity.addView(listAndHeaders);
        }
    }

    void moveListToTop() throws MissingSearchAndButtonLayoutException, MissingListAndHeadersLayoutException {
        View child0 = mainActivity.getChildAt(0);
        View child1 = mainActivity.getChildAt(1);

        View searchAndButton = getSearchAndButtonLayout(child0, child1);
        View listAndHeaders = getListAndHeadersLayout(child0, child1);

        mainActivity.removeAllViews();

        mainActivity.addView(listAndHeaders);
        mainActivity.addView(searchAndButton);
    }

    private View getSearchAndButtonLayout(View child0, View child1) throws MissingSearchAndButtonLayoutException {
        if (child0.getId() == R.id.search_and_button)
            return child0;
        else if (child1.getId() == R.id.search_and_button)
            return child1;
        else throw new MissingSearchAndButtonLayoutException();
    }

    private View getListAndHeadersLayout(View child0, View child1) throws MissingListAndHeadersLayoutException {
        if (child0.getId() == R.id.list_and_headers)
            return child0;
        else if (child1.getId() == R.id.list_and_headers)
            return child1;
        else throw new MissingListAndHeadersLayoutException();
    }

    private void setUpAutoCompleteTextView() {
        String[] dishNames = AppData.getAllDishNames(this).toArray(new String[0]);
        String[] restaurantNames = AppData.getAllRestaurantNames(this).toArray(new String[0]);
        String[] autoCompleteArray = concatenate(dishNames, restaurantNames);
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, autoCompleteArray);
        autoCompleteTextView.setAdapter(autoCompleteAdapter);
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
        Intent intent = new Intent(this, EditFoodPinionActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_VALUE, foodPinion.getRestaurantName());
        intent.putExtra(EXTRA_NAME_VALUE, foodPinion.getDishName());
        intent.putExtra(EXTRA_USER_ID_VALUE, foodPinion.getUser().getID());
        startActivity(intent);
    }

    private void decideHeadersVisible() {
        listHeadersLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpFoodPinionList();

        setUpAutoCompleteTextView();

        decideHeadersVisible();
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

    public void newFoodPinion(View view) {
        moveToNewFoodPinion();
    }

    public void searchFoodPinion(View view) {
        String searchText = autoCompleteTextView.getText().toString();
        ArrayList<HashMap<String, String>> newList = AppData.getAllFoodPinionBySearch(searchText, this);
        setUpFoodPinionList(newList);
    }

    private void moveToNewFoodPinion() {
        Intent intent = new Intent(this, EditFoodPinionActivity.class);
//        String searchValue = searchEditText.getText().toString();
        String searchValue = autoCompleteTextView.getText().toString();
        intent.putExtra(EXTRA_RESTAURANT_VALUE, searchValue);
        startActivity(intent);
    }

    private void hideButtons() {
        newFoodPinionButton.setEnabled(false);
        newFoodPinionButton.setVisibility(View.INVISIBLE);
        searchFoodPinionButton.setEnabled(false);
        searchFoodPinionButton.setVisibility(View.INVISIBLE);
    }

    private void showButtons() {
        newFoodPinionButton.setEnabled(true);
        newFoodPinionButton.setVisibility(View.VISIBLE);
        searchFoodPinionButton.setEnabled(true);
        searchFoodPinionButton.setVisibility(View.VISIBLE);
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
            if (editable.length() > 0) {
                showButtons();
                listAndHeaders.setVisibility(View.VISIBLE);
                try {
                    moveSearchToTop();
                } catch (MissingSearchAndButtonLayoutException e) {
                    e.printStackTrace();
                } catch (MissingListAndHeadersLayoutException e) {
                    e.printStackTrace();
                }
            } else hideButtons();
        }
    };
}