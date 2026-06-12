package org.tutorg;

import org.junit.Test;
import org.tutorg.data.Subject;

import static org.junit.Assert.*;

import android.os.Parcel;

/**
 * Testing class that contains unit tests for the Subject class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */

public class SubjectTest {

    /**
     * Tests the getSubject() method.
     */
    @Test(timeout = 1000)
    public void testGetSubject() {
        Subject subject = new Subject("Math", "Advanced");
        assertEquals("Math Advanced", subject.getSubject());
    }

    /**
     * Tests the isValidSubject() method.
     */
    @Test(timeout = 1000)
    public void testIsValidSubject() {
        Subject validSubject = new Subject("Math", "Advanced");
        Subject invalidSubject = new Subject("", "Intermediate");

        assertTrue(validSubject.isValidSubject());
        assertFalse(invalidSubject.isValidSubject());
    }

    /**
     * Tests the getters and setters for discipline and level.
     */
    @Test(timeout = 1000)
    public void testGettersAndSetters() {
        Subject subject = new Subject();
        subject.setDiscipline("Physics");
        subject.setLevel("1");

        assertEquals("Physics", subject.getDiscipline());
        assertEquals("1", subject.getLevel());
    }

    /**
     * Tests the toString() method.
     */
    @Test(timeout = 1000)
    public void testToString() {
        Subject subject = new Subject("English", "1");
        assertEquals("English 1", subject.toString());
    }
}

