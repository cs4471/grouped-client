package com.example.grouped;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
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
    	final Intent intent = new Intent(this, DisplayMessageActivity.class);
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if (!mBluetoothAdapter.isEnabled()) {
    			 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
    			 helpBuilder.setTitle("WHOA!");
    			 helpBuilder.setMessage("Looks like your Bluetooth isn't enabled." +
    			 						"\nWe need that on to connect your group." +
    			 						"\nCan we turn that on please?");
    			 helpBuilder.setPositiveButton("Ya, Sure",
    			   new DialogInterface.OnClickListener() {

    			    public void onClick(DialogInterface dialog, int which) {
    			    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    		    		mBluetoothAdapter.enable();
    		    		startActivity(intent);
    			    }
    			   });
    			  helpBuilder.setNegativeButton("No Way José", 
    				new DialogInterface.OnClickListener() {
    					  
    				  public void onClick(DialogInterface dialog, int which) {
    					  //Do nothing but close the popup
    				  }
    			  });

    			 // Remember, create doesn't show the dialog
    			 AlertDialog helpDialog = helpBuilder.create();
    			 helpDialog.show();
    	} else {
    		startActivity(intent);
    	}
    }
    	//Do something in response to button
}
    

