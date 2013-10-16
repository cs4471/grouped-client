package com.example.grouped;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.grouped.R;

public class OptionSelect extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_select);
		// Show the Up button in the action bar.
		
		final ImageView logo = (ImageView) findViewById(R.id.logo);
		final Animation animationRotateCenter = AnimationUtils.loadAnimation(this, R.anim.rotate_center);
		logo.startAnimation(animationRotateCenter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option_select, menu);
		return true;
	}
	
	public void onClick(View view) {
		final Intent intent = new Intent(this, CreateGroup.class);
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



}
