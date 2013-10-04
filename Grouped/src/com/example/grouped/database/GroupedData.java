package com.example.grouped.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all database operations for grouped app data.
 */
public class GroupedData {

    // Database fields
    private SQLiteDatabase database;
    private GroupedDatabaseHelper dbHelper;

    public GroupedData(Context context) {
        dbHelper = new GroupedDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean storeGroup(Group group) {
        ContentValues values = new ContentValues();

        values.put(GroupTable.COLUMN_ID, group.getId());
        values.put(GroupTable.COLUMN_NAME, group.getName());
        values.put(GroupTable.COLUMN_KEY, group.getKey());

        long insertId = database.insert(GroupTable.TABLE_NAME, null,
                values);

        if(insertId != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteGroup(int id) {
        database.delete(GroupTable.TABLE_NAME, GroupTable.COLUMN_ID
                + " = " + id, null);
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<Group>();

        Cursor cursor = database.query(GroupTable.TABLE_NAME,
                GroupTable.allColumns(), null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Group group = cursorToGroup(cursor);
            groups.add(group);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return groups;
    }

    private Group cursorToGroup(Cursor cursor) {
        Group group = new Group();
        group.setId(cursor.getLong(cursor.getColumnIndex(GroupTable.COLUMN_ID)));
        group.setName(cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_NAME)));
        group.setKey(cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_KEY)));
        return group;
    }
}
