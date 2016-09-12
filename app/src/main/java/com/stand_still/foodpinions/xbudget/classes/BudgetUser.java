package com.stand_still.foodpinions.xbudget.classes;

import com.stand_still.foodpinions.classes.User;

public class BudgetUser extends User {

    static int income;
    static int expenditure;
    static int funds;
    static OutgoingsList outgoings = new OutgoingsList();

    public static void BudgetUser(String name, int income){
        BudgetUser.name = name;
        BudgetUser.income = income;
    }

    public static boolean addOutgoing(Outgoing outgoing){
        return outgoings.add(outgoing);
    }

    public static OutgoingsList getOutgoings(){
        return outgoings;
    }
}