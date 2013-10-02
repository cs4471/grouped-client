package com.example.grouped;

import java.util.ArrayList;

/**
 * Created by Sebastian on 10/2/13.
 */
public class Group {

    private String key;
    private String name;
    private int id;
    private ArrayList<Member> members;

    public Group() {

    }

    public String getKey() {
        return key;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
