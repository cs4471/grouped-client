package com.example.grouped.network;

import com.example.grouped.models.Group;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Sebastian on 10/21/13.
 */
public class GsonTester {

    public static void main(String[] args){
        Group testGroup = new Group();
        testGroup.setName("testName");
        String tester = new Gson().toJson(testGroup);
        System.out.println(tester);
    }


}
