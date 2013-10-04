package com.example.grouped.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Represents the Groups table in the sqlite db
 */
public class GroupTable {

    public static final String TABLE_NAME = "groups";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_KEY = "crypt_key";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_KEY + " text not null);";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(GroupTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public static String[] allColumns() {
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_KEY};
        return columns;
    }
}
