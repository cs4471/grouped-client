package com.example.grouped;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;
import com.example.grouped.R;
import com.example.grouped.models.DataHandler;
import com.example.grouped.models.Group;
import com.example.grouped.tests.DataTester;

public class CreateGroup extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.grouped.MESSAGE";
	public int fieldsCompleted = 0;
    private Group group = new Group();
		
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
    
    public void launchConfirmationPage(View view) {
        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());
        dh.createGroup(group, new Response.Listener<Group>() {
            @Override
            public void onResponse(Group group) {
                Log.v("grouped CreatedTheGroup", group.toString());
                finishCreateGroup(group);
            }
        });
    }

    public void finishCreateGroup(Group group) {
        SharedPreferences prefs = this.getSharedPreferences("com.example.grouped", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putLong("group_id", group.getId());
        prefEditor.commit();

        Intent intent = new Intent(this, ConfirmationPageActivity.class);
        startActivity(intent);
    }


	public void onClick(View view){
		int id = view.getId();
		if (id == (R.id.NameButton)) {
			showSimplePopUp("Name", R.id.NameButton, R.id.NameButtonCheck);
		} else if (id == (R.id.EventButton)) {
			showSimplePopUp("Event", R.id.EventButton, R.id.EventButtonCheck);
		} else if (id == (R.id.TimeButton)) {
			showSimplePopUp("Time", R.id.TimeButton, R.id.TimeButtonCheck);
		} else if (id == (R.id.FenceButton)) {
			showSimplePopUp("Fence", R.id.FenceButton, R.id.FenceButtonCheck);
		} else if (id == (R.id.SumtinButton)) {
			showSimplePopUp("Sumtin", R.id.SumtinButton, R.id.SumtinButtonCheck);
		} else if (id == (R.id.ElseButton)) {
			showSimplePopUp("Event", R.id.ElseButton, R.id.ElseButtonCheck);
		}	
	}
	
	private void showSimplePopUp(String attr, final int buttonId, int checkId) {
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
				 fieldsCompleted++;
			 	Editable editable = nameInput.getText();
			 	String value = editable == null ? "": editable.toString();

                 if (buttonId == (R.id.NameButton)) {
                     group.setName(value);
                 } else if (buttonId == (R.id.EventButton)) {
                     group.setEvent(value);
                 } else if (buttonId == (R.id.TimeButton)) {
                     group.setLength(value);
                 } else if (buttonId == (R.id.FenceButton)) {
                     group.setRoam(value);
                 }


			 	Button nameButton = (Button) findViewById(button);
			 	ImageView nameCheck = (ImageView) findViewById(check);
			 	Drawable greenRing = getResources().getDrawable(R.drawable.attr_buttons_create_group_page_green);
			 	nameButton.setBackground(greenRing);
			 	nameButton.setOnClickListener(new OnClickListener() {		
			 		@Override
			 		public void onClick(View v) {
			 			//Do Nothing
			 		}
			 	});
			 	nameCheck.setVisibility(View.VISIBLE);
				nameButton.setText("");
				if (fieldsCompleted==6) {
			 						
				}
			 }	
		 });
		 AlertDialog helpDialog = helpBuilder.create();
		 helpDialog.show();
	}
}
