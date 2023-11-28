package com.example.gcc_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginClubMemberSuccess extends AppCompatActivity {

    TextView welcomeTextView;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_club_member_success);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String role = intent.getStringExtra("ROLE");

        welcomeTextView.setText("Welcome, " + username + "!\nRole: " + role);

    }

    public void profileBtn(View view){
        Intent myIntent = new Intent(LoginClubMemberSuccess.this, ClubProfileActivity.class);
        startActivity(myIntent);
    }

    public void eventBtn(View view){
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        Intent myIntent = new Intent(LoginClubMemberSuccess.this, AdminEventManagement.class);
        myIntent.putExtra("USERNAME", username);
        startActivity(myIntent);
    }
}