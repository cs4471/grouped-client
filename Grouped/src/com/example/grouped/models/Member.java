package com.example.grouped.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.grouped.database.MemberTable;
import com.example.grouped.network.Crypto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Member {

    @Expose
    private long id;

    @Expose
    private String nickname;

    @Expose
    private String status;

    @Expose
    private String lat;

    @Expose
    private String lng;

    @Expose
    private String certainty;

    @Expose
    private Integer checkin_id = null;

    private boolean me;
    private long groupID;

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
        return checkin_id;
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

    public void setCheckin_id(int checkin_id) {
        this.checkin_id = checkin_id;
    }

    public Member encrypt(String key) {
        Crypto crypto = null;
        Member encrypted = new Member();
        try {
            crypto = new Crypto();
            crypto.setKey(key);
            encrypted.setId(this.id);
            encrypted.setGroupID(this.groupID);
            encrypted.setMe(this.me);
            encrypted.setCheckin_id(this.checkin_id);

            encrypted.setNickname(crypto.encrypt(this.nickname));
            encrypted.setStatus(crypto.encrypt(this.status));
            encrypted.setLat(crypto.encrypt(this.lat));
            encrypted.setLng(crypto.encrypt(this.lng));
            encrypted.setCertainty(crypto.encrypt(this.certainty));

        } catch (Exception e) {}
        return encrypted;
    }

    public Member decrypt(String key) {
        Crypto crypto = null;
        Member decrypted = new Member();
        try {
            crypto = new Crypto();
            crypto.setKey(key);
            decrypted.setId(this.id);
            decrypted.setGroupID(this.groupID);
            decrypted.setMe(this.me);
            decrypted.setCheckin_id(this.checkin_id);

            decrypted.setNickname(crypto.decrypt(this.nickname));
            decrypted.setStatus(crypto.decrypt(this.status));
            decrypted.setLat(crypto.decrypt(this.lat));
            decrypted.setLng(crypto.decrypt(this.lng));
            decrypted.setCertainty(crypto.decrypt(this.certainty));

        } catch (Exception e) {}
        return decrypted;
    }

    public ContentValues toDataRow() {
        ContentValues values = new ContentValues();

        values.put(MemberTable.COLUMN_ID, this.id);
        values.put(MemberTable.COLUMN_GROUPID, this.groupID);
        values.put(MemberTable.COLUMN_ME, (this.me ? 1 : 0));
        if(this.checkin_id != null) values.put(MemberTable.COLUMN_CHECKIN_ID, this.checkin_id);
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
        this.checkin_id = (cursor.getInt(cursor.getColumnIndex(MemberTable.COLUMN_CHECKIN_ID)));
    }

    @Override
    public String toString() {
        String member = "";

        member += "Member(" +
                new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this, Member.class);

        return member;
    }
}
