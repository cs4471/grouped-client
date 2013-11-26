package com.example.grouped.models;

import android.content.Context;
import android.util.Log;

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
        if(databaseHelper.getGroup(group.getId()) == null) {
            // if the group is not created yet in the database
            databaseHelper.storeGroup(group);
        }
        networkHelper.joinGroup(group, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer memberId) {
                Member member = new Member();
                member.setId((long)memberId);
                member.setGroupID((long) group.getId());
                member.setMe(true);
                dhResponse.onResponse(member);
                databaseHelper.updateMember(member.encrypt(group.getKey()));
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
        String groupKey = databaseHelper.getGroup(me.getGroupID()).getKey();
        final Member encrypted = me.encrypt(groupKey);
        networkHelper.sendCheckin(encrypted, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                databaseHelper.updateMember(encrypted);
                dhResponse.onResponse(integer);
            }
        });
    }

    public void getCheckins(final Group group, final Response.Listener<List<Member>> dhResponse) {
        List<Member> encryptedMembers = databaseHelper.getMembers(group);
        List<Member> decryptedMembers = new ArrayList();

        int lastCheckin = -1;
        for(Member member : encryptedMembers) {
            decryptedMembers.add(member.decrypt(group.getKey()));
            lastCheckin = Math.max(member.getLastCheckin(), lastCheckin);
        }
        dhResponse.onResponse(decryptedMembers);

        networkHelper.getCheckins(group, lastCheckin, new Response.Listener<List<Member>>() {
            @Override
            public void onResponse(List<Member> members) {
                List<Member> decrypted = new ArrayList<Member>();
                for (Member member : members) {
                    member.setGroupID(group.getId());
                    databaseHelper.updateMember(member);
                    decrypted.add(member.decrypt(group.getKey()));
                }
                dhResponse.onResponse(decrypted);
            }
        });
    }

    public void sendMessage(final Group group, final Message message, final Response.Listener<Integer> dhResponse) {
        message.setMemberId(databaseHelper.getMe().getId());
        networkHelper.sendMessage(message.encrypt(group.getKey()), new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                List<Message> messageList = new ArrayList();
                message.setId(integer.longValue());
                messageList.add(message);
                databaseHelper.addMessages(messageList);
                dhResponse.onResponse(integer);
            }
        });
    }

    public void getMessages(final Group group, final Response.Listener<List<Message>> dhResponse) {
        List<Message> encrypted = databaseHelper.getMessages(group);
        List<Message> decrypted = new ArrayList();

        int lastMessage = -1;
        for(Message message : encrypted) {
            decrypted.add(message.decrypt(group.getKey()));
            lastMessage = Math.max((int)message.getId(), lastMessage);
        }
        // respond with database while we wait for network
        dhResponse.onResponse(decrypted);

        networkHelper.getMessages(group, lastMessage, new Response.Listener<List<Message>>() {
            @Override
            public void onResponse(List<Message> messages) {
                List<Message> decrypted = new ArrayList<Message>();
                for(Message message : messages) {
                    decrypted.add(message.decrypt(group.getKey()));
                }
                dhResponse.onResponse(messages);
                databaseHelper.addMessages(messages);
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
