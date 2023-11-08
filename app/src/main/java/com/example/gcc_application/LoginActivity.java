package com.example.gcc_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegistrationActivity
                Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.validateUser(username, password)) {
                    // Successful login
                    String userRole = databaseHelper.getUserRole(username, password); // Fetch user role
                    Intent intent = new Intent();
                    if (databaseHelper.getUserRole(username, password).equals("admin")){
                         intent = new Intent(LoginActivity.this, LoginAdminSuccess.class);//change this to test admin
                    } else if (databaseHelper.getUserRole(username, password).equals("Club member")){
                         intent = new Intent(LoginActivity.this, LoginClubMemberSuccess.class);//change this to test admin
                    } else if (databaseHelper.getUserRole(username, password).equals("Participant")){
                         intent = new Intent(LoginActivity.this, LoginParticipantSuccess.class);//change this to test admin
                    }
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("ROLE", userRole); // Pass the role to WelcomeActivity
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
