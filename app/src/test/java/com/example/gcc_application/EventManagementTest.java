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
        when(eventDatabaseHelper.addEvent("Event 1", "Time Trial", "Description: test description \nDate: test date \nRoute: test route", "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement", "admin")).thenReturn(true);
        boolean result = eventDatabaseHelper.addEvent("Event 1", "Time Trial", "Description: test description \nDate: test date \nRoute: test route", "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement", "admin");
        assertTrue(result);
    }

    @Test
    public void testEventCreationFailure() {
        when(eventDatabaseHelper.addEvent("Event 1", "Time Trial", null, "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement", "admin")).thenReturn(false);
        boolean result = eventDatabaseHelper.addEvent("Event 1", "Time Trial", null, "Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement", "admin");
        assertFalse(result);
    }

    @Test
    public void testEventDeletion() {
        when(eventDatabaseHelper.deleteEvent("Event 1")).thenReturn(true);
        boolean result = eventDatabaseHelper.deleteEvent("Event 1");
        assertTrue(result);
    }

    @Test
    public void testAccountDeletion() {
        when(databaseHelper.deleteUser("Account 1")).thenReturn(true);
        boolean result = databaseHelper.deleteUser("Account 1");
        assertTrue(result);
    }

    @Test
    public void testEventEdit() {
        when(eventDatabaseHelper.editEvent("Event 1", "New Event", "Time Trial", "Description: test description \nDate: test date \nRoute: test route","Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement")).thenReturn(true);
        boolean result = eventDatabaseHelper.editEvent("Event 1", "New Event", "Time Trial", "Description: test description \nDate: test date \nRoute: test route","Age requirement: test age requirement \nLevel requirement: test level requirement \nPace Requirement: test pace requirement");
        assertTrue(result);
    }

}
