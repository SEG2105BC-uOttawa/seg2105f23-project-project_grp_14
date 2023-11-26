package com.example.gcc_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class LoginClubMemberSuccess extends AppCompatActivity {
    private EditText instagramLinkEditText;
    private EditText mainContactNameEditText;
    private EditText phoneNumberEditText;
    private EditText addressEditText;
    private DatabaseHelper databaseHelper;
    private MultiAutoCompleteTextView eventMultiAutoCompleteTextView;
    private String clubId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_club_member_success);

        // Initialize UI components
        instagramLinkEditText = findViewById(R.id.instagramLinkEditText);
        mainContactNameEditText = findViewById(R.id.mainContactNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        addressEditText = findViewById(R.id.addressEditText);
        eventMultiAutoCompleteTextView = findViewById(R.id.eventMultiAutoCompleteTextView);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
    }

    public void updateProfileBtnClick(View view){
        String instagramLink = instagramLinkEditText.getText().toString().trim();
        String mainContactName = mainContactNameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(instagramLink) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address)) {
            showToast("Please fill all mandatory fields.");
            return;
        }

        // Additional validation for phone number and other fields
        if (!isValidPhoneNumber(phoneNumber)) {
            showToast("Invalid phone number.");
            return;
        }

        // Perform other validations as needed
        if (databaseHelper.updateClubProfile(clubId, instagramLink, mainContactName, phoneNumber, address)) {
            showToast("Club profile updated successfully.");
        } else {
            showToast("Error updating club profile.");
        }
    }

    public void associateEventsBtnClick(View view) {
        List<String> selectedEvents = getSelectedEvents();
        if (selectedEvents.isEmpty()) {
            showToast("Select at least one event.");
            return;
        }

        // Associate selected events with the club
        if (databaseHelper.associateEventsWithClub(clubId, selectedEvents)) {
            showToast("Events associated successfully.");
        } else {
            showToast("Error associating events.");
        }
    }

    public void deleteEventBtnClick(View view) {
        // Implement logic to get the event ID to delete
        String eventId = "";

        // Delete the event from the club's profile
        if (databaseHelper.deleteEventFromClub(clubId, eventId)) {
            showToast("Event deleted successfully.");
        } else {
            showToast("Error deleting event.");
        }
    }

    public void processServiceRequestBtnClick(View view) {
        // Implement logic to get the service request ID and approval status
        String requestId = "";
        boolean isApproved = true;

        // Update the status of the service request
        if (databaseHelper.updateServiceRequestStatus(requestId, isApproved)) {
            showToast(isApproved ? "Service request approved." : "Service request rejected.");
        } else {
            showToast("Error processing service request.");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    private List<String> getSelectedEvents() {
        List<String> selectedEvents = new ArrayList<>();

        // Get the selected events from the MultiAutoCompleteTextView
        String selectedEventsText = eventMultiAutoCompleteTextView.getText().toString().trim();

        // Split the selected events text into individual events (assuming they are separated by commas)
        String[] eventsArray = selectedEventsText.split(",");

        // Trim and add each event to the list
        for (String event : eventsArray) {
            selectedEvents.add(event.trim());
        }

        return selectedEvents;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}