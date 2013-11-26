package com.example.grouped;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.grouped.R;
import com.example.grouped.models.DataHandler;
import com.example.grouped.models.Group;

import java.util.List;

public class ConfirmationPageActivity extends Activity {
	
	@SuppressLint("NewApi")
	@Override	
	protected void onCreate(Bundle savedInstanceState) {		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmation_page);


        SharedPreferences prefs = this.getSharedPreferences("com.example.grouped", MODE_PRIVATE);
        final long groupId = prefs.getLong("group_id", 0);
        Log.v("Grouped Confirmation", groupId + "");

		final TextView displayId = (TextView)findViewById(R.id.display_id);
		final TextView displayKey = (TextView)findViewById(R.id.display_key);

        DataHandler dh = DataHandler.getDataHandler(getApplicationContext());
        dh.getGroups(new Response.Listener<List<Group>>() {
            @Override
            public void onResponse(List<Group> groups) {
                for(Group group : groups) {
                    if(group.getId() == groupId ) {
                        displayId.setText("" + group.getId());
                        displayKey.setText(group.getName());
                    }
                }
            }
        });

	}

	/**	 
	* Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override	
		public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmation_page, menu);
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
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}