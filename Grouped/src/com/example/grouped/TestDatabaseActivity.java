package com.example.grouped;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.grouped.database.Group;
import com.example.grouped.database.GroupedData;

import java.util.List;

/**
 * Displays all groups curently stored in the database
 */
public class TestDatabaseActivity extends Activity {

    private GroupedData datasource;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_test);

        datasource = new GroupedData(this);
        datasource.open();
        List<Group> groups = datasource.getGroups();
        datasource.close();

        TextView list = (TextView) findViewById(R.id.DatabaseList);

        for(Group group : groups) {
            list.append("Group " + group.getId() + "\n\tName:" + group.getName() + "\n");
        }


    }


}