package com.apps.rdjsmartapps.androidcrudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Harshal on 4/4/2018.
 */

public class DBConnect extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MovieListDB";


    public DBConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(DBHelper.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    // Insert method

    public Long insert(String table_name, ContentValues initialValues){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.insert(table_name, null, initialValues);
    }

    // Update method

    public int update(String table_name, ContentValues initialValues, String whereClause){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.update(table_name, initialValues, whereClause, null);
    }

    // Raw query method

    public Cursor rawQuery(String queryString){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(queryString, null);
    }

    // execute sql method

    public void execSQL(String queryString){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(queryString);
    }


}
