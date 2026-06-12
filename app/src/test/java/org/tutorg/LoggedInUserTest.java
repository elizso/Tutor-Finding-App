package org.tutorg;

import org.junit.Test;
import org.tutorg.data.model.LoggedInUser;

import static org.junit.Assert.*;

/**
 * Testing class that contains unit tests for the LoggedInUser class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 */

public class LoggedInUserTest {

    /**
     * Tests the constructor and getters of the LoggedInUser class.
     *
     * This method tests the constructor and getters of the LoggedInUser class by creating an instance of LoggedInUser
     * with a user ID and display name, and then asserts that the retrieved user ID and display name are correct.
     */
    @Test(timeout = 1000)
    public void testConstructorAndGetters() {
        String userId = "123";
        String displayName = "John Doe";

        LoggedInUser loggedInUser = new LoggedInUser(userId, displayName);

        assertEquals(userId, loggedInUser.getUserId());
        assertEquals(displayName, loggedInUser.getDisplayName());
    }
}

