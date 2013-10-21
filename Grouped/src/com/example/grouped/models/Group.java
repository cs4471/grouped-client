package com.example.grouped.models;

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
    private Integer length = null;

    @Expose
    private Integer roam = null;

    @Expose
    private Long id = null;

    private ArrayList<Member> members = null;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getRoam() {
        return roam;
    }

    public void setRoam(Integer roam) {
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

}
