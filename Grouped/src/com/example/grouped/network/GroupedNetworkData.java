package com.example.grouped.network;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.grouped.models.Group;
import com.example.grouped.models.Member;
import com.example.grouped.models.Message;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class used to connect and communicate with the grouped api on the grouped server.
 */
public class GroupedNetworkData {
	private static Integer ERROR = -1;
    private static GroupedNetworkData instance = null;
    private static final String BASEURL = "http://thimmig2-box-11673.use1.actionbox.io";

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
    public void createGroup(final Group newGroupInfo, final Response.Listener<Integer> response){
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
                // integer id is guaranteed

                try {
                    if(serverResponse.has("id")) {
                        response.onResponse(serverResponse.getInt("id"));
                    } else if(serverResponse.has("error")) {
                        response.onResponse(serverResponse.getInt("error"));
                    }
                } catch (Exception e) {}
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


    public void joinGroup(Group groupToJoin, final Response.Listener<Integer> response) {
        String url = BASEURL + "/groups/join";
        JSONObject params = null;

        try {
            // turn the group info provided into a json object
            params = new JSONObject(new Gson().toJson(groupToJoin));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
            	// test for error
            	try {
                    if (serverResponse.has("id")){
                        response.onResponse(serverResponse.getInt("id"));
                    } else if(serverResponse.has("error")) {
                        response.onResponse(serverResponse.getInt("error"));
                    }
            	} catch (Exception e) {}

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

    public void leaveGroup(Group groupToLeave, Member me, final Response.Listener<Integer> response){
        String url = BASEURL + "/groups/leave";
        JSONObject params = new JSONObject();

        try {
            params.put("group_id", groupToLeave.getId());
            params.put("member_id", me.getId());
        } catch(Exception e) {}

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
            	try{
                    if (serverResponse.has("id")){
                        response.onResponse(serverResponse.getInt("id"));
                    }
                    else {
                        response.onResponse(serverResponse.getInt("error"));
                    }
                } catch(Exception e){}

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

    public void sendCheckin(Member me, final Response.Listener<Integer> response){
        String url = BASEURL + "/checkins/new";
        JSONObject params = null;

        try {
            // turn the group info provided into a json object
            params = new JSONObject(new Gson().toJson(me));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
            	try {
                    if(serverResponse.has("id")) {
                        response.onResponse(serverResponse.getInt("id"));
                    } else if(serverResponse.has("error")) {
                        response.onResponse(serverResponse.getInt("error"));
                    }
            	} catch (Exception e) {}
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

	public void getCheckins(Group group, Integer lastCheckin, final Response.Listener<List<Member>> response){
	    String url = BASEURL + "/checkins/get";
		Map<String, Integer> checkinsGetArgs = new HashMap();
		checkinsGetArgs.put("group_id", group.getId().intValue());
		checkinsGetArgs.put("checkin_id", lastCheckin);
        url += toUrlParams(checkinsGetArgs);
	
	    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
	        @Override
	        public void onResponse(JSONObject serverResponse) {
                List<Member> membersList = new ArrayList<Member>();
                Member[] members = new Member[0];
                try {
                    Log.v("grouped network", serverResponse.getJSONArray("checkins").toString());
                    members = new Gson().fromJson(serverResponse.getJSONArray("checkins").toString(), Member[].class);
                } catch (Exception e) {}

                membersList.addAll(Arrays.asList(members));

                // call the listener passed with the integer id
                response.onResponse(membersList);
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
	
	public void sendMessage(Message messageToSend,
                            final Response.Listener<Integer> response){
	    String url = BASEURL + "/messages/new";
	    JSONObject params = new JSONObject();
	
	    try {
	        params.put("id", messageToSend.getMemberId());
            params.put("message", messageToSend.getMessage());
	    } catch (Exception e) { }
	
	    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
	        @Override
	        public void onResponse(JSONObject serverResponse) {
                try {
                    // check for error
                    if (serverResponse.has("id")){
                        response.onResponse(serverResponse.getInt("id"));
                    } else {
                        response.onResponse(serverResponse.getInt("error"));
                    }
                } catch (Exception e) {}
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
	
	public void getMessages(Group group, Integer lastMessage,
			final Response.Listener<List<Message>> response){

        String url = BASEURL + "/messages/get";
        Map<String, Integer> checkinsGetArgs = new HashMap();
        checkinsGetArgs.put("group_id", group.getId().intValue());
        checkinsGetArgs.put("message_id", lastMessage);
        url += toUrlParams(checkinsGetArgs);
		
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject serverResponse) {
				List<Message> messageList = new ArrayList();
                Message[] messages = new Message[0];
                try {
                    Log.v("grouped network", serverResponse.getJSONArray("messages").toString());
                    messages = new Gson().fromJson(serverResponse.getJSONArray("messages").toString(), Message[].class);
                } catch (Exception e) {}

                messageList.addAll(Arrays.asList(messages));

                // call the listener passed with the integer id
                response.onResponse(messageList);
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

    private String toUrlParams(Map<String, Integer> params) {
        String paramString = "?";
        for(Map.Entry<String, Integer> param : params.entrySet()) {
            paramString += URLEncoder.encode(param.getKey()) + "=" + URLEncoder.encode(param.getValue().toString()) + "&";
        }
        return paramString;
    }
}
