package com.stand_still.foodpinions.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stand_still.foodpinions.exceptions.DishInDatabaseHasInvalidIDException;
import com.stand_still.foodpinions.exceptions.FoodPinionDishIsNullException;
import com.stand_still.foodpinions.exceptions.FoodPinionInDatabaseHasInvalidIDException;
import com.stand_still.foodpinions.exceptions.RestaurantInDatabaseHasInvalidIDException;
import com.stand_still.foodpinions.exceptions.UserInDatabaseHasInvalidIDException;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All static variables
    // Database version
    private static final int DATABASE_VERSION = 70;

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

        String CREATE_DISHES_TABLE = "CREATE TABLE " + TABLE_DISHES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + DISH + " TEXT NOT NULL,"
                + RESTAURANT_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANTS + "(" + KEY_ID + ")"
//                + ",UNIQUE (" + DISH + "," + RESTAURANT_ID + ")"     // Don't use "ON CONFLICT REPLACE" to specify conflict behaviour. Should find a way that actually makes sense
                // Todo: Find way of doing what was trying to do above. Namely, ensure that a pair of column values together is unique
                + ")";                                              // ON CONFLICT {ROLLBACK, ABORT, FAIL, IGNORE}
        db.execSQL(CREATE_DISHES_TABLE);

        String CREATE_FOOD_PINIONS_TABLE = "CREATE TABLE " + TABLE_FOOD_PINIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + DISH_ID + " INTEGER NOT NULL,"
                + COMMENT + " TEXT,"
                + DATE_TIME + " TEXT NOT NULL,"
                + USER_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + DISH_ID + ") REFERENCES " + TABLE_DISHES + "(" + KEY_ID + "),"
                + "FOREIGN KEY (" + USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")"
