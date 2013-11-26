package com.example.grouped.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.grouped.database.GroupTable;
import com.example.grouped.network.Crypto;
import com.google.gson.annotations.Expose;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Group {

    private String key = null;
    private Crypto crypto;

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

    public Group() {
        try {
            crypto = new Crypto();
            this.getKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLength(String length) {
        try {
            this.length = crypto.encrypt(length);
        } catch (Exception e) {}
    }

    public void setRoam(String roam) {
        try {
            this.roam = crypto.encrypt(roam);
        } catch (Exception e) {}
    }

    public void setEvent(String event) {
        try {
            this.event = crypto.encrypt(event);
        } catch (Exception e) {}
    }

    public void setName(String name) {
        try {
            this.name = crypto.encrypt(name);
        } catch (Exception e) {}
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
        this.crypto.setKey(key);
    }

    public String getKey() {
        if(this.key == null) {
            try {
                this.key = this.crypto.generateKey();
                this.crypto.setKey(key);
            } catch (Exception e) {}
        }
        return this.key;
    }

    public String getName() {
        String name = null;
        try {
            name = crypto.decrypt(this.name);
        } catch (Exception e) {}
        return name;
    }

    public String getEvent() {
        String event = null;
        try {
            event = crypto.decrypt(this.event);
        } catch (Exception e) {}
        return event;
    }

    public String getLength() {
        String length = null;
        try {
            length = crypto.decrypt(this.length);
        } catch (Exception e) {}
        return length;
    }

    public String getRoam() {
        String roam = null;
        try {
            roam = crypto.decrypt(this.roam);
        } catch (Exception e) {}
        return roam;
    }

    public Long getId() {
        return id;
    }

    public ContentValues toDataRow() {
        ContentValues values = new ContentValues();

        values.put(GroupTable.COLUMN_ID, this.id);
        values.put(GroupTable.COLUMN_KEY, this.key);
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
        this.setKey((cursor.getString(cursor.getColumnIndex(GroupTable.COLUMN_KEY))));
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
