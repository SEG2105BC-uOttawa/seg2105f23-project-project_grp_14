package com.example.gcc_application;

public class ClubProfile {
    private String instagramLink;
    private String contactName;
    private String phoneNumber;

    // Constructor
    public ClubProfile(String instagramLink, String contactName, String phoneNumber) {
        this.instagramLink = instagramLink;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters
    public String getInstagramLink() { return instagramLink; }
    public String getContactName() { return contactName; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setInstagramLink(String instagramLink) { this.instagramLink = instagramLink; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}