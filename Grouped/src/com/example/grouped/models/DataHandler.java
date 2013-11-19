package com.example.grouped.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.example.grouped.database.GroupedData;
import com.example.grouped.network.GroupedNetworkData;

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

    public void checkin(final Member me, final Response.Listener<Integer> dhResponse) {
        databaseHelper.updateMember(me);
        networkHelper.checkin(me, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                dhResponse.onResponse(integer);
            }
        });
    }

    public void getCheckins(final Group group, final Response.Listener<List<Member>> dhResponse) {
        List<Member> members = databaseHelper.getMembers(group.getId());
        dhResponse.onResponse(members);

        int lastCheckin = -1;
        long me = 0;
        for(Member member : members) {
            lastCheckin = Math.max(member.getLastCheckin(), lastCheckin);
            if(member.isMe()) {
                me = member.getId();
            }
        }

        final long meId = me;

        networkHelper.checkinsGet(group, lastCheckin, new Response.Listener<List<Member>>() {
            @Override
            public void onResponse(List<Member> members) {
                dhResponse.onResponse(members);

                for(Member member : members) {
                    if(member.getId() != meId) {
                        member.setGroupID(group.getId());
                        databaseHelper.updateMember(member);
                    }
                }
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
