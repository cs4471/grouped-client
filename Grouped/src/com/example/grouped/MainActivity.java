package com.example.grouped;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Called when the Get GROUPED! button is pressed
    public void openEditGroupPage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if (!mBluetoothAdapter.isEnabled()) {
    		mBluetoothAdapter.enable();
    	}
    	startActivity(intent);
    	//Do something in response to button
    }
    
}
