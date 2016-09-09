package com.stand_still.foodpinions;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.stand_still.foodpinions.MESSAGE";

//    Button sendButton = (Button) findViewById(R.id.send_button);      // What's the issue here?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Called when the user clicks the Send button
    // In order for this to work: Must be public, must return void, must have View as only parameter
    public void sendMessage(View view) {
//        sendButton.setBackgroundColor(Color.CYAN);
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void testMethod(View view) {
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating((ratingBar.getRating() + 0.5f) % 5);

////        sendButton.setBackgroundColor(Color.CYAN);
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }
}