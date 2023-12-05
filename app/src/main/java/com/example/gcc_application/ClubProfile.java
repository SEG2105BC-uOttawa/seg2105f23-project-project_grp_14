package com.example.gcc_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ClubProfile extends AppCompatActivity {

    private String instagramLink;
    private String contactName;
    private String phoneNumber;
    private float averageRating;
    EditText rating, comment;
    List<Rating> ratings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_dialog);

        rating = findViewById(R.id.etRating);
        comment = findViewById(R.id.etComment);
    }

    // Constructor
    /*

    public ClubProfile(String instagramLink, String contactName, String phoneNumber) {
        this.instagramLink = instagramLink;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.ratings = new ArrayList<>();
        this.averageRating = 0.0f;
    }
    */

    // Method to add a rating and update the average rating
    public void addRating(Rating rating) {
        ratings.add(rating);
        updateAverageRating();
    }

    // Method to calculate the average rating
    private void updateAverageRating() {
        if (!ratings.isEmpty()) {
            float total = 0;
            for (Rating rating : ratings) {
                total += rating.getValue();
            }
            this.averageRating = total / ratings.size();
        }
    }

    // Getters and setters for existing fields
    public String getInstagramLink() { return instagramLink; }
    public void setInstagramLink(String instagramLink) { this.instagramLink = instagramLink; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    // Getters for the ratings
    public float getAverageRating() { return averageRating; }

    public void confirmBtn(View view) {
        String rated = rating.getText().toString().trim();
        String commented = comment.getText().toString().trim();

        Intent intent = getIntent();
        String clubName = intent.getStringExtra("clubName");


        if (commented.isEmpty() || commented.isEmpty()){
            Toast.makeText(ClubProfile.this, "Please fill a fields", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(rated) > 5 || (Integer.parseInt(rated) < 0)) {
            Toast.makeText(ClubProfile.this, "Invalid rating", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ClubProfile.this, "Review Registered", Toast.LENGTH_SHORT).show();
            Rating review = new Rating(Integer.parseInt(rated), commented, clubName);
            addRating(review);
        }

    }
