package com.stand_still.foodpinions.classes;

import java.util.ArrayList;

/**
 * Created by Luke on 10/09/2016.
 */
public class User {
    static String name;
    static int income;
    static int expenditure;
    static int funds;
    static OutgoingsList outgoings = new OutgoingsList();

    public static void User(String name, int income){
        User.name = name;
        User.income = income;
    }

    public static void addOutgoing(Outgoing outgoing){
        outgoings.add(outgoing);
    }

    public static OutgoingsList getOutgoings(){
        return outgoings;
    }
}