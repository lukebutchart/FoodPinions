package com.stand_still.foodpinions.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All static variables
    // Database version
    private static final int DATABASE_VERSION = 14;

    // Database name
    private static final String DATABASE_NAME = "foodPinionsManager";

    // Table names
    private static final String TABLE_RESTAURANTS = "tblRestaurants";
    private static final String TABLE_DISHES = "tblDishes";
    private static final String TABLE_FOOD_PINIONS = "tblFoodPinions";
    private static final String TABLE_USERS = "tblUsers";

    // FoodPinions table columns names
    private static final String KEY_ID = "id";
    private static final String RESTAURANT = "restaurant";
    private static final String DISH = "dish";
    private static final String COMMENT = "comment";
    //    private static final String KEY_RATING = "rating";
    private static final String DATE_TIME = "dateTime";
    private static final String RESTAURANT_ID = "restaurantID";
    private static final String DISH_ID = "dishID";
//    private static final String USER_NAME = "userName";
    private static final String CONSTRAINT = "myConstraint";
    private static final String USER_ID = "userID";
    private static final String USER_NAME = "userName";

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANTS_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + RESTAURANT + " TEXT NOT NULL UNIQUE"
                + ")";
        db.execSQL(CREATE_RESTAURANTS_TABLE);

//        String CREATE_DISHES_TABLE = "CREATE TABLE " + TABLE_DISHES + "("
//                + KEY_ID + " INTEGER PRIMARY KEY,"
////                + "CONSTRAINT " + KEY_ID + " PRIMARY KEY (" + DISH + "," + RESTAURANT_ID + "),"
//                + DISH + " TEXT NOT NULL,"
//                + RESTAURANT_ID + " INTEGER NOT NULL,CONSTRAINT "
//                + CONSTRAINT + " NOT NULL UNIQUE (" + DISH + "," + RESTAURANT_ID + ")"
//                + ")";
//        db.execSQL(CREATE_DISHES_TABLE);

        String CREATE_DISHES_TABLE = "CREATE TABLE " + TABLE_DISHES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + DISH + " TEXT NOT NULL,"
                + RESTAURANT_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANTS + "(" + KEY_ID + "),"
                + "UNIQUE (" + DISH + "," + RESTAURANT_ID + ")"     // Don't use "ON CONFLICT REPLACE" to specify conflict behaviour. Should find a way that actually makes sense
                + ")";                                              // ON CONFLICT {ROLLBACK, ABORT, FAIL, IGNORE}
        db.execSQL(CREATE_DISHES_TABLE);

        String CREATE_FOOD_PINIONS_TABLE = "CREATE TABLE " + TABLE_FOOD_PINIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + DISH_ID + " INTEGER NOT NULL,"
                + COMMENT + " TEXT,"
                + DATE_TIME + " TEXT NOT NULL,"
                + USER_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + DISH_ID + ") REFERENCES " + TABLE_DISHES + "(" + KEY_ID + "),"
                + "FOREIGN KEY (" + USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "UNIQUE (" + DISH_ID + "," + USER_ID + ")"
                + ")";
        db.execSQL(CREATE_FOOD_PINIONS_TABLE);

