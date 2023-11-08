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
import android.widget.Toast;

public class AdminEventManagement extends AppCompatActivity {

    static String event_details;
    static String event_type;
    static String event_name;//used for editing events
    boolean editEvent = false;
    ListView listOfEvents;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> eventList;
    EventDatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_management);

        editEvent = false;

        database = new EventDatabaseHelper(this);

        listOfEvents = findViewById(R.id.listOfEvents);

        // Create an ArrayList to store your data
        eventList = new ArrayList<>();

        EventDatabaseHelper dbHelper = new EventDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        eventList = dbHelper.getAllRecords();

        // Create an ArrayAdapter to bind the ArrayList to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);

        // Set the adapter for the ListView
        listOfEvents.setAdapter(adapter);

        listOfEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item's value and pass it to the showAlertDialog method
                String selectedItem = eventList.get(position);
                showAlertDialog(selectedItem);
                event_type = database.getEventType(selectedItem);
            }
        });
    }

    public void addEventBtn(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminEventManagement.this);
        builder.setTitle("Add Event");
        builder.setMessage("Type of event:");

        builder.setPositiveButton("Time Trial", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Start EventRegistrationActivity

                event_details = "Time trials, often referred to as \"TTs,\" are individual races against\n" +
                        "the clock. Cyclists start at intervals and race alone to complete a set course as\n" +
                        "quickly as possible. It's a test of a rider's ability to maintain a consistent pace and\n" +
                        "maximize speed";


                event_type = "Time Trial";
                Intent registerIntent = new Intent(AdminEventManagement.this, EventRegistration.class);
                registerIntent.putExtra("event type", "Time Trial");
                startActivity(registerIntent);
            }
        });


        builder.setNegativeButton("Road Stage Race", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Handle Road Stage Race button click

                event_details = "Road stage races are multi-day events composed of multiple\n" +
                        "stages, each with its own route and terrain. Cyclists compete over several days,\n" +
                        "and the overall winner is determined by the lowest cumulative time across all\n" +
                        "stages. Events like the Tour de France are classic examples";


                event_type = "Road Stage Race";
                Intent registerIntent = new Intent(AdminEventManagement.this, EventRegistration.class);
                registerIntent.putExtra("event type", "Road Stage Race");
                startActivity(registerIntent);
            }
        });

        builder.setNeutralButton("Road Race", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Handle Road Race button click

                event_details = "Road races are competitive cycling events held on paved roads.\n" +
                        "Cyclists race in a group, and the winner is typically determined by the first rider\n" +
                        "to cross the finish line. Road races vary in distance and can be one-day events or\n" +
                        "part of a stage race";


                event_type = "Road Race";
                Intent registerIntent = new Intent(AdminEventManagement.this, EventRegistration.class);
                registerIntent.putExtra("event type", "Road Race");
                startActivity(registerIntent);
            }
        });

        builder.create().show();
    }



    public static String getEventDetails(){
        return event_details;
    }

    public static String getEventType(){
        return event_type;
    }

    private void showAlertDialog(String selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminEventManagement.this);
        builder.setTitle(selectedItem);
        //builder.setMessage("Type of event:");
        builder.setPositiveButton("Edit Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle "Edit Event" button click
                event_name = selectedItem;
                editEvent = true;
                Intent registerIntent = new Intent(AdminEventManagement.this, EventRegistration.class);
                registerIntent.putExtra("editing event", editEvent);
                registerIntent.putExtra("event type", database.getEventType(event_name));
                startActivity(registerIntent);

            }
        });
        builder.setNegativeButton("Delete Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle "Delete Event" button click
                database.deleteEvent(selectedItem);
                eventList.remove(selectedItem);
                Toast.makeText(getApplicationContext(), selectedItem + " deleted!", Toast.LENGTH_SHORT).show();
                listOfEvents.setAdapter(adapter);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}


