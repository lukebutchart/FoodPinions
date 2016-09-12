package com.stand_still.foodpinions.xbudget.classes;

import java.util.ArrayList;

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

    public boolean contains(Outgoing outgoing) {
        if (super.contains(outgoing)) {
            return true;
        } else return getNames().contains(outgoing.getName());
    }
}