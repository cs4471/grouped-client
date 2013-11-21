package com.example.grouped.network;

import com.android.volley.*;
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
    public void createGroup(Group newGroupInfo, final Response.Listener<Integer> response){
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
                Integer id = -1;
				try {
					id = serverResponse.getInt("id");
				} catch (JSONException e) {
					e.printStackTrace();
				}

                // call the listener passed with the integer id
                response.onResponse(id);

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
            	if (serverResponse.has("id")){
            		Integer memberID = -1;
					try {
						memberID = serverResponse.getInt("id");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    // call the listener passed with the integer id
                    response.onResponse(memberID);
            	}
            	else {
            		response.onResponse(ERROR);
            	}

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

    public void destroyGroup(Group groupToDestroy, final Response.Listener<Integer> response){
        String url = BASEURL + "/groups/delete";
        JSONObject params = null;

        try {
            // turn the group info provided into a json object
            params = new JSONObject(new Gson().toJson(groupToDestroy));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
            	if (serverResponse.has("id")){
            		Integer groupID = -1;
					try {
						groupID = serverResponse.getInt("id");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    // call the listener passed with the integer id
                    response.onResponse(groupID);
            	}
            	else {
            		response.onResponse(ERROR);
            	}

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

    public void checkin(Group groupToDestroy, final Response.Listener<Integer> response){
        String url = BASEURL + "/checkins/new";
        JSONObject params = null;

        try {
            // turn the group info provided into a json object
            params = new JSONObject(new Gson().toJson(groupToDestroy));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
            	if (serverResponse.has("id")){
            		Integer memberID = - 1;
					try {
						memberID = serverResponse.getInt("id");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    // call the listener passed with the integer id
                    response.onResponse(memberID);
            	}
            	else {
            		response.onResponse(ERROR);
            	}

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

	public void checkinsGet(Group groupToCheckinWith, Member member, final Response.Listener<List<Member>> response){
	    String url = BASEURL + "/checkins/get";
	    JSONObject params = null;
	
	    // turn the group info provided into a json object
		Map<String, Long> checkinsGetArgs = new HashMap<String, Long>();
		checkinsGetArgs.put("group_id", groupToCheckinWith.getId());
		checkinsGetArgs.put("checkin_id", (long) member.getLastCheckin());
		params = new JSONObject(checkinsGetArgs);
	
	    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
	        @Override
	        public void onResponse(JSONObject serverResponse) {
	        	List<Member> membersList = new ArrayList<Member>();
	        	if (serverResponse.has("checkins")){
	        		Member[] members = new Gson().fromJson(serverResponse.toString(), Member[].class);
	        		membersList.addAll(Arrays.asList(members));
	
	                // call the listener passed with the integer id
	                response.onResponse(membersList);
	        	}
	        	else {
	        		response.onResponse(null);
	        	}
	
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
	
	public void newMessage(Message messageToSend, 
							final Response.Listener<Integer> response){
	    String url = BASEURL + "/messages/new";
	    JSONObject params = null;
	
	    try {
	        // turn the message provided into a json object
	    	params = new JSONObject(new Gson().toJson(messageToSend));
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	
	    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
	        @Override
	        public void onResponse(JSONObject serverResponse) {
	        	// check for error
            	if (serverResponse.has("id")){
            		Integer messageID = -1;
					try {
						messageID = serverResponse.getInt("id");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    // call the listener passed with the integer id
                    response.onResponse(messageID);
            	}
            	else {
            		response.onResponse(ERROR);
            	}
	
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
	
	public void getMessages(Group group, Message lastMessage,
			final Response.Listener<List<Message>> response){
		String url = BASEURL + "/messages/get";
		JSONObject params = null;
		
		Map<String, Long> getMessageArgs = new HashMap<String, Long>();
		getMessageArgs.put("group_id", group.getId());
		// TODO: checkinsGetArgs.put("message_id", message.getLastID());
		
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject serverResponse) {
				// check for error
				List<Message> messagesList = new ArrayList<Message>();

				if (serverResponse.has("checkins")){
	        		Message[] messages = new Gson().fromJson(serverResponse.toString(), Message[].class);
	        		messagesList.addAll(Arrays.asList(messages));
	
	                // call the listener passed with the integer id
	                response.onResponse(messagesList);
	        	}
	        	else {
	        		response.onResponse(null);
	        	}
			
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
}
