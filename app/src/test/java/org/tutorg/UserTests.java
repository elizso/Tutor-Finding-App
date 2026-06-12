package org.tutorg;

import org.junit.Test;
import org.tutorg.data.Subject;
import org.tutorg.data.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Testing class that contains unit tests for the User class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */
public class UserTests {

    /**
     * Tests the getFullName() method.
     */
    @Test(timeout = 1000)
    public void testGetFullName() {
        User user = new User("John", "Doe", "john@example.com", "Melbourne", new ArrayList<>());

        String fullName = user.getFullName();

        assertEquals("John Doe", fullName);
    }

    /**
     * Tests the getSubjects() method.
     */
    @Test(timeout = 1000)
    public void testGetSubjects() {
        Subject subject1 = new Subject("Math", "Algebra");
        Subject subject2 = new Subject("Science", "Physics");
        ArrayList<Subject> subjects = new ArrayList<>(Arrays.asList(subject2, subject1));

        User user = new User("John", "Doe", "john@example.com", "Melbourne", subjects);

        List<Subject> sortedSubjects = user.getSubjects();

        // Check that the subjects are sorted in alphabetical order
        assertEquals(subject1, sortedSubjects.get(0));
        assertEquals(subject2, sortedSubjects.get(1));
    }

    /**
     * Tests the setSubjects() method.
     */
    @Test(timeout = 1000)
    public void testSetSubjects() {
        Subject subject1 = new Subject("Math", "Algebra");
        Subject subject2 = new Subject("Science", "Physics");
        ArrayList<Subject> subjects = new ArrayList<>(Arrays.asList(subject1, subject2));

        User user = new User("John", "Doe", "john@example.com", "Melbourne", new ArrayList<>());

        user.setSubjects(subjects);

        // Check that the subjects are set correctl
        assertEquals(subjects, user.getSubjects());
    }
}

