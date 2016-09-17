package com.stand_still.foodpinions.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stand_still.foodpinions.R;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_SEARCH_VALUE = "com.stand_still.foodpinions.SEARCH_VALUE";

    EditText searchEditText;

    Button newFoodPinionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText) findViewById(R.id.search_editText);

        newFoodPinionButton = (Button) findViewById(R.id.newFoodPinion_button);

        newFoodPinionButton.setEnabled(false);

        searchEditText.addTextChangedListener(searchTextWatcher);
    }

    public void newFoodPinion(View view) {
        moveToNewFoodPinion();
    }

    private void moveToNewFoodPinion() {
        Intent intent = new Intent(this, NewFoodPinionActivity.class);
        String searchValue = searchEditText.getText().toString();
        intent.putExtra(EXTRA_SEARCH_VALUE, searchValue);
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
            if (editable.length() > 0)
                newFoodPinionButton.setEnabled(true);
            else newFoodPinionButton.setEnabled(false);
        }
    };
}