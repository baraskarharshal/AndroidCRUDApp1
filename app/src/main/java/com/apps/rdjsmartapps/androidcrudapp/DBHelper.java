package com.apps.rdjsmartapps.androidcrudapp;

/**
 * Created by Harshal on 4/4/2018.
 */

public class DBHelper {

    //public static final String UPGRADE_TABLE_NAME = "upgrade_table";Movie
    public static final String TABLE_NAME = "Movie";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Movie" +
            "(MovieId INTEGER PRIMARY KEY AUTOINCREMENT" +
            ",MovieName VARCHAR" +
            ",MovieRating INTEGER" +
            ",Image BLOB" +
            ");";

    public DBHelper() {
        // do something
    }
}
