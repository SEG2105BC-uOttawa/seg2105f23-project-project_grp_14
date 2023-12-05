package com.example.gcc_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginParticipantSuccess extends AppCompatActivity {

    TextView welcomeTextView;
    DatabaseHelper databaseHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> eventList;
    ListView listOfEnteredEvents;
    EventDatabaseHelper database;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_participant_success);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String role = intent.getStringExtra("ROLE");

        welcomeTextView.setText("Welcome, " + username + "!\nRole: " + role);

        database = new EventDatabaseHelper(this);

        listOfEnteredEvents = findViewById(R.id.listOfEnteredEvents);

        // Create an ArrayList to store your data
        eventList = new ArrayList<>();

        EventDatabaseHelper dbHelper = new EventDatabaseHelper(this);

        eventList = dbHelper.getAllRecords("participant", username);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);

        // Set the adapter for the ListView
        listOfEnteredEvents.setAdapter(adapter);

        listOfEnteredEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item's value and pass it to the showAlertDialog method
                String selectedItem = eventList.get(position);
                showAlertDialog(selectedItem);
            }
        });
    }

    public void searchEventBtn(View view) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String role = intent.getStringExtra("ROLE");

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginParticipantSuccess.this);
        builder.setTitle("Event Search");
        builder.setMessage("Search by:");

        builder.setPositiveButton("Event Type", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent registerIntent = new Intent(LoginParticipantSuccess.this, EventSearch.class);
                registerIntent.putExtra("search by", "event_type");
                registerIntent.putExtra("username", username);
                registerIntent.putExtra("role", role);
                registerIntent.putExtra("eventList", eventList);
                startActivity(registerIntent);
            }
        });


        builder.setNegativeButton("Event name", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent registerIntent = new Intent(LoginParticipantSuccess.this, EventSearch.class);
                registerIntent.putExtra("search by", "event_name");
                registerIntent.putExtra("username", username);
                registerIntent.putExtra("role", role);
                registerIntent.putExtra("eventList", eventList);
                startActivity(registerIntent);
            }
        });

        builder.setNeutralButton("Club Name", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent registerIntent = new Intent(LoginParticipantSuccess.this, EventSearch.class);
                registerIntent.putExtra("search by", "club_name");
                registerIntent.putExtra("username", username);
                registerIntent.putExtra("role", role);
                registerIntent.putExtra("eventList", eventList);
                startActivity(registerIntent);
            }
        });

        builder.create().show();
    }

    public void searchClubBtn(View view) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String role = intent.getStringExtra("ROLE");

        Intent registerIntent = new Intent(LoginParticipantSuccess.this, EventSearch.class);
        registerIntent.putExtra("search by", "club");
        registerIntent.putExtra("username", username);
        registerIntent.putExtra("role", role);
        startActivity(registerIntent);
    }

    private void showAlertDialog(String selectedItem) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginParticipantSuccess.this);
        builder.setTitle(selectedItem);
        //builder.setMessage("Type of event:");
        builder.setPositiveButton("Event Info", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent registerIntent = new Intent(LoginParticipantSuccess.this, EventDetails.class);
                startActivity(registerIntent);

            }
        });

        builder.setNeutralButton("Rate Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(), selectedItem + " deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Leave Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.deleteParticipant(selectedItem, username);
                eventList.remove(selectedItem);
                Toast.makeText(getApplicationContext(), selectedItem + " Left Event", Toast.LENGTH_SHORT).show();
                listOfEnteredEvents.setAdapter(adapter);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
