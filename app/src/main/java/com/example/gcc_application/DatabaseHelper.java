package com.example.gcc_application;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userdatabase";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String TABLE_CLUB_EVENT = "club_event";
    private static final String COLUMN_CLUB_EVENT_ID = "club_event_id";
    private static final String COLUMN_CLUB_ID_FK = "club_id";
    private static final String COLUMN_EVENT_ID_FK = "event_id";
    private static final String TABLE_CLUB = "clubs";
    private static final String COLUMN_CLUB_ID = "club_id";
    private static final String COLUMN_INSTAGRAM_LINK = "instagram_link";
    private static final String COLUMN_MAIN_CONTACT_NAME = "main_contact_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_ADDRESS = "address";

    private static final String TABLE_EVENT = "events";
    private static final String COLUMN_EVENT_ID = "id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_TYPE = "event_type";
    private static final String COLUMN_DETAILS = "event_details";
    private static final String COLUMN_REQUIREMENTS = "event_requirements";
    private static final String CREATE_CLUB_EVENT_TABLE = "CREATE TABLE " + TABLE_CLUB_EVENT + "("
            + COLUMN_CLUB_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLUB_ID_FK + " INTEGER,"
            + COLUMN_EVENT_ID_FK + " INTEGER,"
            + "FOREIGN KEY (" + COLUMN_CLUB_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "),"
            + "FOREIGN KEY (" + COLUMN_EVENT_ID_FK + ") REFERENCES " + TABLE_EVENT + "(" + COLUMN_EVENT_ID + ")" + ")";
    private static final String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EVENT_NAME + " TEXT,"
            + COLUMN_EVENT_TYPE + " TEXT,"
            + COLUMN_DETAILS + " TEXT,"
            + COLUMN_REQUIREMENTS + " TEXT" + ")";
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_ROLE + " TEXT" + ")";
    private static final String CREATE_CLUB_TABLE = "CREATE TABLE " + TABLE_CLUB + "("
            + COLUMN_CLUB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_INSTAGRAM_LINK + " TEXT,"
            + COLUMN_MAIN_CONTACT_NAME + " TEXT,"
            + COLUMN_PHONE_NUMBER + " TEXT,"
            + COLUMN_ADDRESS + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CLUB_TABLE);
        db.execSQL(CREATE_CLUB_EVENT_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean addUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, role);

        long result = db.insert(TABLE_USER, null, values);
        db.close();

        return result != -1;
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getUserRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ROLE + " FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
            cursor.close();
            return role;
        } else {
            return null;
        }
    }


    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public ArrayList getUsers(String role) {
        ArrayList<String> users = new ArrayList<>();
        int duplicate = 0;//variable used to stop duplicates from being added to array list

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + " FROM " + TABLE_USER, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                if (this.getUserRole(user, password).equals(role) && duplicate < 1) {
                    users.add(user);
                    duplicate++;
                } else if (duplicate >= 1) {
                    duplicate = 0;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public boolean deleteUser(String user) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_USER, COLUMN_USERNAME + "=?", new String[]{user});
        db.close();

        return result > 0;
    }

    // Method to save the club profile to the database
    public void saveClubProfile(ClubProfile profile, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_INSTAGRAM_LINK, profile.getInstagramLink()); // Adjust column name as needed
        values.put(COLUMN_MAIN_CONTACT_NAME, profile.getContactName()); // Adjust column name as needed
        values.put(COLUMN_PHONE_NUMBER, profile.getPhoneNumber()); // Adjust column name as needed

        long id = -1;
        try {
            // Check if a profile already exists and update or insert accordingly
            int updatedRows = db.update(TABLE_CLUB, values, COLUMN_CLUB_ID + " = ?", new String[]{"1"}); // Assuming a single profile with ID '1'
            if (updatedRows == 0) {
                // If no rows were updated, insert a new row
                id = db.insert(TABLE_CLUB, null, values);
            }
            if (updatedRows > 0 || id != -1) {
                Toast.makeText(context, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to save profile.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error saving profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    public ClubProfile getClubProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_INSTAGRAM_LINK, // Adjust column name as needed
                COLUMN_MAIN_CONTACT_NAME, // Adjust column name as needed
                COLUMN_PHONE_NUMBER // Adjust column name as needed
        };

        Cursor cursor = db.query(TABLE_CLUB, columns, COLUMN_CLUB_ID + " = ?", new String[]{"1"}, null, null, null); // Assuming a single profile with ID '1'
        ClubProfile profile = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String instagramLink = cursor.getString(cursor.getColumnIndex(COLUMN_INSTAGRAM_LINK));
            @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(COLUMN_MAIN_CONTACT_NAME));
            @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));

            profile = new ClubProfile(instagramLink, contactName, phoneNumber);
        }
        cursor.close();
        db.close();
        return profile;
    }
}

class EventDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "eventdatabase";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_EVENT = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_TYPE = "event_type";
    private static final String COLUMN_DETAILS = "event_details";
    private static final String COLUMN_REQUIREMENTS = "event_requirements";
    private static final String COLUMN_REGISTREE = "event_registree";

    private static final String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EVENT_NAME + " TEXT,"
            + COLUMN_EVENT_TYPE + " TEXT,"
            + COLUMN_DETAILS + " TEXT,"
            + COLUMN_REQUIREMENTS + " TEXT,"
            + COLUMN_REGISTREE + " TEXT" + ")";

    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        onCreate(db);
    }

    public String getEventType(String event_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_EVENT_TYPE + " FROM " + TABLE_EVENT + " WHERE " + COLUMN_EVENT_NAME + "=?", new String[]{event_name});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE));
            cursor.close();
            return type;
        } else {
            return null;
        }
    }

    public String getEventDetails(String event_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DETAILS + " FROM " + TABLE_EVENT + " WHERE " + COLUMN_EVENT_NAME + "=?", new String[]{event_name});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS));
            cursor.close();
            return role;
        } else {
            return null;
        }
    }

    public String getEventRequirements(String event_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_REQUIREMENTS + " FROM " + TABLE_EVENT + " WHERE " + COLUMN_EVENT_NAME + "=?", new String[]{event_name});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String requirements = cursor.getString(cursor.getColumnIndex(COLUMN_REQUIREMENTS));
            cursor.close();
            return requirements;
        } else {
            return null;
        }
    }


    public boolean addEvent(String eventName, String eventType, String eventDetails, String eventRequirements, String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, eventName);
        values.put(COLUMN_EVENT_TYPE, eventType);
        values.put(COLUMN_DETAILS, eventDetails);
        values.put(COLUMN_REQUIREMENTS, eventRequirements);
        values.put(COLUMN_REGISTREE, username);

        long result = db.insert(TABLE_EVENT, null, values);
        db.close();

        return result != -1;
    }

    // Method to retrieve all records from the table
    public ArrayList getAllRecords() {
        ArrayList<String> events = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_EVENT_NAME + " FROM " + TABLE_EVENT, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME));
                events.add(eventName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

    /*
    public ArrayList getAllRecords(String username) {
        ArrayList<String> events = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_EVENT_NAME + " FROM " + TABLE_EVENT + " WHERE " + COLUMN_EVENT_TYPE + "=?", new String[]{"Time Trial"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME));
                events.add(eventName);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return events;
    }
     */

    public ArrayList<String> getAllRecords(String username) {
        ArrayList<String> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_EVENT_NAME + " FROM " + TABLE_EVENT + " WHERE " + COLUMN_REGISTREE + "=?", new String[]{username});

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME));
                    events.add(eventName);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while getting event records", e);
        } finally {
            db.close();
        }

        return events;
    }


    public boolean deleteEvent(String eventName) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_EVENT, COLUMN_EVENT_NAME + "=?", new String[]{eventName});
        db.close();

        return result > 0;
    }

    public boolean editEvent(String defaultEventName, String eventName, String eventType, String eventDetails, String eventRequirements) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EVENT_NAME, eventName);
        cv.put(COLUMN_EVENT_TYPE, eventType);
        cv.put(COLUMN_DETAILS, eventDetails);
        cv.put(COLUMN_REQUIREMENTS, eventRequirements);

        int rowsUpdated = db.update(TABLE_EVENT, cv, "event_name=?", new String[]{defaultEventName});

        db.close();

        return rowsUpdated > 0;
    }


    @SuppressLint("Range")
    public boolean eventNameExists (String eventName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_EVENT_NAME + " FROM " + TABLE_EVENT, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nameComaprison = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME));
                if (nameComaprison.equals(eventName)){
                    return true;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return false;
    }


}