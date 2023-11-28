package com.example.gcc_application;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ClubProfileActivity extends AppCompatActivity {
    private EditText instagramEditText, contactNameEditText, contactPhoneEditText;
    private Button saveProfileButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_profile);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Bind the EditText and Button views
        instagramEditText = findViewById(R.id.instagramEditText);
        contactNameEditText = findViewById(R.id.contactNameEditText);
        contactPhoneEditText = findViewById(R.id.contactPhoneEditText);
        saveProfileButton = findViewById(R.id.saveProfileButton);

        // Load existing profile data if available
        loadProfile();

        // Set up the button click listener for saving the profile
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void loadProfile() {
        ClubProfile profile = databaseHelper.getClubProfile();
        if (profile != null) {
            instagramEditText.setText(profile.getInstagramLink());
            contactNameEditText.setText(profile.getContactName());
            contactPhoneEditText.setText(profile.getPhoneNumber());
        }
    }

    private void saveProfile() {
        String instagramLink = instagramEditText.getText().toString().trim();
        String contactName = contactNameEditText.getText().toString().trim();
        String phoneNumber = contactPhoneEditText.getText().toString().trim();

        boolean isValid = true;

        if (instagramLink.isEmpty()) {
            instagramEditText.setError("Instagram link is mandatory.");
            isValid = false;
        } else if (!isValidInstagramUrl(instagramLink)) {
            instagramEditText.setError("Invalid Instagram URL.");
            isValid = false;
        }

        // Separate validation for phone number
        if (phoneNumber.isEmpty()) {
            contactPhoneEditText.setError("Phone number is mandatory.");
            isValid = false;
        } else if (!isValidPhoneNumber(phoneNumber)) {
            isValid = false;
        }

        // If validation passes, proceed to save the profile
        if (isValid) {
            ClubProfile profile = new ClubProfile(instagramLink, contactName, phoneNumber);
            databaseHelper.saveClubProfile(profile, ClubProfileActivity.this);
        }
    }

    // Method to validate the Instagram URL
    private boolean isValidInstagramUrl(String url) {
        // Add your Instagram URL validation logic here
        // Simple pattern check (you should improve this with a proper regex)
        return url.contains(".instagram.com/");
    }

    // Method to validate the phone number format
    private boolean isValidPhoneNumber(String number) {
        // Remove non-digit characters for counting digits
        String digits = number.replaceAll("\\D+", "");
        boolean isValid = digits.length() >= 10;

        if (!isValid) {
            contactPhoneEditText.setError("Phone number must be at least 10 digits.");
        }

        return isValid;
    }
}