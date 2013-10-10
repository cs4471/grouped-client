package com.example.grouped;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DisplayMessageActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.grouped.MESSAGE";
		
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	public void onClick(View view){
		showSimplePopUp();
	}
	
	private void showSimplePopUp() {

		 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		 final EditText nameInput = new EditText(this);
		 
		 helpBuilder.setTitle("Group Name");
		 helpBuilder.setMessage("Enter a Name for your Group");
		 helpBuilder.setView(nameInput);
		 
		 helpBuilder.setNegativeButton("Mmm Nah",
				 new DialogInterface.OnClickListener() {
			 
			 public void onClick(DialogInterface dialog, int which) {
				 //Just close dialog box
			 }
		 });
		 helpBuilder.setPositiveButton("I Dig It",
		   new DialogInterface.OnClickListener() {

		    @SuppressLint("NewApi") public void onClick(DialogInterface dialog, int which) {
		     Editable editable = nameInput.getText();
		     String value = editable == null ? "": editable.toString();
		     
		     Button nameButton = (Button) findViewById(R.id.NameButton);
		     ImageView nameCheck = (ImageView) findViewById(R.id.NameButtonCheck);
		     Drawable greenRing = getResources().getDrawable(R.drawable.attr_buttons_create_group_page_green);
		     nameButton.setBackground(greenRing);
		     nameCheck.setVisibility(View.VISIBLE);
		     nameButton.setText("");
		    }
		 });

		 AlertDialog helpDialog = helpBuilder.create();
		 helpDialog.show();
		}
}
