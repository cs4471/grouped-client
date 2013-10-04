package com.example.grouped.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Represents the Members table in the sqlite db
 */
public class MemberTable {

    public static final String TABLE_NAME = "members";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_CERTAINTY = "certainty";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NICKNAME + " text, "
            + COLUMN_LAT + " text, "
            + COLUMN_LNG + " text, "
            + COLUMN_CERTAINTY + " text, "
            + COLUMN_TIMESTAMP + "text not null);";

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
        String[] columns = {COLUMN_ID, COLUMN_NICKNAME, COLUMN_LAT, COLUMN_LNG, COLUMN_CERTAINTY, COLUMN_TIMESTAMP};
        return columns;
    }

}
