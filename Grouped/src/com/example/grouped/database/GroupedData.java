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
    }

    public static GroupedData getGroupedDataInstance(Context context) {
        if(GroupedData.instance == null) {
            GroupedData.instance = new GroupedData(context);
        }
        return GroupedData.instance;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean updateMember(Member member) {
        Cursor cursor = database.query(MemberTable.TABLE_NAME, new String[]{MemberTable.COLUMN_ID}, "id=?", new String[]{member.getId().toString()}, null, null, null);

        if(cursor.getCount() > 0) {
            if(database.update(MemberTable.TABLE_NAME, member.toDataRow(), "id=?", new String[]{member.getId().toString()}) > 0) {
                return true;
            } else {
                return false;
            }

        } else {
            if(database.insert(MemberTable.TABLE_NAME, null,
                    member.toDataRow()) == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public List<Member> getMembers(Long groupID) {
        List<Member> members = new ArrayList();

        Cursor cursor = database.query(MemberTable.TABLE_NAME,
                MemberTable.allColumns(), null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Member member = new Member();
            member.fromCursor(cursor);
            members.add(member);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return members;
    }


    public boolean storeGroup(Group group) {
        long insertId = database.insert(GroupTable.TABLE_NAME, null,
                group.toDataRow());

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
            Group group = new Group();
            group.fromCursor(cursor);
            groups.add(group);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return groups;
    }

    public Group getGroup(Long id) {
        Cursor cursor = database.query(GroupTable.TABLE_NAME, new String[]{GroupTable.COLUMN_ID}, "id=?", new String[]{id.toString()}, null, null, null);
        cursor.moveToFirst();
        Group group = new Group();
        group.fromCursor(cursor);
        cursor.close();
        return group;
    }

}
