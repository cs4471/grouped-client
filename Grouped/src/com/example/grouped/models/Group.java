package com.example.grouped.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.grouped.database.GroupTable;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Group {

    private String key = null;

    @Expose
    private String name = null;

    @Expose
    private String event = null;

    @Expose
    private String length = null;

    @Expose
    private String roam = null;

    @Expose
    private Long id = null;

    private ArrayList<Member> members = null;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRoam() {
        return roam;
    }

    public void setRoam(String roam) {
        this.roam = roam;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getKey() {
        // temporary key placeholder
        return "Changeee";
    }

    public void setKey(String key) {
        if(this.key == null){
            key = "yesass";
        }
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void merge(Group addAttributesFrom) {
        if(this.getName() == null) this.setName(addAttributesFrom.getName());
        if(this.getRoam() == null) this.setRoam(addAttributesFrom.getRoam());
        if(this.getLength() == null) this.setLength(addAttributesFrom.getLength());
        if(this.getEvent() == null) this.setEvent(addAttributesFrom.getEvent());
    }

    public ContentValues toDataRow() {
        ContentValues values = new ContentValues();

        values.put(GroupTable.COLUMN_ID, this.getId());
        values.put(GroupTable.COLUMN_KEY, this.getKey());
        if(this.name != null) values.put(GroupTable.COLUMN_NAME, this.name);
        if(this.event != null) values.put(GroupTable.COLUMN_EVENT, this.event);
        if(this.length != null) values.put(GroupTable.COLUMN_LENGTH, this.length);
        if(this.roam != null) values.put(GroupTable.COLUMN_ROAM, this.roam);

        return values;
    }

    public void fromCursor(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(GroupTable.COLUMN_ID));
        this.name = cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_NAME));
        this.event = (cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_EVENT)));
        this.length = (cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_LENGTH)));
        this.roam = (cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_ROAM)));
        this.key = (cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_KEY)));
    }

    @Override
    public String toString() {
        String group = "";
        group += "Group(" + this.id + ") : "
                + this.name + " - ";
//                "name = " + name +
//                "event = " + event +
//                "length = " + length +
//                "roam = " + roam +
//                "members( = " + members.size() + ")";
        return group;
    }

}
