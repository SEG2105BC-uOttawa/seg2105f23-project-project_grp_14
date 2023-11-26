package com.example.gcc_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginAdminSuccess extends AppCompatActivity {

    TextView welcomeTextView;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin_success);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String role = intent.getStringExtra("ROLE");

        welcomeTextView.setText("Welcome, " + username + "!\nRole: " + role);

    }

    public void eventManagementBtnClick(View view){
        Intent myIntent = new Intent(LoginAdminSuccess.this, AdminEventManagement.class);
        startActivity(myIntent);
    }

    public void accountManagementBtnClick(View view){
        Intent myIntent = new Intent(LoginAdminSuccess.this, AdminAccountManagement.class);
        startActivity(myIntent);
    }

    public void clubManagementBtnClick(View view){
        Intent myIntent = new Intent(LoginAdminSuccess.this, LoginClubMemberSuccess.class);
        startActivity(myIntent);
    }
}
