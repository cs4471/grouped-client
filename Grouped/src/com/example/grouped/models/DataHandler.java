package com.example.grouped.models;

import android.content.Context;

import com.android.volley.Response;
import com.example.grouped.database.GroupedData;
import com.example.grouped.network.GroupedNetworkData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 11/17/13.
 */
public class DataHandler {

    private static DataHandler singleton = null;
    private GroupedData databaseHelper;
    private GroupedNetworkData networkHelper;

    private DataHandler(Context context) {
        databaseHelper = GroupedData.getGroupedDataInstance(context);
        networkHelper = GroupedNetworkData.getGroupedDataInstance(context);
    }

    public void getGroups(final Response.Listener<List<Group>> dhResponse) {
        dhResponse.onResponse(databaseHelper.getGroups());
    }

    public void createGroup(final Group group, final Response.Listener<Group> dhResponse) {
        networkHelper.createGroup(group, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer groupId) {
                group.setId((long) groupId);
                databaseHelper.storeGroup(group);
                dhResponse.onResponse(group);
            }

        });
    }

    public void joinGroup(final Group group, final Response.Listener<Member> dhResponse) {
        networkHelper.joinGroup(group, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer memberId) {
                Member member = new Member();
                member.setId((long)memberId);
                member.setGroupID((long) group.getId());
                member.setMe(true);
                dhResponse.onResponse(member);
                databaseHelper.updateMember(member);
            }
        });
    }

    public void leaveGroup(Group group, final Response.Listener<Integer> dhResponse) {
        networkHelper.leaveGroup(group, databaseHelper.getMe(), new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                dhResponse.onResponse(response);
            }
        });
        databaseHelper.deleteGroup(group.getId());
    }

    public void checkin(final Member me, final Response.Listener<Integer> dhResponse) {
        networkHelper.sendCheckin(me, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                databaseHelper.updateMember(me);
                dhResponse.onResponse(integer);
            }
        });
    }

    public void getCheckins(final Group group, final Response.Listener<List<Member>> dhResponse) {
        List<Member> members = databaseHelper.getMembers(group);
        dhResponse.onResponse(members);

        int lastCheckin = -1;
        for(Member member : members) {
            lastCheckin = Math.max(member.getLastCheckin(), lastCheckin);
        }

        networkHelper.getCheckins(group, lastCheckin, new Response.Listener<List<Member>>() {
            @Override
            public void onResponse(List<Member> members) {
                for (Member member : members) {
                    member.setGroupID(group.getId());
                    databaseHelper.updateMember(member);
                }
                dhResponse.onResponse(members);
            }
        });
    }

    public void sendMessage(final Message message, final Response.Listener<Integer> dhResponse) {
        message.setMemberId(databaseHelper.getMe().getId());
        networkHelper.sendMessage(message, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                List<Message> messageList = new ArrayList();
                message.setId(integer.longValue());
                messageList.add(message);
                databaseHelper.addMessages(messageList);
            }
        });
    }

    public void getMessages(Group group, final Response.Listener<List<Message>> dhResponse) {
        List<Message> messages = databaseHelper.getMessages(group);
        dhResponse.onResponse(messages);

        int lastMessage = -1;
        for(Message message : messages) {
            lastMessage = Math.max((int)message.getId(), lastMessage);
        }

        networkHelper.getMessages(group, lastMessage, new Response.Listener<List<Message>>() {
            @Override
            public void onResponse(List<Message> messages) {
                databaseHelper.addMessages(messages);
                dhResponse.onResponse(messages);
            }
        });
    }

    public static DataHandler getDataHandler(Context context) {
        if(singleton == null) {
            singleton = new DataHandler(context);
        }
        return singleton;
    }
}
