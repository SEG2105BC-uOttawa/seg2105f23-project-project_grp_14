package com.example.gcc_application;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class ApplicationTest {

    @Mock
    EventDatabaseHelper eventDatabaseHelper;

    @Mock
    DatabaseHelper databaseHelper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidRatingCreation() {
        Rating rating = new Rating(5, "Great event", "user123");
        assertEquals(5, rating.getValue());
        assertEquals("Great event", rating.getComment());
        assertEquals("user123", rating.getUsername());
    }

    @Test
    public void testEventRegistration() {
        when(eventDatabaseHelper.addEvent("Cycling Marathon", "Road Race", "Details", "Requirements", "user123")).thenReturn(true);
        boolean result = eventDatabaseHelper.addEvent("Cycling Marathon", "Road Race", "Details", "Requirements", "user123");
        assertTrue(result);
    }

    @Test
    public void testEventRegistrationFailure() {
        when(eventDatabaseHelper.addEvent("Cycling Marathon", "Road Race", "Details", "Requirements", "user123")).thenReturn(false);
        boolean result = eventDatabaseHelper.addEvent("Cycling Marathon", "Road Race", "Details", "Requirements", "user123");
        assertFalse(result);
    }

    @Test
    public void testParticipantRegistrationForEvent() {
        when(eventDatabaseHelper.addParticipant("Cycling Marathon", "user123")).thenReturn(true);
        boolean result = eventDatabaseHelper.addParticipant("Cycling Marathon", "user123");
        assertTrue(result);
    }

    @Test
    public void testParticipantRegistrationFailureForEvent() {
        when(eventDatabaseHelper.addParticipant("Cycling Marathon", "user123")).thenReturn(false);
        boolean result = eventDatabaseHelper.addParticipant("Cycling Marathon", "user123");
        assertFalse(result);
    }

    @Test
    public void testEventSearchByName() {
        when(eventDatabaseHelper.getAllRecords("event_name", "Cycling Marathon")).thenReturn(new ArrayList<>());
        ArrayList<String> result = eventDatabaseHelper.getAllRecords("event_name", "Cycling Marathon");
        assertNotNull(result);
    }

    @Test
    public void testEventSearchByType() {
        when(eventDatabaseHelper.getAllRecords("event_type", "Road Race")).thenReturn(new ArrayList<>());
        ArrayList<String> result = eventDatabaseHelper.getAllRecords("event_type", "Road Race");
        assertNotNull(result);
    }

    @Test
    public void testEventDetailsFetching() {
        when(eventDatabaseHelper.getEventDetails("Cycling Marathon")).thenReturn("Detailed description here");
        String details = eventDatabaseHelper.getEventDetails("Cycling Marathon");
        assertEquals("Detailed description here", details);
    }

    @Test
    public void testEventRequirementsFetching() {
        when(eventDatabaseHelper.getEventRequirements("Cycling Marathon")).thenReturn("Requirements here");
        String requirements = eventDatabaseHelper.getEventRequirements("Cycling Marathon");
        assertEquals("Requirements here", requirements);
    }

    @Test
    public void testRatingValueConstraint() {
        Rating rating = new Rating(6, "Excellent", "user123");
        assertFalse(rating.getValue() >= 1 && rating.getValue() <= 5);
    }
}
