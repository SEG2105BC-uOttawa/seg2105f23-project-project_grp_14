package com.example.gcc_application;

public class Rating {
    private int value; // Rating value from 1 to 5
    private String comment;
    private String username; // Identifier of the user who gave the rating

    // Constructor
    public Rating(int value, String comment, String username) {
        this.value = value;
        this.comment = comment;
        this.username = username;
    }

    // Getters
    public int getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public String getUsername() {
        return username;
    }

    // Setters
    public void setValue(int value) {
        this.value = value;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
