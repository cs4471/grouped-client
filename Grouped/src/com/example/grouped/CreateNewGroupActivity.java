package com.example.grouped;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastian on 10/1/13.
 */
public class CreateNewGroupActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.grouped.MESSAGE";

    // used for logging
    private static final String TAG = "CreateNewGroupActivity";

    // the base url for our app
    private static final String baseUrl = "http://thimmig2-box-11673.use1.actionbox.io:3000";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // construct url to create a new group
        String url = baseUrl + "/groups/new";
        Log.i(TAG, "Creating Request to " + url);

        // create the needed params
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "TestGroupFromAndroid");

        // create a request queue to conduct our request
        RequestQueue queue = Volley.newRequestQueue(this);

        // create the new group POST
        GroupedRequest jsObjRequest = new GroupedRequest(Request.Method.POST, url, params, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    // try displaying the created group and id returned by server
                    callThis(params.get("name") + ":" + response.getString("group_id"));
                } catch (JSONException e) {

                }


                Log.i(TAG, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle any errors
            }
        });

        // fire our request
        queue.add(jsObjRequest);
    }

    public void callThis(String group) {
        Intent intent = new Intent(this, ConfirmationPageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Created: " + group);
        startActivity(intent);
    }

}