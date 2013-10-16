package com.example.grouped;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.grouped.R;

public class StartUp extends Activity {

	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
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

    public void openStoredGroupsPage(View view) {
        Intent intent = new Intent(this, TestDatabaseActivity.class);
        startActivity(intent);
    }
    
}
    

