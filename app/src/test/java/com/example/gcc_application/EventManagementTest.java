package com.example.gcc_application;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class EventManagementTest {
    @Mock
    DatabaseHelper databaseHelper;

    @Mock
    EventDatabaseHelper eventDatabaseHelper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEventCreation() {
        when(eventDatabaseHelper.addEvent("Event 1", "Time Trial", "Description: test description \nDate: test date \nRoute: test route", "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement")).thenReturn(true);
        boolean result = eventDatabaseHelper.addEvent("Event 1", "Time Trial", "Description: test description \nDate: test date \nRoute: test route", "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement");
        assertTrue(result);
    }

    @Test
    public void testEventCreationFailure() {
        when(eventDatabaseHelper.addEvent("Event 1", "Time Trial", null, "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement")).thenReturn(false);
        boolean result = eventDatabaseHelper.addEvent("Event 1", "Time Trial", null, "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement");
        assertFalse(result);
    }

    @Test
    public void testEventDeletion(String eventName) {
        when(eventDatabaseHelper.deleteEvent(eventName)).thenReturn(true);
        boolean result = eventDatabaseHelper.deleteEvent(eventName);
        assertTrue(result);
    }

    @Test
    public void testAccountDeletion(String accountName) {
        when(databaseHelper.deleteUser(accountName)).thenReturn(true);
        boolean result = databaseHelper.deleteUser(accountName);
        assertTrue(result);
    }

    @Test
    public void testEventEdit(String eventName) {
        when(eventDatabaseHelper.editEvent(eventName, "New Event", "Time Trial", "Description: test description \nDate: test date \nRoute: test route","Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement")).thenReturn(true);
        boolean result = eventDatabaseHelper.editEvent(eventName, "New Event", "Time Trial", "Description: test description \nDate: test date \nRoute: test route","Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement");
        assertTrue(result);
    }

}
