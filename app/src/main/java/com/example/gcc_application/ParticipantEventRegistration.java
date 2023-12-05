package com.example.gcc_application;

import static java.lang.Integer.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParticipantEventRegistration extends AppCompatActivity{
    EditText firstName, lastName, age, levelRequirement, paceRequirement;
    Button registerButton;
    EventDatabaseHelper database;
    TextView signUpText, eventDetails, eventRequirements;
    String requirements;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_event_registration);

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        levelRequirement = findViewById(R.id.levelRequirement);
        paceRequirement = findViewById(R.id.paceRequirement);
        registerButton = findViewById(R.id.register_button);
        signUpText = findViewById(R.id.signUp);
        eventDetails = findViewById(R.id.eventDetails);
        eventRequirements = findViewById(R.id.eventRequirements);

        database = new EventDatabaseHelper(this);

        eventDetails.setText(database.getEventDetails(eventName));
        eventRequirements.setText(database.getEventRequirements(eventName));

    }


    public void register_button_click(View view) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String eventName = intent.getStringExtra("eventName");
        String role = intent.getStringExtra("role");
        ArrayList<String> eventList = intent.getStringArrayListExtra("registeredEvents");

        String first_name = firstName.getText().toString().trim();
        String last_name = lastName.getText().toString().trim();
        String user_age = age.getText().toString().trim();
        String level_requirement = levelRequirement.getText().toString().trim();
        String pace_requirement = paceRequirement.getText().toString().trim();
        String requirements = eventRequirements.getText().toString().trim();

        int[] intRequirements = new int[3];
        int count = 0;

        // Define the pattern for matching digits in the input string
        Pattern pattern = Pattern.compile("\\d+");

        // Create a matcher to find the pattern in the input string
        Matcher matcher = pattern.matcher(requirements);

        // Iterate through the matches and add them to the list
        while (matcher.find()) {
            String match = matcher.group();
            // Convert the matched string to an integer and add it to the list
            intRequirements[count] = (Integer.parseInt(match));
            count++;
        }
        int age = intRequirements[0];
        int level = intRequirements[1];
        int pace = intRequirements[2];

        if (first_name.isEmpty() || last_name.isEmpty() || user_age.isEmpty() || level_requirement.isEmpty() || pace_requirement.isEmpty()) {
            Toast.makeText(ParticipantEventRegistration.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if ( parseInt(user_age) < age) {
            Toast.makeText(ParticipantEventRegistration.this, "Age Requirement not met", Toast.LENGTH_SHORT).show();
        } else if ( parseInt(level_requirement) < level) {
            Toast.makeText(ParticipantEventRegistration.this, "Level Requirement not met", Toast.LENGTH_SHORT).show();
        } else if ( parseInt(pace_requirement) < pace) {
            Toast.makeText(ParticipantEventRegistration.this, "Pace Requirement not met", Toast.LENGTH_SHORT).show();
        } else if(eventList == null){
            database.addParticipant(eventName, username);
            eventList.add(eventName);
            Toast.makeText(getApplicationContext(), eventName + " Event Joined", Toast.LENGTH_SHORT).show();

            Intent registerIntent = new Intent(ParticipantEventRegistration.this, LoginParticipantSuccess.class);
            registerIntent.putExtra("username", username);
            registerIntent.putExtra("role", role);
            startActivity(registerIntent);
            finish();
        } else if (eventList.contains(eventName)){
            Toast.makeText(ParticipantEventRegistration.this, "Already Registered for event", Toast.LENGTH_SHORT).show();
            Intent registerIntent = new Intent(ParticipantEventRegistration.this, LoginParticipantSuccess.class);
            registerIntent.putExtra("USERNAME", username);
            registerIntent.putExtra("ROLE", role);
            startActivity(registerIntent);
            finish();
        }else{
            database.addParticipant(eventName, username);
            eventList.add(eventName);
            Toast.makeText(getApplicationContext(), eventName + " Event Joined", Toast.LENGTH_SHORT).show();

            Intent registerIntent = new Intent(ParticipantEventRegistration.this, LoginParticipantSuccess.class);
            registerIntent.putExtra("username", username);
            registerIntent.putExtra("role", role);
            startActivity(registerIntent);
            finish();
        }
    }

}
