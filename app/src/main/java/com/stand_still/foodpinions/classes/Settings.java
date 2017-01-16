package com.stand_still.foodpinions.classes;

import android.content.Context;

public class Settings {
    static boolean NAME_IS_MANDATORY = true;
    static boolean RESTAURANT_IS_MANDATORY = true;
    static boolean COMMENT_IS_MANDATORY = false;
    static boolean RATING_IS_MANDATORY = true;

    static String USER_NAME = "Luke";

    static User user;

    public static User getUser(Context context) {
        if (user == null) {
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            databaseHandler.addUser(new User(USER_NAME));
            user = databaseHandler.getUserByName(USER_NAME);
        }
        return user;
    }

    public static boolean isCommentMandatory() {
        return COMMENT_IS_MANDATORY;
    }

    public static boolean isRestaurantMandatory() {
        return RESTAURANT_IS_MANDATORY;
    }

    public static boolean isDishMandatory() {
        return NAME_IS_MANDATORY;
    }

    public static String getUserName() {
        return USER_NAME;
    }
}