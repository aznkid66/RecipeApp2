package com.example.patrick.recipeapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsDbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = SearchResultsDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "search";
    // tasks table name
    private static final String TABLE_RESULTS = "results";
    // tasks Table Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RECIPE_NAME = "recipeName";
    private static final String COLUMN_RECIPE_RATING = "recipeRating";
    private static final String COLUMN_RECIPE_LINK   = "recipeLink";

    public SearchResultsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_RESULTS + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECIPE_NAME + " TEXT, "
                + COLUMN_RECIPE_RATING + " FLOAT, "
                + COLUMN_RECIPE_LINK + " TEXT" + " ) ";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        // Create tables again
        onCreate(db);
    }

    public void addResult(SearchResultItem result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, result.NAME); // task name
        values.put(COLUMN_RECIPE_RATING, result.RATING);
        values.put(COLUMN_RECIPE_LINK, result.LINK);
        // Inserting Row
        result.setId(
                db.insert(TABLE_RESULTS, null, values));
        db.close(); // Closing database connection
    }
//
//    public void removeIngredient(String ingredientName) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Deleting Row
//        db.delete(TABLE_INGREDIENTS, KEY_INGREDIENT_NAME + " = '" + ingredientName + "'", null);
//        db.close(); // Closing database connection
//    }

    public List<SearchResultItem> getAllResults() {
        List<SearchResultItem> resultsList = new ArrayList<SearchResultItem>();
// Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESULTS +
                " ORDER BY " + COLUMN_RECIPE_RATING + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SearchResultItem result;
                result = new SearchResultItem(
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getString(3)
                );
                result.setId(cursor.getLong(0));
// Adding contact to list
                resultsList.add(result);
            } while (cursor.moveToNext());
        }
// return task list
        return resultsList;
    }

    public int deleteAllResults() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RESULTS, null, null);
    }
}
