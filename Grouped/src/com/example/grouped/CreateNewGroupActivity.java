package com.example.grouped;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Sebastian on 10/1/13.
 */
public class CreateNewGroupActivity extends Activity {

    private RequestQueue queue = Volley.newRequestQueue(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String url = "grouped-server.com";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // do something with server response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle any errors
            }
        });

        queue.add(jsObjRequest);
    }
}