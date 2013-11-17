package com.example.grouped.models;

import java.util.ArrayList;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Member {

    private long id;
    private String nickname;
    private float lat, lng, certainty;
    private int lastCheckin;
    private ArrayList<Message> messages;
    private boolean me;

    public Member() {

    }

    public long getId() {
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

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getCertainty() {
        return certainty;
    }

    public void setCertainty(float certainty) {
        this.certainty = certainty;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

}
