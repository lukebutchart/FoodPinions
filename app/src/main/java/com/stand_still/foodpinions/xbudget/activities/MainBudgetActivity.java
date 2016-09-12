package com.stand_still.foodpinions.xbudget.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.activities.DisplayMessageActivity;

public class MainBudgetActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.stand_still.foodpinions.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_budget);
    }

    // Called when the user clicks the Send button
    // In order for this to work: Must be public, must return void, must have View as only parameter
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToCreateOutgoing(View view) {
        Intent intent = new Intent(this, CreateOutgoingActivity.class);
        startActivity(intent);
    }

    public void goToViewOutgoings(View view){
        Intent intent = new Intent(this, ViewOutgoingsActivity.class);
        startActivity(intent);
    }
}
