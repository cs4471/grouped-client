package com.example.grouped.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.grouped.database.GroupTable;
import com.example.grouped.database.MemberTable;
import com.example.grouped.database.MessageTable;
import com.example.grouped.network.Crypto;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Message {

    public long id;
    public long memberId;
    public String message;
    public String timeStamp;

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Message encrypt(String key) {
        Crypto crypto = null;
        Message encrypted = new Message();
        try {
            crypto = new Crypto();
            crypto.setKey(key);
            encrypted.setId(this.id);
            encrypted.setMemberId(this.memberId);

            encrypted.setMessage(crypto.encrypt(this.message));

        } catch (Exception e) {}
        return encrypted;
    }

    public Message decrypt(String key) {
        Crypto crypto = null;
        Message decrypted = new Message();
        try {
            crypto = new Crypto();
            crypto.setKey(key);
            decrypted.setId(this.id);
            decrypted.setMemberId(this.memberId);

            decrypted.setMessage(crypto.decrypt(this.message));

        } catch (Exception e) {}
        return decrypted;
    }

    public void fromCursor(Cursor cursor) {
        this.id = (cursor.getLong(cursor.getColumnIndex(MessageTable.COLUMN_ID)));
        this.memberId = (cursor.getLong(cursor.getColumnIndex(MessageTable.COLUMN_MEMBER_ID)));
        this.message = (cursor.getString(cursor.getColumnIndex(MessageTable.COLUMN_MESSAGE)));
        this.timeStamp = (cursor.getString(cursor.getColumnIndex(MessageTable.COLUMN_TIMESTAMP)));
    }

    public ContentValues toDataRow() {
        ContentValues values = new ContentValues();

        values.put(MessageTable.COLUMN_ID, this.getId());
        values.put(MessageTable.COLUMN_MEMBER_ID, this.getMemberId());
        values.put(MessageTable.COLUMN_MESSAGE, this.message);

        return values;
    }
}
