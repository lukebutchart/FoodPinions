package com.stand_still.foodpinions.classes;

public class Settings {
    static boolean NAME_IS_MANDATORY = true;
    static boolean RESTAURANT_IS_MANDATORY = true;
    static boolean COMMENT_IS_MANDATORY = false;
    static boolean RATING_IS_MANDATORY = true;


    public static boolean isCommentMandatory() {
        return COMMENT_IS_MANDATORY;
    }

    public static boolean isRestaurantMandatory() {
        return RESTAURANT_IS_MANDATORY;
    }

    public static boolean isNameMandatory() {
        return NAME_IS_MANDATORY;
    }
}