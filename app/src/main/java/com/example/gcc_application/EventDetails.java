package com.example.gcc_application;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetails extends AppCompatActivity{
    TextView EventDetails, EventRequirements;
    EventDatabaseHelper database;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        database = new EventDatabaseHelper(this);

        Intent intent = getIntent();
        eventName = intent.getStringExtra("eventName");

        EventDetails = findViewById(R.id.EventDetails);
        EventRequirements = findViewById(R.id.EventRequirements);

        EventDetails.setText(database.getEventDetails(eventName));
        EventRequirements.setText(database.getEventRequirements(eventName));

    }


}


/*package com.example.gcc_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity{

    TextView eventDetails, EventRequirements;
    EventDatabaseHelper database;
    String eventName;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent intent = getIntent();
        eventName = intent.getStringExtra("eventName");

        database = new EventDatabaseHelper(this);

        eventDetails = findViewById(R.id.eventDetails);
        EventRequirements = findViewById(R.id.EventRequirements);

        eventDetails.setText(database.getEventDetails(eventName));
        EventRequirements.setText(database.getEventRequirements(eventName));

    }

}

 */
