package com.example.grouped.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Group {

    @Expose
    private String key;

    @Expose
    private String name;

    @Expose
    private long id;

    private ArrayList<Member> members;

    // singleton instance
    private static Group groupInstance = null;


    public static void setGroupInstance(Group groupInstance) {
        Group.groupInstance = groupInstance;
    }

    public static Group getInstance() {
        return Group.groupInstance;
    }

    public String getKey() {
        // temporary key placeholder
        return "Changeee";
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



}
