package com.example.grouped;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.example.grouped.models.DataHandler;
import com.example.grouped.models.Group;
import com.example.grouped.models.Member;

public class JoinGroup extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_group);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join_group, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void joinGroup(View view) {
		EditText idField = (EditText)findViewById(R.id.enter_group_id_field);
		EditText keyField = (EditText)findViewById(R.id.enter_group_key_field);
		final Group group = new Group();

        // here we accept a groups information to join
        // including key and name and id
        String id = idField.getText().toString();
		String key = keyField.getText().toString();
        String name = "This is a testGroup";

        group.setId(Long.parseLong(id));
        group.getKey();
        group.setName(name);

        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());
        dh.joinGroup(group, new Response.Listener<Member>() {
            @Override
            public void onResponse(Member member) {
                joinGroupSuccess(group);
            }
        });
	}

    public void joinGroupSuccess(Group group) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        final EditText nameInput = new EditText(this);

        helpBuilder.setTitle("Success!");
        helpBuilder.setMessage("Congrats, you just joined " + group.getName());
        helpBuilder.setView(nameInput);
        helpBuilder.setPositiveButton("Sweeet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });

        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

}
