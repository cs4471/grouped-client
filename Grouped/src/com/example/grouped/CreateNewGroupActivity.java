package com.example.grouped;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.example.grouped.models.Group;
import com.example.grouped.database.GroupedData;
import com.example.grouped.network.GroupedNetworkData;

/**
 * An example activity that creates a new group on the server, adds the response
 *  to the database, and then calls another activity to display it.
 */
public class CreateNewGroupActivity extends Activity {

    // used for logging
    private static final String TAG = "CreateNewGroupActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create the needed params
        final Group newGroupInfo = new Group();
        newGroupInfo.setName("TestGroupFromPhone");

        GroupedNetworkData networkData = GroupedNetworkData.getGroupedDataInstance(getApplicationContext());
        final GroupedData datasource = GroupedData.getGroupedDataInstance(getApplicationContext());

        networkData.createGroup(newGroupInfo, new Response.Listener<Group>() {
            @Override
            public void onResponse(Group group) {
                group.merge(newGroupInfo);

                // save the newly aquired group into the database
                datasource.open();
                datasource.storeGroup(group);
                datasource.close();

                Intent intent = new Intent(getApplicationContext(), TestDatabaseActivity.class);
                startActivity(intent);
            }
        });

    }

}