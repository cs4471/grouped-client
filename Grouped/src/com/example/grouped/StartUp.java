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
import com.example.grouped.tests.DataTester;

import java.util.List;

public class StartUp extends Activity {

	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataTester.runTests(getApplicationContext());
    }

    protected void onResume(Bundle savedInstanceState) {}

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

}
    

