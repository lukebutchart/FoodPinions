package com.stand_still.foodpinions.xbudget.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.CustomArrayAdapter;
import com.stand_still.foodpinions.xbudget.classes.Outgoing;
import com.stand_still.foodpinions.xbudget.classes.User;

import java.util.ArrayList;

public class ViewOutgoingsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Outgoing> outgoings;

    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outgoings);
        listView = (ListView) findViewById(R.id.outgoings_list);
        names = new ArrayList<>();
        outgoings = User.getOutgoings();

        for (Outgoing outgoing : outgoings)
            names.add(outgoing.getName());

        CustomArrayAdapter customAdapter = new CustomArrayAdapter(
                this, User.getOutgoings()
        );

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String itemValue = (String) listView.getItemAtPosition(position);

                Outgoing outgoing = User.getOutgoings().get(position);
                int itemCost = outgoing.getCost();
                int itemFrequency = outgoing.getFrequency();

                Toast.makeText(
                        getApplicationContext(),
                        String.format("Name: %s, Cost: %s, Frequency: %s",
                                itemValue, itemCost, itemFrequency),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}