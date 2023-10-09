package com.example.gcc_application;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountCreationTest {

    @Mock
    DatabaseHelper databaseHelper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdminAccountCreation() {
        when(databaseHelper.addUser("adminTest", "passwordTest", "admin")).thenReturn(true);
        boolean result = databaseHelper.addUser("adminTest", "passwordTest", "admin");
        assertTrue(result);
    }

    @Test
    public void testAdminAccountCreationFailure() {
        when(databaseHelper.addUser("adminTest", "wrongPassword", "admin")).thenReturn(false);
        boolean result = databaseHelper.addUser("adminTest", "wrongPassword", "admin");
        assertFalse(result);
    }

    @Test
    public void testClubMemberAccountCreation() {
        when(databaseHelper.addUser("clubTest", "passwordTest", "club member")).thenReturn(true);
        boolean result = databaseHelper.addUser("clubTest", "passwordTest", "club member");
        assertTrue(result);
    }

    @Test
    public void testClubMemberAccountCreationFailure() {
        when(databaseHelper.addUser("clubTest", "wrongPassword", "club member")).thenReturn(false);
        boolean result = databaseHelper.addUser("clubTest", "wrongPassword", "club member");
        assertFalse(result);
    }

    @Test
    public void testParticipantAccountCreation() {
        when(databaseHelper.addUser("participantTest", "passwordTest", "participant")).thenReturn(true);
        boolean result = databaseHelper.addUser("participantTest", "passwordTest", "participant");
        assertTrue(result);
    }

    @Test
    public void testParticipantAccountCreationFailure() {
        when(databaseHelper.addUser("participantTest", "wrongPassword", "participant")).thenReturn(false);
        boolean result = databaseHelper.addUser("participantTest", "wrongPassword", "participant");
        assertFalse(result);
    }

    // ................................

    
}
