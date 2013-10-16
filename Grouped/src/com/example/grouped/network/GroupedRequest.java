package com.example.grouped.network;

import com.android.volley.toolbox.*;
import com.android.volley.Response;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Map;

/**
 * A volley request customized to take parameters that it will insert into the url.
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
            url += key + "=" + URLEncoder.encode(this.params.get(key)) + "&";
        }

        return url;
    }
}
