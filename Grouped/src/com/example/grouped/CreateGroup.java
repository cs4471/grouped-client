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

public class CreateGroup extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.grouped.MESSAGE";
		
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_group, menu);
        return true;
    }


	public void onClick(View view){
		switch(view.getId()) {
		case(R.id.NameButton):
			showSimplePopUp("Name", R.id.NameButton, R.id.NameButtonCheck);
		break;
		case(R.id.EventButton):
			showSimplePopUp("Event", R.id.EventButton, R.id.EventButtonCheck);
		break;
		case(R.id.TimeButton):
			showSimplePopUp("Time", R.id.TimeButton, R.id.TimeButtonCheck);
		break;
		case(R.id.FenceButton):
			showSimplePopUp("Fence", R.id.FenceButton, R.id.FenceButtonCheck);
		break;
		case(R.id.SumtinButton):
			showSimplePopUp("Sumtin", R.id.SumtinButton, R.id.SumtinButtonCheck);
		break;
		case(R.id.ElseButton):
			showSimplePopUp("Event", R.id.ElseButton, R.id.ElseButtonCheck);
		break;
		}	
	}
	
	private void showSimplePopUp(String attr, int buttonId, int checkId) {
		final int button = buttonId;
		final int check = checkId;

		 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		 final EditText nameInput = new EditText(this);
		 
		 helpBuilder.setTitle(attr);
		 helpBuilder.setMessage("Enter a(n) " + attr + " for your Group");
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
		     //Here is where youd get the field. value holds the string content
		     Button nameButton = (Button) findViewById(button);
		     ImageView nameCheck = (ImageView) findViewById(check);
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
