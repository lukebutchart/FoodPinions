package com.stand_still.foodpinions.classes;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Luke on 10/09/2016.
 */
public class OutgoingsList extends ArrayList<Outgoing> {
    ArrayList<String> names;

    public ArrayList<String> getNames() {
        names = new ArrayList<>();
        for (Outgoing outgoing :
                this) {
            names.add(outgoing.getName());
        }
        return names;
    }

    @Override
    public boolean add(Outgoing outgoing) {
        if (!this.contains(outgoing)) {
            return super.add(outgoing);
        } else return false;
    }

    //    @Override
//    public boolean contains(Object outgoing){
    public boolean contains(Outgoing outgoing) {
        if (super.contains(outgoing)) {
            return true;
        } else if(getNames().contains(outgoing.getName())){
            return true;
        } else {
            return false;
        }
    }
}