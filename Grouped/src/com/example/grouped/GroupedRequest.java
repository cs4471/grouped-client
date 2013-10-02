package com.example.grouped;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.*;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sebastian on 10/2/13.
 */
public class GroupedRequest extends JsonObjectRequest {

    Map<String, String> params;

    public GroupedRequest(int method, String url, Map<String, String> params, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.params = params;
    }

    @Override
    public String getUrl() {
        String url = super.getUrl() + "?";

        for(String key : this.params.keySet()) {
            url += key + "=" + this.params.get(key) + "&";
        }

        return url;
    }
}
