package com.stand_still.foodpinions.classes;

import java.util.ArrayList;

/**
 * Created by Luke on 10/09/2016.
 */
public class OutgoingsList extends ArrayList<Outgoing> {
    ArrayList<String> names;

    public ArrayList<String> getNames(){
        names = new ArrayList<>();
        for (Outgoing outgoing :
                this) {
            names.add(outgoing.getName());
        }
        return names;
    }
}
