package com.example.grouped.network;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.grouped.models.Group;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class used to connnect and communicate with the grouped api on the grouped server.
 */
public class GroupedNetworkData {

    private static GroupedNetworkData instance = null;
    private static final String BASEURL = "http://thimmig2-box-11673.use1.actionbox.io:3000";

    private static RequestQueue queue;

    private GroupedNetworkData(Context context) {
        GroupedNetworkData.queue = Volley.newRequestQueue(context);
    }

    public static GroupedNetworkData getGroupedDataInstance(Context context) {
        if(GroupedNetworkData.instance == null) {
            GroupedNetworkData.instance = new GroupedNetworkData(context);
        }
        return GroupedNetworkData.instance;
    }

    /*
        Used to create or update a group located on the server.
        Params :
            Group : info used to create the group
                if group has an id this funtion will attempt to update a group with the same id on the server
                can be passed a group with no values filled in
            Response.Listener<Group> : will be called with the group after it is recieved from server
                use this to update database or view
     */
    public void createGroup(Group newGroupInfo, final Response.Listener<Group> response){
        String url = BASEURL + "/groups/new";
        JSONObject params = null;

        try {
            // turn the group info provided into a json object
            params = new JSONObject(new Gson().toJson(newGroupInfo));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
                // parse the parameters returned into a Group.java object
                Group test = new Gson().fromJson(serverResponse.toString(), Group.class);

                // call the listener passed with the constructed object
                response.onResponse(test);

                Log.i("Network Grouped Data", serverResponse.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Network Grouped Data", error.getMessage().toString());
            }
        });

        // fire our request
        GroupedNetworkData.queue.add(jsObjRequest);
    }


    public void joinGroup(Group groupToJoin, final Response.Listener<Boolean> response) {
        String url = BASEURL + "/groups/join";
    }

    public void destroyGroup(Group groupToDestroy, final Response.Listener<Boolean> response){
        String url = BASEURL + "/groups/delete";
    }

}
