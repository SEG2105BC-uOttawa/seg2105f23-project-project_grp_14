package com.example.gcc_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }
}
