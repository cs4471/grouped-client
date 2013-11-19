package com.example.grouped;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.android.volley.Response;
import com.example.grouped.R;
import com.example.grouped.database.GroupedData;
import com.example.grouped.models.DataHandler;
import com.example.grouped.models.Group;
import com.example.grouped.models.Member;
import com.example.grouped.network.GroupedNetworkData;

import java.util.List;

public class StartUp extends Activity {

	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        //testGroupedData();
        //testNetworkData();
        //testDataHandler();
        //testGetCheckins(new Group());
    }

	protected void onResume(Bundle savedInstanceState) {
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.startup, menu);
        return true;
    }

    //Called when the Get GROUPED! button is pressed
    public void openEditGroupPage(View view) {
    	final Intent intent = new Intent(this, OptionSelect.class);
    		startActivity(intent);
    }

    public void testGroupedData()
    {
        GroupedData gdInstance = GroupedData.getGroupedDataInstance(getApplicationContext());
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

        List<Member> allMembers = gdInstance.getMembers(testGroup.getId());
        testMember.setStatus("Looking Good");
        testMember.setGroupID(testGroup.getId());

        gdInstance.updateMember(testMember);
        allMembers = gdInstance.getMembers(testGroup.getId());
    }

    public void testDataHandler() {
        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());
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

    public void testGetCheckins(Group group) {
        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());
        group.setId((long)1);
        dh.getCheckins(group, new Response.Listener<List<Member>>() {
            @Override
            public void onResponse(List<Member> members) {
                for(Member member : members) {
                    Log.v("grouped DataHandler Pulled Update", member.toString());
                }
            }
        });
    }

    public void testCreateGroup() {
        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());
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

    public void testJoinGroup(Group group) {
        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());
        dh.joinGroup(group, new Response.Listener<Member>() {
            @Override
            public void onResponse(Member member) {
                Log.v("grouped DataHandler Join Group", member.toString());
                testMemberUpdate(member);
            }
        });
    }

    public void testMemberUpdate(final Member member) {
        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());

        member.setStatus("Updated");
        member.setCertainty("Pretty Certain");

        dh.checkin(member, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                if(integer >= 0) {
                    Log.v("grouped DataHandler Update Member", member.toString());
                } else {
                    Log.e("grouped DataHandler Update Member", "failed with error code " + integer);
                }
            }
        });
    }

}
    