//                + ",UNIQUE (" + DISH_ID + "," + USER_ID + ")"
//                + ", PRIMARY KEY (" + DISH_ID + "," + USER_ID + ")"
                // Todo: Find way of doing what was trying to do above. Namely, ensure that a pair of column values together is unique
                + ")";
        db.execSQL(CREATE_FOOD_PINIONS_TABLE);

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
        assert cursor != null;
        cursor.close();
        db.close();
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
        assert cursor != null;
        cursor.close();
        db.close();
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

        cursor.close();
        db.close();
        return restaurantList;
    }

    // Getting Restaurant Count
    public int getRestaurantsCount() {
        String countQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    // Updating single Restaurant
    public int updateRestaurant(Restaurant restaurant) {
        int restaurantID = restaurant.getID();

        SQLiteDatabase db = this.getWritableDatabase();

        // Ensure restaurantID is not null
        try {
            if (restaurantID < 1) {
                Restaurant restaurantByName = getRestaurantByName(restaurant.getName());
                if (restaurantByName == null) {
                    addRestaurant(restaurant);
                    restaurantByName = getRestaurantByName(restaurant.getName());
                }
                if (restaurantByName.getID() > 0) {
                    restaurant.setID(restaurantByName.getID());
                    restaurantID = restaurant.getID();
                } else throw new RestaurantInDatabaseHasInvalidIDException();
            }
        } catch (RestaurantInDatabaseHasInvalidIDException e) {
            // this should never happen
            return -1;
        }

        ContentValues values = new ContentValues();
        values.put(RESTAURANT, restaurant.getName());

        // Updating row
        return db.update(TABLE_RESTAURANTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(restaurantID)});
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
        ContentValues values = new ContentValues();
        Restaurant checkRestaurant = getRestaurantByName(
                dish.getRestaurant().getName()
        );
        if (checkRestaurant == null) {
            addRestaurant(dish.getRestaurant());
        }
        dish.setRestaurant(getRestaurantByName(
                dish.getRestaurant().getName()
        ));

        values.put(DISH, dish.getName());
        values.put(RESTAURANT_ID, dish.getRestaurant().getID());

        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting row
        db.insert(TABLE_DISHES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Dish
    public Dish getDish(int id) {
        String GET_DISH_RESTAURANT_QUERY = String.format(
                "SELECT A.%s, A.%s, B.%s, B.%s FROM %s A INNER JOIN %s B ON A.%s = B.%s WHERE A.%s=?",
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

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(GET_DISH_RESTAURANT_QUERY, new String[]{String.valueOf(id)});

        Dish dish;
        try {
            if (cursor.moveToFirst()) { // ALWAYS ALWAYS ALWAYS moveToFirst before checking cursor results
                Restaurant restaurant = new Restaurant(
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3)
                );

                dish = new Dish(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        restaurant
                );
            } else dish = null;
        } catch (CursorIndexOutOfBoundsException e) {
            dish = null;
        }

        cursor.close();
        db.close();
        return dish;
    }

    public Dish getDishByPair(String dishName, Restaurant checkRestaurant) {
        if (checkRestaurant.getID() < 1) {
            Restaurant restaurantByName = getRestaurantByName(checkRestaurant.getName());
            if (restaurantByName == null) {
                addRestaurant(checkRestaurant);
                restaurantByName = getRestaurantByName(checkRestaurant.getName());
            }
            checkRestaurant.setID(restaurantByName.getID());
        }

        List<Dish> dishesFromRestaurant = getDishesFromRestaurant(checkRestaurant);

        for (Dish dish : dishesFromRestaurant) {
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

        cursor.close();
        db.close();
        return dishList;
    }

    // Getting all Dishes
    public List<Dish> getDishesFromRestaurant(Restaurant restaurant) {
        List<Dish> dishList = new ArrayList<>();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_DISHES + " WHERE " + RESTAURANT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(restaurant.getID())});

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setID(Integer.parseInt(cursor.getString(0)));
                dish.setName(cursor.getString(1));
                dish.setRestaurant(getRestaurant(Integer.parseInt(cursor.getString(2))));
                // Add to list
                dishList.add(dish);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dishList;
    }

    // Getting Dish Count
    public int getDishesCount() {
        String countQuery = "SELECT * FROM " + TABLE_DISHES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    // Updating single Dish
    public int updateDish(Dish dish) {
        int dishID = dish.getID();
        Restaurant restaurant = dish.getRestaurant();
        int restaurantID = restaurant.getID();

        SQLiteDatabase db = this.getWritableDatabase();

        // Ensure dishId is not null
        try {
            if (dishID < 1) {
                Dish dishByPair = getDishByPair(dish.getName(), restaurant);
                if (dishByPair == null) {
                    addDish(dish);
                    dishByPair = getDishByPair(dish.getName(), restaurant);
                }
                if (dishByPair.getID() > 0) {
                    dish.setID(dishByPair.getID());
                    dishID = dish.getID();
                } else throw new DishInDatabaseHasInvalidIDException();
            }
        } catch (DishInDatabaseHasInvalidIDException e) {
            // this should never happen
            return -1;
        }
        // Ensure restaurantID is not null
        try {
            if (restaurantID < 1) {
                Restaurant restaurantByName = getRestaurantByName(restaurant.getName());
                if (restaurantByName == null) {
                    addRestaurant(restaurant);
                    restaurantByName = getRestaurantByName(restaurant.getName());
                }
                if (restaurantByName.getID() > 0) {
                    restaurant.setID(restaurantByName.getID());
                    restaurantID = restaurant.getID();
                } else throw new RestaurantInDatabaseHasInvalidIDException();
            }
        } catch (RestaurantInDatabaseHasInvalidIDException e) {
            // this should never happen
            return -1;
        }

        ContentValues values = new ContentValues();
        values.put(DISH, dish.getName());

        values.put(RESTAURANT_ID, restaurantID);

        // Updating row
        return db.update(TABLE_DISHES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(dishID)});
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
    public void addFoodPinion(FoodPinion foodPinion) { // Todo: Fix issue with adding same FoodPinion multiple times and them being treated separately (because of timestamp)
        ContentValues values = new ContentValues();
        Dish checkDish = getDishByPair(
                foodPinion.getDish().getName(),
                foodPinion.getDish().getRestaurant()
        );
        if (checkDish == null) {
            addDish(foodPinion.getDish());
        }

        foodPinion.setDish(getDishByPair(
                foodPinion.getDish().getName(),
                foodPinion.getDish().getRestaurant()
        ));

        values.put(DISH_ID, foodPinion.getDish().getID());
        values.put(COMMENT, foodPinion.getComment());
        values.put(DATE_TIME, foodPinion.getDateTimeString());
        values.put(USER_ID, foodPinion.getUser().getID());

        // IMPORTANT: The DB can be closed by another method in this class, invalidating db.insert
        // Therefore, always open the db after any other methods from this class.
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting row
        long rowInserted = db.insertOrThrow(TABLE_FOOD_PINIONS, null, values);
        if (rowInserted != -1) {
            int test = 1;   // throw new FoodPinionNotInsertedException();
            test++;
        } else {
            int test = 2;
            test++;
        }
        db.close(); // Closing database connection
    }

    // Getting single FoodPinion
    public FoodPinion getFoodPinion(int id) {
        String GET_DISH_RESTAURANT_QUERY = String.format(
                "SELECT A.%s, A.%s, A.%s, A.%s, B.%s, B.%s, C.%s, C.%s " +
                        "FROM %s A INNER JOIN %s B " +
                        "ON A.%s = B.%s " +
                        "INNER JOIN %s C " +
                        "ON B.%s = C.%s " +
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
                DISH_ID, // 4
                KEY_ID,
                TABLE_RESTAURANTS,
                RESTAURANT_ID, // 6
                KEY_ID,
                KEY_ID
        );

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_DISH_RESTAURANT_QUERY, new String[]{String.valueOf(id)});

        FoodPinion foodPinion;

        try {
            if (cursor.moveToFirst()) {
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
            } else foodPinion = null;
        } catch (CursorIndexOutOfBoundsException e) {
            foodPinion = null;
        }

        cursor.close();
        db.close();
        return foodPinion;
    }


    public FoodPinion getFoodPinionByPair(Dish checkDish, User user) {
        if (checkDish.getID() < 1) {
            Dish dishByPair = getDishByPair(checkDish.getName(), checkDish.getRestaurant());
            if (dishByPair == null) {
                addDish(checkDish);
                dishByPair = getDishByPair(checkDish.getName(), checkDish.getRestaurant());
            }
            checkDish.setID(dishByPair.getID());
        }

        List<FoodPinion> foodPinionsByDish = getFoodPinionsByDish(checkDish);
        if (foodPinionsByDish.size() < 1) {
//            throw new NoFoodPinionsForDishException();
        }

        for (FoodPinion foodPinion :
                foodPinionsByDish) {
            if (foodPinion.getUser().getID() == user.getID()) {     // Throws nullpointerexception because foodPinion.user is null
                return foodPinion;
            }
        }
        return null;
    }

    private List<FoodPinion> getFoodPinionsByDish(Dish checkDish) {
        List<FoodPinion> foodPinionList = new ArrayList<>();
        String GET_FOOD_PINION_DISH_QUERY = String.format(
                "SELECT A.%s, A.%s, A.%s, A.%s, " +
                        "B.%s, B.%s, " +
                        "C.%s, C.%s " +
                        "FROM %s A INNER JOIN %s B " +
                        "ON A.%s = B.%s " +
                        "INNER JOIN %s C " +
                        "ON B.%s = C.%s " +
                        "WHERE B.%s = ?",
                KEY_ID,     // 0    // FoodPinion
                COMMENT,    // 1
                DATE_TIME,  // 2
                USER_ID,    // 3
                KEY_ID,     // 4    // Dish
                DISH,       // 5
                KEY_ID,     // 6    // Restaurant
                RESTAURANT, // 7
//                KEY_ID,           // User
//                USER_NAME
                TABLE_FOOD_PINIONS,
                TABLE_DISHES,
                DISH_ID,    // 4
                KEY_ID,
                TABLE_RESTAURANTS,
                RESTAURANT_ID,  // 6
                KEY_ID,
                KEY_ID
        );

        SQLiteDatabase db = this.getReadableDatabase();
        String dishID = String.valueOf(checkDish.getID());
        Cursor cursor = db.rawQuery(GET_FOOD_PINION_DISH_QUERY, new String[]{dishID});

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant(
                        Integer.parseInt(cursor.getString(6)),   // id
                        cursor.getString(7)                      // name
                );
                Dish dish = new Dish(
                        Integer.parseInt(cursor.getString(4)),   // id
                        cursor.getString(5),                     // name
                        restaurant
                );
                User user = getUser(Integer.parseInt(cursor.getString(3)));

                FoodPinion foodPinion = new FoodPinion(
                        Integer.parseInt(cursor.getString(0)),  // id
                        dish,
                        cursor.getString(1),                    // comment
                        cursor.getString(2),                    // datetime
                        user
                );

                foodPinion.setUser(user);
                // Add to list
                foodPinionList.add(foodPinion);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
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
        try {
            if (cursor.moveToFirst()) {
                do {
                    FoodPinion foodPinion = new FoodPinion();
                    foodPinion.setID(Integer.parseInt(cursor.getString(0)));

                    Dish dish = getDish(
                            Integer.parseInt(cursor.getString(1))
                    );
                    if (dish == null)
                        throw new FoodPinionDishIsNullException();
                    foodPinion.setDish(dish);

                    foodPinion.setComment(cursor.getString(2));
                    foodPinion.setDateTime(cursor.getString(3));
                    // Add to list
                    foodPinionList.add(foodPinion);
                } while (cursor.moveToNext());
            }
        } catch (FoodPinionDishIsNullException e) {
            foodPinionList = null;
        }

        cursor.close();
        db.close();
        return foodPinionList;
    }

    // Getting all FoodPinions as FoodPinionArrayList       Todo: try and combine this with getAllFoodPinions()
    public FoodPinionArrayList getAllFoodPinionsArrayList() {
        FoodPinionArrayList foodPinionArrayList = new FoodPinionArrayList();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_FOOD_PINIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        try {
            if (cursor.moveToFirst()) {
                do {
                    FoodPinion foodPinion = new FoodPinion();
                    foodPinion.setID(Integer.parseInt(cursor.getString(0)));

                    Dish dish = getDish(
                            Integer.parseInt(cursor.getString(1))
                    );
                    if (dish == null)
                        throw new FoodPinionDishIsNullException();
                    foodPinion.setDish(dish);

                    foodPinion.setComment(cursor.getString(2));
                    foodPinion.setDateTime(cursor.getString(3));
                    // Add to list
                    foodPinionArrayList.add(foodPinion);
                } while (cursor.moveToNext());
            }
        } catch (FoodPinionDishIsNullException e) {
            foodPinionArrayList = null;
        }

        cursor.close();
        db.close();
        return foodPinionArrayList;
    }

    // Getting FoodPinion Count
    public int getFoodPinionsCount() {
        String countQuery = "SELECT * FROM " + TABLE_FOOD_PINIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    // Updating single FoodPinion
    public int updateFoodPinion(FoodPinion foodPinion) {
        Dish dish = foodPinion.getDish();
        int dishId = dish.getID();
        User user = foodPinion.getUser();
        int userId = user.getID();
        int foodPinionID = foodPinion.getID();

        // Ensure dishId is not null
        try {
            if (dishId < 1) {
                Dish dishByPair = getDishByPair(dish.getName(), dish.getRestaurant());
                if (dishByPair == null) {
                    addDish(dish);
                    dishByPair = getDishByPair(dish.getName(), dish.getRestaurant());
                }
                if (dishByPair.getID() > 0) {
                    dish.setID(dishByPair.getID());
                    dishId = dish.getID();
                } else throw new DishInDatabaseHasInvalidIDException();
            }
        } catch (DishInDatabaseHasInvalidIDException e) {
            // this should never happen
            return -1;
        }
        // Ensure userId is not null
        try {
            if (userId < 1) {
                User userByName = getUserByName(user.getName());
                if (userByName == null) {
                    addUser(user);
                    userByName = getUserByName(user.getName());
                }
                if (userByName.getID() > 0) {
                    user.setID(userByName.getID());
                    userId = user.getID();
                } else throw new UserInDatabaseHasInvalidIDException();
            }
        } catch (UserInDatabaseHasInvalidIDException e) {
            // this should never happen
            return -1;
        }
        // Ensure foodPinionId is not null
        try {
            if (foodPinionID < 1) {
                FoodPinion foodPinionByPair = getFoodPinionByPair(dish, user);
                if (foodPinionByPair == null) {
                    addFoodPinion(foodPinion);
                    foodPinionByPair = getFoodPinionByPair(dish, user);
                }
                if (foodPinionByPair.getID() > 0) {
                    foodPinion.setID(foodPinionByPair.getID());
                    foodPinionID = foodPinion.getID();
                } else throw new FoodPinionInDatabaseHasInvalidIDException();
            }
        } catch (FoodPinionInDatabaseHasInvalidIDException e) {
            // this should never happen
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISH_ID, dishId);
        values.put(COMMENT, foodPinion.getComment());
        values.put(DATE_TIME, foodPinion.getDateTimeString());
        values.put(USER_ID, userId);

        // Updating row

        return db.update(TABLE_FOOD_PINIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(foodPinionID)}
        );
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

        assert cursor != null;
        cursor.close();
        db.close();
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

        assert cursor != null;
        cursor.close();
        db.close();
        return user;
    }

    public int getUserCount() {
        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // Select All query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getString(1)
                );
                user.setID(Integer.parseInt(cursor.getString(0)));
                // Add to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public int updateUser(User user) {
        int userID = user.getID();
        String userName = user.getName();

        if (userID < 1) {
            User userByName = getUserByName(userName);
            if (userByName == null) {
                addUser(user);
                userByName = getUserByName(userName);
            }
            user.setID(userByName.getID());
            userID = user.getID();
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);

        // Updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(userID)});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }
}