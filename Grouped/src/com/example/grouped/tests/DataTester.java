package com.example.grouped.tests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.example.grouped.database.GroupedData;
import com.example.grouped.models.DataHandler;
import com.example.grouped.models.Group;
import com.example.grouped.models.Member;
import com.example.grouped.models.Message;
import com.example.grouped.network.GroupedNetworkData;

import java.util.List;

/**
 * Created by Sebastian on 11/21/13.
 */
public class DataTester {
    
    private static Context context;
    
    public static void runTests(Context context) {
        DataTester.context = context;

        //GroupedData.getGroupedDataInstance(context).clearDB();

        testDataHandler();
        //testGetGroups();

//        GroupedData data = GroupedData.getGroupedDataInstance(context);
//        Group group = data.getGroup((long)13);
//        testGetCheckins(group);

        //testMessages(data.getGroup((long)10));

        //testGroupedData();
        //testNetworkData();

        //testLeaveGroup();
    }

    public static void testGroupedData()
    {
        GroupedData gdInstance = GroupedData.getGroupedDataInstance(context);
        Group testGroup = new Group();
        testGroup.setName("TEST GROUP NAME");
        testGroup.setKey("SECRET KEY");
        gdInstance.storeGroup(testGroup);

        List<Group> allGroups = gdInstance.getGroups();
        Group testGroup2 = allGroups.remove(0);
        gdInstance.getGroup(testGroup2.getId());

        Member testMember = new Member();
        testMember.setMe(true);
        testMember.setNickname("Test nickname");
        gdInstance.updateMember(testMember);

        List<Member> allMembers = gdInstance.getMembers(testGroup);
        testMember.setStatus("Looking Good");
        testMember.setGroupID(testGroup.getId());

        gdInstance.updateMember(testMember);
        allMembers = gdInstance.getMembers(testGroup);
    }

    private static void testLeaveGroup(final Group group) {
        DataHandler dh = DataHandler.getDataHandler(context);
        dh.leaveGroup(group, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                Log.v("grouped LeaveGroup", group.toString());
            }
        });
    }

    private static void testGetGroups() {
        DataHandler dh = DataHandler.getDataHandler(context);
        dh.getGroups(new Response.Listener<List<Group>>() {
            @Override
            public void onResponse(List<Group> groups) {
                for(Group group : groups) {
                    Log.i("grouped", group.toString());
                    Log.i("grouped", group.getName());


                    //testLeaveGroup(group);
                    testGetCheckins(group);
                    //testMessages(group);
                }
            }
        });
    }

    public static void testDataHandler() {
        DataHandler dh = DataHandler.getDataHandler(context);
        final Group testGroup = new Group();
        testGroup.setName("TEST GROUP NAME");

        dh.createGroup(testGroup, new Response.Listener<Group>(){
            @Override
            public void onResponse(Group group) {
                Log.v("grouped DataHandler Create Group", group.toString());

                testJoinGroup(group);
            }
        });
    }

    public static void testJoinGroup(final Group group) {
        DataHandler dh = DataHandler.getDataHandler(context);
        dh.joinGroup(group, new Response.Listener<Member>() {
            @Override
            public void onResponse(Member member) {
                Log.v("grouped DataHandler Join Group", member.toString());

                testMemberUpdate(member);
                testMessages(group);
            }
        });
    }

    public static void testMemberUpdate(final Member member) {
        DataHandler dh = DataHandler.getDataHandler(context);

        member.setStatus("Updated");
        member.setCertainty("Pretty Certain");
        member.setNickname("Sebass");

        Log.v("grouped updateMember", member.toString());

        dh.checkin(member, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                if(integer >= 0) {
                    Log.v("grouped updateDMember", member.toString());
                } else {
                    Log.e("grouped DataHandler Update Member", "failed with error code " + integer);
                }
            }
        });
    }

    private static void testMessages(final Group group) {
        DataHandler dh = DataHandler.getDataHandler(context);

        Message message = new Message();
        message.setMessage("#2 from phone");

        dh.sendMessage(group, message, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                Log.v("grouped MessageSent", integer.toString());
            }
        });
        dh.getMessages(group, new Response.Listener<List<Message>>() {
            @Override
            public void onResponse(List<Message> messages) {
                for(Message message : messages) {
                    Log.v("grouped MessagesRecieved", message.getId() + ":" + message.getMessage());
                }
            }
        });

    }

    public static void testGetCheckins(Group group) {
        DataHandler dh = DataHandler.getDataHandler(context);

        dh.getCheckins(group, new Response.Listener<List<Member>>() {
            @Override
            public void onResponse(List<Member> members) {
                for(Member member : members) {
                    Log.v("grouped DataHandler Pulled Update", member.toString());
                    if(member.isMe()) {
                        testMemberUpdate(member);
                    }
                }
            }
        });
    }

    public static void testCreateGroup() {
        DataHandler dh = DataHandler.getDataHandler(context);
        final Group testGroup = new Group();
        testGroup.setName("TEST GROUP NAME");

        dh.createGroup(testGroup, new Response.Listener<Group>() {
            @Override
            public void onResponse(Group group) {
                Log.v("Grouped", group.toString());
                testJoinGroup(group);
            }
        });
    }

}
