package com.stand_still.foodpinions.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stand_still.foodpinions.R;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_SEARCH_VALUE =
            "com.stand_still.foodpinions.SEARCH_VALUE";

//    public final static String EXTRA_MESSAGE = "com.stand_still.foodpinions.MESSAGE";

    EditText searchEditText;
    Button newFoodPinionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText) findViewById(R.id.search_editText);
//        searchEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS); // Changed this to textVisiblePassword because of SwiftKey issue

        newFoodPinionButton = (Button) findViewById(R.id.newFoodPinion_button);
    }

//    // Called when the user clicks the Send button
//    // In order for this to work: Must be public, must return void, must have View as only parameter
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

//    public void openBudget(View view) {
//        Intent intent = new Intent(this, MainBudgetActivity.class);
//        startActivity(intent);
//    }

    public void newFoodPinion(View view) {
        Intent intent = new Intent(this, NewFoodPinionActivity.class);
        String searchValue = searchEditText.getText().toString();
        intent.putExtra(EXTRA_SEARCH_VALUE, searchValue);
        startActivity(intent);
    }
}