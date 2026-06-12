package org.tutorg;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.tutorg.data.Subject;
import org.tutorg.data.Tutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Testing class that contains unit tests for the Tutor class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */
public class TutorTests {

    /**
     * Tests the convertDaysIntToString() method.
     */
    @Test(timeout = 1000)
    public void testConvertDaysIntToString() {
        Tutor tutor = new Tutor();
        List<Integer> days = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<String> expected = Arrays.asList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        List<String> result = tutor.convertDaysIntToString(days);

        // Check that the converted days match the expected values
        assertEquals(expected, result);
    }

    /**
     * Tests the getDaysInStringFormat() method.
     */
    @Test(timeout = 1000)
    public void testGetDaysInStringFormat() {
        Tutor tutor = new Tutor();
        List<Integer> days = Arrays.asList(1, 2, 3);
        List<String> expected = Arrays.asList("Monday", "Tuesday", "Wednesday");

        tutor.setDays(new ArrayList<>(days));
        List<String> result = tutor.getDaysInStringFormat();

        // Check that the days in string format match the expected values
        assertEquals(expected, result);
    }

    @Test(timeout = 1000)
    public void testCopy() {
        Tutor tutor1 = new Tutor("FirstName", "LastName","Email","Location","Phone",new ArrayList<>(),new ArrayList<>());
        Tutor tutor2 = new Tutor();
        tutor2.copy(tutor1);
        List<String> expected = Arrays.asList("FirstName","LastName","Email");
        List<String> result = Arrays.asList(tutor2.getFirstName(), tutor2.getLastName(), tutor2.getEmail());
        // Check that the copied Tutor object matches the expected result
        assertEquals(expected, result);
    }

    @Test(timeout = 1000)
    public void testDefaultTrue() {
        Tutor tutor1 = new Tutor();
        assertEquals(true, tutor1.isDefault());
    }

    @Test(timeout = 1000)
    public void testDefaultFalse() {
        Tutor tutor1 = new Tutor("test@gmail.com");
        assertEquals(false, tutor1.isDefault());
    }

}
