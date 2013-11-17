package com.example.grouped.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.grouped.database.MemberTable;

import java.util.ArrayList;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Member {

    private long id, groupID;
    private String nickname;
    private String status;
    private String lat, lng, certainty;
    private int lastCheckin;
    private ArrayList<Message> messages;
    private boolean me;

    public Member() {

    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLastCheckin() {
        return lastCheckin;
    }

    public void setLastCheckin(int lastCheckin) {
        this.lastCheckin = lastCheckin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCertainty() {
        return certainty;
    }

    public void setCertainty(String certainty) {
        this.certainty = certainty;
    }

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ContentValues toDataRow() {
        ContentValues values = new ContentValues();

        values.put(MemberTable.COLUMN_ID, this.id);
        values.put(MemberTable.COLUMN_GROUPID, this.groupID);
        values.put(MemberTable.COLUMN_ME, this.me);
        if(this.nickname != null) values.put(MemberTable.COLUMN_NICKNAME, this.nickname);
        if(this.status != null) values.put(MemberTable.COLUMN_STATUS, this.status);
        if(this.lat != null) values.put(MemberTable.COLUMN_LAT, this.lat);
        if(this.lng != null) values.put(MemberTable.COLUMN_LNG, this.lng);
        if(this.certainty != null) values.put(MemberTable.COLUMN_CERTAINTY, this.certainty);

        return values;
    }

    public void fromCursor(Cursor cursor) {
        this.id = (cursor.getLong(cursor.getColumnIndex(MemberTable.COLUMN_ID)));
        this.groupID = (cursor.getLong(cursor.getColumnIndex(MemberTable.COLUMN_GROUPID)));
        this.nickname = (cursor.getString(cursor.getColumnIndex(MemberTable.COLUMN_NICKNAME)));
        this.status = (cursor.getString(cursor.getColumnIndex(MemberTable.COLUMN_STATUS)));
        this.lat = (cursor.getString(cursor.getColumnIndex(MemberTable.COLUMN_LAT)));
        this.lng = (cursor.getString(cursor.getColumnIndex(MemberTable.COLUMN_LNG)));
        this.certainty = (cursor.getString(cursor.getColumnIndex(MemberTable.COLUMN_CERTAINTY)));
        this.me = (cursor.getInt(cursor.getColumnIndex(MemberTable.COLUMN_ME))) == 0 ? false : true;
    }
}
