package org.tutorg;

import org.junit.Test;
import org.tutorg.data.Result;

import static org.junit.Assert.*;

/**
 * Testing class that contains unit tests for the Results class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * Aided and adapted with ChatGPT
 */

public class ResultTest {

    /**
     * Tests the toString() method for a Success result.
     */
    @Test(timeout = 1000)
    public void testSuccessToString() {
        Result<Integer> successResult = new Result.Success<>(42);
        String expectedString = "Success[data=42]";
        String actualString = successResult.toString();
        assertEquals(expectedString, actualString);
    }

    /**
     * Tests the toString() method for an Error result.
     */
    @Test(timeout = 1000)
    public void testErrorToString() {
        Exception error = new Exception("Something went wrong");
        Result<String> errorResult = new Result.Error(error);
        String expectedString = "Error[exception=java.lang.Exception: Something went wrong]";
        String actualString = errorResult.toString();
        assertEquals(expectedString, actualString);
    }

    /**
     * Tests the toString() method for an empty Result.
     */
    @Test(timeout = 1000)
    public void testResultToStringEmpty() {
        Result<?> result = new Result<>();
        String expectedString = "";
        String actualString = result.toString();
        assertEquals(expectedString, actualString);
    }

    /**
     * Tests the toString() method for an unknown subclass of Result.
     */
    @Test(timeout = 1000)
    public void testResultToStringUnknownSubclass() {
        Result<?> result = new Result<>();
        String expectedString = "";
        String actualString = result.toString();
        assertEquals(expectedString, actualString);
    }
}

