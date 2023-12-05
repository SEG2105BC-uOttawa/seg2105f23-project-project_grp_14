package com.example.gcc_application;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EventSearch extends AppCompatActivity {

    ListView searchResults;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> eventList;
    EventDatabaseHelper database;
    DatabaseHelper db;
    TextView textView;
    EditText searchQuery;



    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);

        Intent intent = getIntent();
        String searchField = intent.getStringExtra("search by");

        database = new EventDatabaseHelper(this);
        db = new DatabaseHelper(this);

        textView = findViewById(R.id.textView);

        if (searchField.equals("event_name")) {
            textView.setText("Search By Event Name");
        } else if (searchField.equals("event_type")) {
            textView.setText("Search By Event Type");
        } else if (searchField.equals("event_registree")){
            textView.setText("Search By Club");
        } else{
            textView.setText("Search for Club");
        }

    }

    public void searchBtn(View view) {
        searchResults = findViewById(R.id.searchResults);
        searchQuery = findViewById(R.id.searchQueryText);
        String query = searchQuery.getText().toString().trim();

        Intent intent = getIntent();
        String searchField = intent.getStringExtra("search by");

        // Create an ArrayList to store your data
        eventList = new ArrayList<>();

        EventDatabaseHelper eventdbHelper = new EventDatabaseHelper(this);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (searchField.equals("event_name")) {
            eventList = eventdbHelper.getAllRecords("event_name", query);
        } else if (searchField.equals("event_type")) {
            eventList = eventdbHelper.getAllRecords("event_type", query);
        } else if (searchField.equals("club_name")) {
            eventList = eventdbHelper.getAllRecords("event_registree", query);
        } else{
            eventList = dbHelper.getAllRecords("main_contact_name", query);
        }

        // Create an ArrayAdapter to bind the ArrayList to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);

        // Set the adapter for the ListView
        searchResults.setAdapter(adapter);

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item's value and pass it to the showAlertDialog method
                String selectedItem = eventList.get(position);
                if (searchField.equals("event_name") || searchField.equals("event_type") || searchField.equals("event_registree")){
                    showAlertDialog(selectedItem);
                }else{
                    showAlertDialogClub(selectedItem);
                }
            }
        });
    }

    private void showAlertDialog(String selectedItem) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String role = intent.getStringExtra("role");
        ArrayList<String> list = intent.getStringArrayListExtra("registeredEvents");

        AlertDialog.Builder builder = new AlertDialog.Builder(EventSearch.this);
        builder.setTitle(selectedItem);
        builder.setPositiveButton("Join Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent registerIntent = new Intent(EventSearch.this, ParticipantEventRegistration.class);
                registerIntent.putExtra("eventName", selectedItem);
                registerIntent.putExtra("username", username);
                registerIntent.putExtra("role", role);
                registerIntent.putExtra("registeredEvents", list);
                startActivity(registerIntent);
                finish();
            }
        });

        builder.setNegativeButton("Event Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent registerIntent = new Intent(EventSearch.this, EventDetails.class);
                registerIntent.putExtra("username", username);
                registerIntent.putExtra("role", role);
                registerIntent.putExtra("eventName", selectedItem);
                startActivity(registerIntent);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void showAlertDialogClub(String selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EventSearch.this);
        builder.setTitle(selectedItem);
        builder.setPositiveButton("Rate Club", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent registerIntent = new Intent(EventSearch.this, ClubProfile.class);
                registerIntent.putExtra("clubName", selectedItem);
                startActivity(registerIntent);
                finish();
            }
        });

        builder.create().show();
    }
}
