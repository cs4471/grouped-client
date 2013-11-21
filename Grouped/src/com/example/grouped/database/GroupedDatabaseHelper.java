package com.example.grouped.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This will create/open the grouped database.
 *  currently consists of 3 tables Groups, members, and messages
 */
public class GroupedDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "grouped.db";
    private static final int DATABASE_VERSION = 1;

    public GroupedDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        GroupTable.onCreate(database);
        MemberTable.onCreate(database);
        MessageTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        GroupTable.onUpgrade(database, oldVersion, newVersion);
        MemberTable.onUpgrade(database, oldVersion, newVersion);
        MessageTable.onUpgrade(database, oldVersion, newVersion);
    }

}
