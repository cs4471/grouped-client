package com.example.grouped.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Represents the Members table in the sqlite db
 */
public class MemberTable {

    public static final String TABLE_NAME = "members";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GROUPID = "group_id";
    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_CERTAINTY = "certainty";
    public static final String COLUMN_ME = "me";
    public static final String COLUMN_CHECKIN_ID = "checkin_id";


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_GROUPID + " integer, "
            + COLUMN_NICKNAME + " text, "
            + COLUMN_LAT + " text, "
            + COLUMN_LNG + " text, "
            + COLUMN_CERTAINTY + " text, "
            + COLUMN_ME + " integer default 0, "
            + COLUMN_CHECKIN_ID + " integer default -1, "
            + COLUMN_STATUS + " text);";

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
        String[] columns = {COLUMN_ID, COLUMN_GROUPID, COLUMN_NICKNAME, COLUMN_CHECKIN_ID, COLUMN_ME, COLUMN_STATUS, COLUMN_LAT, COLUMN_LNG, COLUMN_CERTAINTY};
        return columns;
    }

}