//        String CREATE_FOOD_PINIONS_TABLE = "CREATE TABLE " + TABLE_FOOD_PINIONS + "("
////                + KEY_ID + " INTEGER PRIMARY KEY,"
////                + "CONSTRAINT " + KEY_ID + " PRIMARY KEY (" + DISH_ID + "," + USER_NAME + "),"
//                + DISH_ID + " INTEGER UNIQUE NOT NULL,"
//                + COMMENT + " TEXT,"
//                + DATE_TIME + " TEXT NOT NULL,"
//                + USER_NAME + " TEXT NOT NULL" +
//                "," + "CONSTRAINT " + KEY_ID + " PRIMARY KEY (" + DISH_ID + "," + USER_NAME + ")"
//                + ")";
//        db.execSQL(CREATE_FOOD_PINIONS_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + USER_NAME + " TEXT UNIQUE NOT NULL"
                + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_PINIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    // ================================= Restaurant methods ===================================
    // Adding new Restaurant
    public void addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RESTAURANT, restaurant.getName()); // Restaurant name

        // Inserting row
        db.insert(TABLE_RESTAURANTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Restaurant
    public Restaurant getRestaurant(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RESTAURANTS, new String[]{KEY_ID, RESTAURANT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Restaurant restaurant;
        try {
            restaurant = new Restaurant(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        } catch (CursorIndexOutOfBoundsException e) {
            restaurant = null;
        }
        return restaurant;
    }

    // Getting single Restaurant by name
    public Restaurant getRestaurantByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RESTAURANTS, new String[]{KEY_ID, RESTAURANT}, RESTAURANT + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Restaurant restaurant;
        try {
            restaurant = new Restaurant(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        } catch (CursorIndexOutOfBoundsException e) {
            restaurant = null;
        }
        return restaurant;
    }

    // Getting all Restaurants
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setID(Integer.parseInt(cursor.getString(0)));
                restaurant.setName(cursor.getString(1));
                // Add to list
                restaurantList.add(restaurant);
            } while (cursor.moveToNext());
        }

        return restaurantList;
    }

    // Getting Restaurant Count
    public int getRestaurantsCount() {
        String countQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Updating single Restaurant
    public int updateRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RESTAURANT, restaurant.getName());

        // Updating row
        return db.update(TABLE_RESTAURANTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(restaurant.getID())});
    }

    // Deleting single Restaurant
    public void deleteRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANTS, KEY_ID + " = ?",
                new String[]{String.valueOf(restaurant.getID())});
        db.close();
    }

    // ==================================== Dish methods ======================================
    // Adding new Dish
    public void addDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // Todo: CHECK FOR RESTAURANT join?
        Restaurant checkRestaurant = getRestaurant(dish.getRestaurant().getID());
        if (checkRestaurant == null) {
            addRestaurant(dish.getRestaurant());
        } else {
            // Todo: ???
        }

        values.put(DISH, dish.getName());
        values.put(RESTAURANT_ID, dish.getRestaurant().getID());

        // Inserting row
        db.insert(TABLE_DISHES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Dish
    public Dish getDish(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String GET_DISH_RESTAURANT_QUERY = String.format(
                "SELECT A.%s, A.%s, B.%s, B.%s FROM %s A INNER JOIN %s B ON A.%s = B.%s WHERE A.%s = ?",
                KEY_ID, // 0
                DISH, // 1
                KEY_ID,
                RESTAURANT, // 3
                TABLE_DISHES,
                TABLE_RESTAURANTS,
                RESTAURANT_ID, // 2
                KEY_ID,
                KEY_ID
        );

        Cursor cursor = db.rawQuery(GET_DISH_RESTAURANT_QUERY, new String[]{String.valueOf(id)});

        Dish dish;
        try {
            Restaurant restaurant = new Restaurant(
                    Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3)
            );

            dish = new Dish(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    restaurant
            );
        } catch (CursorIndexOutOfBoundsException e) {
            dish = null;
        }

        return dish;
    }

    public Dish getDishByPair(String dishName, Restaurant checkRestaurant) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (checkRestaurant.getID() == 0){  // Todo: Make more efficient
            Restaurant restaurantByName = getRestaurantByName(checkRestaurant.getName());
            if (restaurantByName == null){
                addRestaurant(checkRestaurant);
                restaurantByName = getRestaurantByName(checkRestaurant.getName());
            }
            checkRestaurant.setID(restaurantByName.getID());
        }

        int restaurantId = checkRestaurant.getID();

        List<Dish> dishesFromRestaurant = getDishesFromRestaurant(checkRestaurant);

        for (Dish dish :
                dishesFromRestaurant) {
            if (dish.getName().equals(dishName))
                return dish;
        }
        return null;
    }

    // Getting all Dishes
    public List<Dish> getAllDishes() {
        List<Dish> dishList = new ArrayList<>();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_DISHES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
//        cursor.moveToFirst();
            do {
                Dish dish = new Dish();
                dish.setID(Integer.parseInt(cursor.getString(0)));
                dish.setName(cursor.getString(1));
                dish.setRestaurant(getRestaurant(Integer.parseInt(cursor.getString(2))));
                // Add to list
                dishList.add(dish);
            } while (cursor.moveToNext());
        }

        return dishList;
    }

    // Getting all Dishes
    public List<Dish> getDishesFromRestaurant(Restaurant restaurant) {
        List<Dish> dishList = new ArrayList<>();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_DISHES + " WHERE " + RESTAURANT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase(); //Todo: Make this actually work
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(restaurant.getID())});

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
//        cursor.moveToFirst();
            do {
                Dish dish = new Dish();
                dish.setID(Integer.parseInt(cursor.getString(0)));
                dish.setName(cursor.getString(1));
                dish.setRestaurant(getRestaurant(Integer.parseInt(cursor.getString(2))));
                // Add to list
                dishList.add(dish);
            } while (cursor.moveToNext());
        }

        return dishList;
    }

    // Getting Dish Count
    public int getDishesCount() {
        String countQuery = "SELECT * FROM " + TABLE_DISHES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Updating single Dish
    public int updateDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISH, dish.getName());
        values.put(RESTAURANT_ID, dish.getRestaurant().getID());

        // Updating row
        return db.update(TABLE_DISHES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(dish.getID())});
    }

    // Deleting single Dish
    public void deleteDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISHES, KEY_ID + " = ?",
                new String[]{String.valueOf(dish.getID())});
        db.close();
    }

    // ================================= FoodPinion methods ===================================
    // Adding new FoodPinion
    public void addFoodPinion(FoodPinion foodPinion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // TODO: CHECK FOR DISH join?
        Dish checkDish = getDishByPair(
                foodPinion.getDish().getName(),
                foodPinion.getDish().getRestaurant()
        );
        if (checkDish == null) {
            addDish(foodPinion.getDish());
        } else {
            // Todo: ???
        }

        values.put(DISH_ID, foodPinion.getDish().getID());
        values.put(COMMENT, foodPinion.getComment());
        values.put(DATE_TIME, foodPinion.getDateTimeString());
        values.put(USER_ID, foodPinion.getUser().getID());

        // Inserting row
        db.insert(TABLE_FOOD_PINIONS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single FoodPinion
    public FoodPinion getFoodPinion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String GET_DISH_RESTAURANT_QUERY = String.format(
                "SELECT A.%s, A.%s, A.%s, A.%s, B.%s, B.%s, C.%s, C.%s " +
                        "FROM %s A INNER JOIN %s B INNER JOIN %s C " +
                        "ON A.%s = B.%s AND B.%s = C.%s " +
                        "WHERE A.%s = ?",
                KEY_ID, // 0
                COMMENT, // 1
                DATE_TIME, // 2
                    USER_ID, // 3
                KEY_ID,
                DISH, // 5
                KEY_ID,
                RESTAURANT, // 7
                TABLE_FOOD_PINIONS,
                TABLE_DISHES,
                TABLE_RESTAURANTS,
                DISH_ID, // 4
                KEY_ID,
                RESTAURANT_ID, // 6
                KEY_ID,
                KEY_ID
        );

        Cursor cursor = db.rawQuery(GET_DISH_RESTAURANT_QUERY, new String[]{String.valueOf(id)});

        FoodPinion foodPinion;

        try {
            Restaurant restaurant = new Restaurant(
                    Integer.parseInt(cursor.getString(6)),
                    cursor.getString(7)
            );

            Dish dish = new Dish(
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    restaurant
            );

            User user = getUser(Integer.parseInt(cursor.getString(3)));

            foodPinion = new FoodPinion(
                    Integer.parseInt(cursor.getString(0)),
                    dish,
                    cursor.getString(1),
                    cursor.getString(2),
                    user
//                    Integer.parseInt(cursor.getString(3)),
            );
        } catch (CursorIndexOutOfBoundsException e) {
            foodPinion = null;
        }
        return foodPinion;
    }




    public FoodPinion getFoodPinionByPair(Dish checkDish, User user) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (checkDish.getID() < 1){
            Dish dishByPair = getDishByPair(checkDish.getName(), checkDish.getRestaurant());
            if (dishByPair == null){
                addDish(checkDish);
                dishByPair = getDishByPair(checkDish.getName(), checkDish.getRestaurant());
            }
            checkDish.setID(dishByPair.getID());
        }

        List<FoodPinion> foodPinionsByDish = getFoodPinionsByDish(checkDish);

        for (FoodPinion foodPinion :
                foodPinionsByDish) {
            if (foodPinion.getUser().getID() == user.getID()){
                return foodPinion;
            }
        }
        return null;

//        if (checkRestaurant.getID() == 0){  // Todo: Make more efficient
//            Restaurant restaurantByName = getRestaurantByName(checkRestaurant.getName());
//            if (restaurantByName == null){
//                addRestaurant(checkRestaurant);
//                restaurantByName = getRestaurantByName(checkRestaurant.getName());
//            }
//            checkRestaurant.setID(restaurantByName.getID());
//        }
//
//        int restaurantId = checkRestaurant.getID();
//
//        List<Dish> dishesFromRestaurant = getDishesFromRestaurant(checkRestaurant);
//
//        for (Dish dish :
//                dishesFromRestaurant) {
//            if (dish.getName().equals(dishName))
//                return dish;
//        }
//        return null;
    }

    private List<FoodPinion> getFoodPinionsByDish(Dish checkDish) {
        List<FoodPinion> foodPinionList = new ArrayList<>();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_FOOD_PINIONS + " WHERE " + DISH_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase(); //Todo: Make this actually work
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(checkDish.getID())});

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                FoodPinion foodPinion = new FoodPinion();
                foodPinion.setID(Integer.parseInt(cursor.getString(0)));
                foodPinion.setDish(getDish(Integer.parseInt(cursor.getString(1))));
                foodPinion.setComment(cursor.getString(2));
                foodPinion.setDateTime(cursor.getString(3));
                foodPinion.setUser(getUser(Integer.parseInt(cursor.getString(4))));
                // Add to list
                foodPinionList.add(foodPinion);
            } while (cursor.moveToNext());
        }

        return foodPinionList;
    }


    // Getting all FoodPinions
    public List<FoodPinion> getAllFoodPinions() {
        List<FoodPinion> foodPinionList = new ArrayList<>();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_FOOD_PINIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                FoodPinion foodPinion = new FoodPinion();
                foodPinion.setID(Integer.parseInt(cursor.getString(0)));
                foodPinion.setDish(getDish(Integer.parseInt(cursor.getString(1)))); // Todo: Replace getDish call with SQL command
                foodPinion.setComment(cursor.getString(2));
                foodPinion.setDateTime(cursor.getString(3));
                // Add to list
                foodPinionList.add(foodPinion);
            } while (cursor.moveToNext());
        }

        return foodPinionList;
    }

    // Getting FoodPinion Count
    public int getFoodPinionsCount() {
        String countQuery = "SELECT * FROM " + TABLE_FOOD_PINIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Updating single FoodPinion
    public int updateFoodPinion(FoodPinion foodPinion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISH_ID, foodPinion.getDishName());
        values.put(COMMENT, foodPinion.getComment());
        values.put(DATE_TIME, foodPinion.getDateTimeString());

        // Updating row
        return db.update(TABLE_FOOD_PINIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(foodPinion.getID())});
    }

    // Deleting single FoodPinion
    public void deleteFoodPinion(FoodPinion foodPinion) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD_PINIONS, KEY_ID + " = ?",
                new String[]{String.valueOf(foodPinion.getID())});
        db.close();
    }

    // ===================================== User methods =======================================
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName()); // Restaurant name

        // Inserting row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    public User getUserByName(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID, USER_NAME}, USER_NAME + "=?",
                new String[]{String.valueOf(userName)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user;
        try {
            user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        } catch (CursorIndexOutOfBoundsException e) {
            user = null;
        }
        return user;
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID, USER_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user;
        try {
            user = new User(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
        } catch (CursorIndexOutOfBoundsException e) {
            user = null;
        }
        return user;
    }
}
