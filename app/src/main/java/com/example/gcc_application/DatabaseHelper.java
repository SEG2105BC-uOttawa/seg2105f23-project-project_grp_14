package com.example.gcc_application;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userdatabase";
    private static final int DATABASE_VERSION = 2; // Updated database version
    private static final String TABLE_USER = "users"; // Changed table name to "users"
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role"; // Removed COLUMN_FIRSTNAME as it wasn't used in the given methods

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_ROLE + " TEXT" + ")"; // Updated table creation statement

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
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

                if (this.getUserRole(user, password).equals(role) && duplicate<1) {
                    users.add(user);
                    duplicate++;
                } else if (duplicate >= 1){
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
}

class EventDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "eventdatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_EVENT = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_TYPE = "event_type";
    private static final String COLUMN_DETAILS = "event_details";
    private static final String COLUMN_REQUIREMENTS = "event_requirements";

    private static final String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EVENT_NAME + " TEXT,"
            + COLUMN_EVENT_TYPE + " TEXT,"
            + COLUMN_DETAILS + " TEXT,"
            + COLUMN_REQUIREMENTS + " TEXT" + ")";

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


    public boolean addEvent(String eventName, String eventType, String eventDetails, String eventRequirements) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, eventName);
        values.put(COLUMN_EVENT_TYPE, eventType);
        values.put(COLUMN_DETAILS, eventDetails);
        values.put(COLUMN_REQUIREMENTS, eventRequirements);

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
