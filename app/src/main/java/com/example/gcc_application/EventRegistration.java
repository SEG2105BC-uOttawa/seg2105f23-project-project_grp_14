package com.example.gcc_application;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EventRegistration extends AppCompatActivity {

    EditText eventName, eventDate, eventRoute, ageRequirement, levelRequirement, paceRequirement;
   TextView eventDetails;
    Button register_button;
    EventDatabaseHelper database;

    String defaultEventName, defaultEventDate, defaultEventRoute, defaultAgeRequirement, defaultLevelRequirement, defaultPaceRequirement, event_type;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);

        eventName = findViewById(R.id.eventName);
        eventDate = findViewById(R.id.eventDate);
        eventRoute = findViewById(R.id.eventRoute);
        ageRequirement = findViewById(R.id.ageRequirement);
        levelRequirement = findViewById(R.id.levelRequirement);
        paceRequirement = findViewById(R.id.paceRequirement);
        register_button = findViewById(R.id.register_button);
        eventDetails = findViewById(R.id.EventDetails);

        database = new EventDatabaseHelper(this);

        Intent intent = getIntent();
        boolean editingEvent = intent.getBooleanExtra("editing event", false);
        String eventType = intent.getStringExtra("event type");

        switch (eventType){
            case ("Time Trial"):
                eventDetails.setText(eventDetails.getText()+ "Time trials, often referred to as TTs, are individual races against\n"
                        +"the clock. Cyclists start at intervals and race alone to complete a set course as\n"+
                        "quickly as possible. It's a test of a rider's ability to maintain a consistent pace and\n" +
                        "maximize speed");
                break;
            case ("Road Stage Race"):
                eventDetails.setText(eventDetails.getText()+ "Road stage races are multi-day events composed of multiple\n" +
                        "stages, each with its own route and terrain. Cyclists compete over several days,\n" +
                        "and the overall winner is determined by the lowest cumulative time across all\n" +
                        "stages. Events like the Tour de France are classic examples");
                break;
            case("Road Race"):
                eventDetails.setText(eventDetails.getText()+ "Road races are competitive cycling events held on paved roads.\n" +
                        "Cyclists race in a group, and the winner is typically determined by the first rider\n" +
                        "to cross the finish line. Road races vary in distance and can be one-day events or\n" +
                        "part of a stage race");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + AdminEventManagement.event_type);
        }

        if (editingEvent){//if we're coming to this activity to edit an event
            String details = database.getEventDetails(AdminEventManagement.event_name);
            String requirements = database.getEventRequirements(AdminEventManagement.event_name);

            // Define regular expressions to match Description, Date, and Route
            Pattern datePattern = Pattern.compile("Date: (.+?)\\n");
            Pattern routePattern = Pattern.compile("Route: (.+)$");
            Pattern ageRequirementPattern = Pattern.compile("Age requirement: (.+?)\\n");
            Pattern levelRequirementPattern = Pattern.compile("Level requirement: (.+?)\\n");
            Pattern paceRequirementPattern = Pattern.compile("Pace Requirement: (.+)$");

            // Create Matcher objects for each pattern
            Matcher dateMatcher = datePattern.matcher(details);
            Matcher routeMatcher = routePattern.matcher(details);
            Matcher ageRequirementMatcher = ageRequirementPattern.matcher(requirements);
            Matcher levelRequirementMatcher = levelRequirementPattern.matcher(requirements);
            Matcher paceRequirementMatcher = paceRequirementPattern.matcher(requirements);

            // Extract and print the matched values
            if (dateMatcher.find()) {
                String event_date = dateMatcher.group(1);
                eventDate.setText(event_date);
            }

            if (routeMatcher.find()) {
                String event_route = routeMatcher.group(1);
                eventRoute.setText(event_route);
            }

            if (ageRequirementMatcher.find()) {
                String age_requirement = ageRequirementMatcher.group(1);
                ageRequirement.setText(age_requirement);
            }

            if (levelRequirementMatcher.find()) {
                String level_requirement = levelRequirementMatcher.group(1);
                levelRequirement.setText(level_requirement);
            }

            if (paceRequirementMatcher.find()) {
                String pace_requirement = paceRequirementMatcher.group(1);
                paceRequirement.setText(pace_requirement);
            }

            eventName.setText(AdminEventManagement.event_name);

            defaultEventName = eventName.getText().toString().trim();
            defaultEventDate = eventDate.getText().toString().trim();
            defaultEventRoute = eventRoute.getText().toString().trim();
            defaultAgeRequirement = ageRequirement.getText().toString().trim();
            defaultLevelRequirement = levelRequirement.getText().toString().trim();
            defaultPaceRequirement = paceRequirement.getText().toString().trim();
        }


        register_button.setOnClickListener(v -> {
            String event_name = eventName.getText().toString().trim();
            String event_date = eventDate.getText().toString().trim();
            String event_route = eventRoute.getText().toString().trim();
            String age_requirement = ageRequirement.getText().toString().trim();
            String level_requirement = levelRequirement.getText().toString().trim();
            String pace_requirement = paceRequirement.getText().toString().trim();

            String event_details = "Description: " + AdminEventManagement.getEventDetails() + "\nDate: " + event_date + "\nRoute: " + event_route;
            String event_requirements = "Age requirement: " + age_requirement + "\nLevel requirement: " + level_requirement + "\nPace Requirement: " + pace_requirement;

            if (event_name.isEmpty() || event_route.isEmpty() || event_date.isEmpty() || age_requirement.isEmpty() || level_requirement.isEmpty() || pace_requirement.isEmpty()) {
                Toast.makeText(EventRegistration.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            } else if (database.eventNameExists(event_name)) {
                Toast.makeText(EventRegistration.this, "Event name already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editingEvent) {

                AlertDialog.Builder typeChange = new AlertDialog.Builder(EventRegistration.this);
                typeChange.setTitle("New Event Type");
                typeChange.setMessage("Select type of event:");

                typeChange.setPositiveButton("Time Trial", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Start EventRegistrationActivity

                        String eventDetails = "Time trials, often referred to as \"TTs,\" are individual races against\n" +
                                "the clock. Cyclists start at intervals and race alone to complete a set course as\n" +
                                "quickly as possible. It's a test of a rider's ability to maintain a consistent pace and\n" +
                                "maximize speed";

                        database.editEvent(defaultEventName, event_name, "Time Trial", event_details, event_requirements);

                        Toast.makeText(EventRegistration.this, "Event updated successfully", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(EventRegistration.this, AdminEventManagement.class);
                        startActivity(registerIntent);
                        finish();                    }
                });


                typeChange.setNegativeButton("Road Stage Race", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle Road Stage Race button click

                        String eventDetails = "Road stage races are multi-day events composed of multiple\n" +
                                "stages, each with its own route and terrain. Cyclists compete over several days,\n" +
                                "and the overall winner is determined by the lowest cumulative time across all\n" +
                                "stages. Events like the Tour de France are classic examples";

                        database.editEvent(defaultEventName, event_name, "Road Stage Race", event_details, event_requirements);

                        Toast.makeText(EventRegistration.this, "Event updated successfully", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(EventRegistration.this, AdminEventManagement.class);
                        startActivity(registerIntent);
                        finish();                    }
                });

                typeChange.setNeutralButton("Road Race", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle Road Race button click

                        String eventDetails = "Road races are competitive cycling events held on paved roads.\n" +
                                "Cyclists race in a group, and the winner is typically determined by the first rider\n" +
                                "to cross the finish line. Road races vary in distance and can be one-day events or\n" +
                                "part of a stage race";

                        database.editEvent(defaultEventName, event_name, "Road Race", event_details, event_requirements);

                        Toast.makeText(EventRegistration.this, "Event updated successfully", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(EventRegistration.this, AdminEventManagement.class);
                        startActivity(registerIntent);
                        finish();

                    }
                });

                typeChange.create().show();


            } else {
                boolean isInserted = database.addEvent(event_name, AdminEventManagement.getEventType(), event_details, event_requirements);
                if (isInserted) {
                    Toast.makeText(EventRegistration.this, "Event registered successfully", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(EventRegistration.this, AdminEventManagement.class);
                    startActivity(registerIntent);
                    finish();
                } else {
                    Toast.makeText(EventRegistration.this, "Registration error", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
