package com.example.grouped.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.grouped.models.Group;
import com.example.grouped.models.Member;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all database operations for grouped app data.
 */
public class GroupedData {

    private static GroupedData instance = null;

    // Database fields
    private SQLiteDatabase database;
    private GroupedDatabaseHelper dbHelper;

    private GroupedData(Context context) {
        dbHelper = new GroupedDatabaseHelper(context);

        // for debugging
        this.open();
        database.delete(GroupTable.TABLE_NAME, null, null);
        database.delete(MemberTable.TABLE_NAME, null, null);
        this.close();
    }

    public static GroupedData getGroupedDataInstance(Context context) {
        if(GroupedData.instance == null) {
            GroupedData.instance = new GroupedData(context);
        }
        return GroupedData.instance;
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public boolean updateMember(Member member) {
        boolean updated = false;

        this.open();
        Cursor cursor = database.query(MemberTable.TABLE_NAME, new String[]{MemberTable.COLUMN_ID}, "_id=?", new String[]{member.getId().toString()}, null, null, null);

        if(cursor.getCount() > 0) {
            if(database.update(MemberTable.TABLE_NAME, member.toDataRow(), "_id=?", new String[]{member.getId().toString()}) > 0) {
                updated = true;
            }

        } else {
            if(database.insert(MemberTable.TABLE_NAME, null,
                    member.toDataRow()) == -1) {
                updated = false;
            }
        }

        this.close();
        return updated;
    }

    public List<Member> getMembers(Long groupID) {
        List<Member> members = new ArrayList();

        this.open();
        Cursor cursor = database.query(MemberTable.TABLE_NAME, MemberTable.allColumns(), null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Member member = new Member();
            member.fromCursor(cursor);
            members.add(member);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        this.close();
        return members;
    }


    public boolean storeGroup(Group group) {
        this.open();
        long insertId = database.insert(GroupTable.TABLE_NAME, null,
                group.toDataRow());
        group.setId(insertId);

        this.close();
        if(insertId != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteGroup(Long id) {
        this.open();
        database.delete(GroupTable.TABLE_NAME, GroupTable.COLUMN_ID
                + " = " + id, null);
        this.close();
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<Group>();

        this.open();
        Cursor cursor = database.query(GroupTable.TABLE_NAME,
                GroupTable.allColumns(), null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Group group = new Group();
            group.fromCursor(cursor);
            groups.add(group);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        this.close();
        return groups;
    }

    public Group getGroup(Long id) {
        this.open();
        Cursor cursor = database.query(GroupTable.TABLE_NAME, GroupTable.allColumns(), "_id=?", new String[]{id.toString()}, null, null, null);

        cursor.moveToFirst();
        Group group = new Group();
        group.fromCursor(cursor);

        cursor.close();
        this.close();
        return group;
    }

}
