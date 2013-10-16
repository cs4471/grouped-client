package com.example.grouped;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.grouped.models.Group;
import com.example.grouped.database.GroupedData;
import com.example.grouped.network.GroupedRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastian on 10/1/13.
 */
public class CreateNewGroupActivity extends Activity {

    // used for logging
    private static final String TAG = "CreateNewGroupActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the parameters passed by the intent to this activity
        Intent caller = this.getIntent();
        Bundle extra = caller.getExtras();
        String groupName = (String)extra.get("group_name");

        // construct url to create a new group
        String url = R.string.base_url + "/groups/new";
        // create the needed params
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", groupName);

        // create a request queue to add our request to
        RequestQueue queue = Volley.newRequestQueue(this);

        // create the new group POST
        GroupedRequest jsObjRequest = new GroupedRequest(Request.Method.POST, url, params, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, response.toString());

                // convert the json into a group object
                Group test = new Gson().fromJson(response.toString(), Group.class);

                // set the converted group into the current group singleton instance
                Group.setGroupInstance(test);

                // open up the confirmation page activity
                callThis();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        // fire our request
        queue.add(jsObjRequest);
    }

    public void callThis() {
        // save the newly aquired group into the database
        GroupedData datasource = new GroupedData(this);
        datasource.open();
        datasource.storeGroup(Group.getInstance());
        datasource.close();

        Intent intent = new Intent(this, ConfirmationPageActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, "Created: " + group);
        startActivity(intent);
    }

}