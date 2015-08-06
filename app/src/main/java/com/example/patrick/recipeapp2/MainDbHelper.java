package com.example.patrick.recipeapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MainDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "fridgeManager";
    // tasks table name
    private static final String TABLE_INGREDIENTS = "ingredients";
    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_INGREDIENT_NAME = "ingredientName";
    //private static final String KEY_STATUS = "status";

    public MainDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_INGREDIENTS + " ( "
        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + KEY_INGREDIENT_NAME + " TEXT" + " ) ";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        // Create tables again
        onCreate(db);
    }

    public void addIngredient(String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INGREDIENT_NAME, ingredientName); // task name
        // Inserting Row
        db.insert(TABLE_INGREDIENTS, null, values);
        db.close(); // Closing database connection
    }

    public void removeIngredient(String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Deleting Row
        db.delete(TABLE_INGREDIENTS, KEY_INGREDIENT_NAME + " = '" + ingredientName + "'", null);
        db.close(); // Closing database connection
    }

    public List<String> getAllIngredients() {
        List<String> ingredientsList = new ArrayList<String>();
// Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String ingredient;
                ingredient = cursor.getString(1);
// Adding contact to list
                ingredientsList.add(ingredient);
            } while (cursor.moveToNext());
        }
// return task list
        return ingredientsList;
    }
}
