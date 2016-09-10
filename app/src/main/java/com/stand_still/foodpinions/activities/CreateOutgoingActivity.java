package com.stand_still.foodpinions.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.Outgoing;
import com.stand_still.foodpinions.classes.User;

public class CreateOutgoingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_outgoing);
    }

    public void createOutgoing(View view) {
        EditText outgoingName = (EditText) findViewById(R.id.name_text);
        EditText outgoingCost = (EditText) findViewById(R.id.cost_text);
        EditText outgoingFrequency = (EditText) findViewById(R.id.frequency_text);

        String oName = outgoingName.getText().toString();
        int oCost = Integer.parseInt(outgoingCost.getText().toString());
        int oFrequency = Integer.parseInt(outgoingFrequency.getText().toString());

        Outgoing outgoing = new Outgoing(oName, oCost, oFrequency);

        User.addOutgoing(outgoing);
    }
}
