package com.stand_still.foodpinions.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.classes.Outgoing;
import com.stand_still.foodpinions.classes.User;

import java.util.ArrayList;

public class ViewOutgoingsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Outgoing> outgoings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outgoings);

        listView = (ListView) findViewById(R.id.outgoings_list);

        ArrayList<String> names = new ArrayList<>();

        outgoings = User.getOutgoings();

        for (Outgoing outgoing :
                outgoings) {
            names.add(outgoing.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, android.R.id.text1, names);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;

                String itemValue = (String) listView.getItemAtPosition(position);

                Toast.makeText(
                        getApplicationContext(),
                        "Position:"+itemPosition+" ListItem:"+itemValue, Toast.LENGTH_LONG).show();
            }
        });



        TextView outgoingsNames = (TextView) findViewById(R.id.outgoings_names);
        TextView outgoingsCosts = (TextView) findViewById(R.id.outgoings_costs);
        TextView outgoingsFrequencies = (TextView) findViewById(R.id.outgoings_frequencies);


        String namesMessage = "";
        String costsMessage = "";
        String frequenciesMessage = "";

        for (Outgoing outgoing :
                outgoings) {
            namesMessage += outgoing.getName() + " ";
            costsMessage += outgoing.getCost() + " ";
            frequenciesMessage += outgoing.getFrequency() + " ";
        }
        
        outgoingsNames.setText(namesMessage);
        outgoingsCosts.setText(costsMessage);
        outgoingsFrequencies.setText(frequenciesMessage);
    }
}