package com.example.gcc_application;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button adminButton, clubMemberButton, participantButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameEditText = findViewById(R.id.registrationUsernameEditText);
        passwordEditText = findViewById(R.id.registrationPasswordEditText);
        adminButton = findViewById(R.id.adminButton);
        clubMemberButton = findViewById(R.id.clubMemberButton);
        participantButton = findViewById(R.id.participantButton);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser("Admin");
            }
        });

        clubMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser("Club member");
            }
        });

        participantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser("Participant");
            }
        });
    }

    private void registerUser(String role) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        DatabaseHelper db = new DatabaseHelper(this);

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = db.addUser(username, password, role);
        if (isInserted) {
            db.addUser(username, password, role);
            Toast.makeText(RegistrationActivity.this, "Registered successfully as " + role, Toast.LENGTH_SHORT).show();
            // Optionally, you can redirect the user back to the LoginActivity after successful registration
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(RegistrationActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
        }
    }

}
