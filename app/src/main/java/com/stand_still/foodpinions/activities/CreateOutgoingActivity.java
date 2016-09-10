package com.stand_still.foodpinions.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.Outgoing;
import com.stand_still.foodpinions.classes.User;

import java.util.ArrayList;

public class CreateOutgoingActivity extends AppCompatActivity {

    EditText outgoingName;
    EditText outgoingCost;
    EditText outgoingFrequency;

    ArrayList<EditText> editTextFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_outgoing);

        outgoingName = (EditText) findViewById(R.id.name_text);
        outgoingCost = (EditText) findViewById(R.id.cost_text);
        outgoingFrequency = (EditText) findViewById(R.id.frequency_text);

        editTextFields = new ArrayList<>();
        editTextFields.add(outgoingName);
        editTextFields.add(outgoingCost);
        editTextFields.add(outgoingFrequency);
    }

    public void createOutgoing(View view) {
        if (checkFields()) {
            String oName = outgoingName.getText().toString();
            int oCost = Integer.parseInt(outgoingCost.getText().toString());
            int oFrequency = Integer.parseInt(outgoingFrequency.getText().toString());
            User.addOutgoing(new Outgoing(oName, oCost, oFrequency));
            clearFields();
        }
    }

    boolean checkFields() {
        boolean returnValue = true;
        for (EditText editTextField :
                editTextFields) {
            if (editTextField.getText().toString().equals("")) {
                showError(editTextField);
                returnValue = false;
            }
        }
        return returnValue;
    }

    void showError(EditText editText){
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError("Field is empty");
    }

    void clearFields() {
        for (EditText editTextField :
                editTextFields) {
            editTextField.getText().clear();
            editTextField.clearAnimation();
        }
    }
}