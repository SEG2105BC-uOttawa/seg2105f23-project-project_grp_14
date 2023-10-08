package com.example.gcc_application;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
        if (db.addUser("admin", "admin", "admin")) {
            Log.d("Database", "Admin account added successfully");
        } else {
            Log.d("Database", "Error adding admin account");
        }

    }
}