package com.stand_still.foodpinions.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All static variables
    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "foodPinionsManager";

    // Table names
    private static final String TABLE_RESTAURANTS = "tblRestaurants";
    private static final String TABLE_DISHES = "tblDishes";
    private static final String TABLE_FOOD_PINIONS = "tblFoodPinions";

    // FoodPinions table columns names
    private static final String KEY_ID = "id";
    private static final String RESTAURANT = "restaurant";
    private static final String DISH = "dish";
    private static final String COMMENT = "comment";
    //    private static final String KEY_RATING = "rating";
    private static final String DATE_TIME = "dateTime";
    private static final String RESTAURANT_ID = "restaurantID";
    private static final String DISH_ID = "dishID";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANTS_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + RESTAURANT + " TEXT" + ")";
        db.execSQL(CREATE_RESTAURANTS_TABLE);

        String CREATE_DISHES_TABLE = "CREATE TABLE " + TABLE_DISHES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + DISH + " TEXT,"
                + RESTAURANT_ID + " INTEGER" + ")";
        db.execSQL(CREATE_DISHES_TABLE);

        String CREATE_FOOD_PINIONS_TABLE = "CREATE TABLE " + TABLE_FOOD_PINIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + DISH_ID + " INTEGER,"
                + COMMENT + " TEXT,"
                + DATE_TIME + " TEXT" + ")";
        db.execSQL(CREATE_FOOD_PINIONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_PINIONS);

        // Create tables again
        onCreate(db);
    }
}
