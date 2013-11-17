package com.example.grouped;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.grouped.R;
import com.example.grouped.database.GroupedData;
import com.example.grouped.models.Group;
import com.example.grouped.models.Member;

import java.util.List;

public class StartUp extends Activity {

	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testGroupedData();
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
        gdInstance.open();
        gdInstance.storeGroup(testGroup);
        List<Group> allGroups = gdInstance.getGroups();
        Group testGroup2 = allGroups.remove(0);
        gdInstance.getGroup(testGroup2.getId());
        Member testMember = new Member();
        testMember.setMe(true);
        testMember.setNickname("Test nickname");
        List<Member> allMembers = gdInstance.getMembers(testGroup.getId());
        Member testMember = allMembers.remove(0);
        gdInstance.updateMember(testMember);
        gdInstance.deleteGroup(testGroup.getId());
        gdInstance.close();
    }
}
    

