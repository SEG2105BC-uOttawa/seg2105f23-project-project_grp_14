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

public class AdminAccountManagement extends AppCompatActivity{
    private ArrayAdapter<String> adapterClub, adapterParticipant;
    private ArrayList<String> participantList, clubList;
    DatabaseHelper database;
    ListView listOfParticipants, listOfClubs;
    static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_management);

        listOfClubs = findViewById(R.id.listOfClubs);
        listOfParticipants = findViewById(R.id.listOfParticipants);

        clubList = new ArrayList<>();
        participantList = new ArrayList<>();

        database = new DatabaseHelper(this);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        clubList = dbHelper.getUsers("Club member");
        participantList = dbHelper.getUsers("Participant");

        adapterClub = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clubList);
        adapterParticipant = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, participantList);

        listOfClubs.setAdapter(adapterClub);
        listOfParticipants.setAdapter(adapterParticipant);

        listOfClubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = clubList.get(position);
                showAlertDialog(selectedItem);
            }
        });

        listOfParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = participantList.get(position);
                showAlertDialog(selectedItem);
            }
        });
    }

    private void showAlertDialog(String selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAccountManagement.this);
        builder.setTitle(selectedItem);
        //builder.setMessage("Type of event:");
        builder.setPositiveButton("Delete User", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle "Edit Event" button click
                user = selectedItem;
                database.deleteUser(user);
                clubList.remove(user);
                participantList.remove(user);
                Toast.makeText(getApplicationContext(), user + " deleted!", Toast.LENGTH_SHORT).show();
                listOfClubs.setAdapter(adapterClub);
                listOfParticipants.setAdapter(adapterParticipant);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
