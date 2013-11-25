package com.example.grouped.models;

import android.content.Context;

import com.android.volley.Response;
import com.example.grouped.database.GroupedData;
import com.example.grouped.network.GroupedNetworkData;

/**
 * Created by Ethan on 11/17/13.
 */
/**public class DataHandler {

    private static DataHandler singleton = null;
    private GroupedData databaseHelper;
    private GroupedNetworkData networkHelper;


    private DataHandler(Context context) {
        GroupedData.getGroupedDataInstance(context);
        GroupedNetworkData.getGroupedDataInstance(context);
    }

    public void createGroup(Group group, final Response.Listener<Group> dhResponse) {
        networkHelper.createGroup(group, new Response.Listener<Group>() {
            @Override
            public void onResponse(Group group) {
                databaseHelper.storeGroup(group);
                dhResponse.onResponse(group);
            }
        });
    }

    public void joinGroup(Group group, final Response.Listener<Group> dhResponse) {
        networkHelper.joinGroup(group, new Response.Listener<Group>() {
            @Override
            public void onResponse(Group group) {
                databaseHelper.storeGroup(group);
                dhResponse.onResponse(group);
            }
        });
    }

    public void checkin(Member update, final Response.Listener<Integer> dhResponse) {
        databaseHelper.updateMember(update);
        networkHelper.updateMember(update, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer status) {
                dhResponse.onResponse(status);
            }
        });
    }



    public static DataHandler getDataHandler(Context context) {
        if(singleton == null) {
            singleton = new DataHandler(context);
        }
        return singleton;
    }
}*/
