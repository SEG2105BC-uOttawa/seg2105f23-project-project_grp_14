package com.example.gcc_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeTextView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String role = intent.getStringExtra("ROLE");

        welcomeTextView.setText("Welcome, " + username + "!\nRole: " + role);


    }
}
