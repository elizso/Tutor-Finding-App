package org.tutorg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.tutorg.search.tokenizer.Token;
import org.tutorg.search.tokenizer.Tokenizer;

/**
 * Testing class that contains unit tests for the Tokenizer.java class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 */

public class TokenizerTest {

    /**
     * Test smallest valid entry which is one token ON
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testSingularONToken() {

        Tokenizer tokenizer = new Tokenizer("ON");

        assertEquals(Token.Type.ON, tokenizer.current().getType());
    }

    /**
     * Test smallest valid entry which is one token IN
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testSingularINToken() {

        Tokenizer tokenizer = new Tokenizer("IN");

        assertEquals(Token.Type.IN, tokenizer.current().getType());
    }

    /**
     * Test smallest valid entry which is one token STRING
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testSingularSTRINGToken() {

        Tokenizer tokenizer = new Tokenizer("Test");

        assertEquals("Test", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
    }

    /**
     * Test smallest valid entry which is one token SPACE
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testSingularSPACEToken() {

        Tokenizer tokenizer = new Tokenizer(" ");

        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
    }

    /**
     * Test smallest valid entry which is one token COMMA
     * @author Name: Samuel Seymour UID:u6959744
     */

    @Test(timeout = 1000)
    public void testSingularCOMMAToken() {

        Tokenizer tokenizer = new Tokenizer(",");

        assertEquals(Token.Type.COMMA, tokenizer.current().getType());
    }

    /**
     * Test smallest valid entry which is one token WITH
     * @author Name: Samuel Seymour UID:u6959744
     */

    @Test(timeout = 1000)
    public void testSingularWITHToken() {

        Tokenizer tokenizer = new Tokenizer("WITH");

        assertEquals(Token.Type.WITH, tokenizer.current().getType());
    }

    /**
     * Test empty entry
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testEmptyEntry() {
        Tokenizer tokenizer = new Tokenizer("");
        tokenizer.next();
        assertFalse(tokenizer.hasNext());
    }

    /**
     * Test multiple entries
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testMultipleStringToken() {

        Tokenizer tokenizer = new Tokenizer("MATH YEAR11");

        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        assertEquals("MATH", tokenizer.current().getToken());
        tokenizer.next();

        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertEquals("YEAR11", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
    }

    /**
     * Test multiple days entry
     * @author Name: Samuel Seymour UID:u6959744
     */

    @Test(timeout = 1000)
    public void testMultipleDays() {
        Tokenizer tokenizer = new Tokenizer("CHEM YEAR10 ON MONDAY, TUESDAY, WEDNESDAY");


        assertEquals( "CHEM", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertEquals( "YEAR10", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertEquals(Token.Type.ON, tokenizer.current().getType());
        tokenizer.next();

        assertEquals( Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "MONDAY", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( ",", tokenizer.current().getToken());
        assertEquals(Token.Type.COMMA, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "TUESDAY", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( ",", tokenizer.current().getToken());
        assertEquals(Token.Type.COMMA, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "WEDNESDAY", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertFalse(tokenizer.hasNext());
    }

    /**
     * Test long valid statement
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testLongStatement() {
        Tokenizer tokenizer = new Tokenizer("CHEM YEAR10 WITH SAM IN BRISBANE ON MONDAY");

        assertEquals( "CHEM", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertEquals( "YEAR10", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertEquals(Token.Type.WITH, tokenizer.current().getType());
        tokenizer.next();

        assertEquals( Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "SAM", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "IN", tokenizer.current().getToken());
        assertEquals(Token.Type.IN, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "BRISBANE", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "ON", tokenizer.current().getToken());
        assertEquals(Token.Type.ON, tokenizer.current().getType());
        tokenizer.next();

        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertEquals( "MONDAY", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertFalse(tokenizer.hasNext());
    }

    /**
     * Test long statement in valid entry
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 10000)
    public void testMultipleTokens1() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH JAI IN CANBERRA ON TUESDAY");
        assertTrue(tokenizer.hasNext());
        assertEquals( "MATH", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "YEAR9", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "WITH", tokenizer.current().getToken());
        assertEquals(Token.Type.WITH, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "JAI", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "IN", tokenizer.current().getToken());
        assertEquals(Token.Type.IN, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "CANBERRA", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "ON", tokenizer.current().getToken());
        assertEquals(Token.Type.ON, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "TUESDAY", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertFalse(tokenizer.hasNext());
    }

    /**
     * Test invalid statement multiple words
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testMultipleTokens() {
        Tokenizer tokenizer = new Tokenizer("ON WITH SAM, ON TUESDAY");
        assertTrue(tokenizer.hasNext());
        assertEquals( "ON", tokenizer.current().getToken());
        assertEquals(Token.Type.ON, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "WITH", tokenizer.current().getToken());
        assertEquals(Token.Type.WITH, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "SAM", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( ",", tokenizer.current().getToken());
        assertEquals(Token.Type.COMMA, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "ON", tokenizer.current().getToken());
        assertEquals(Token.Type.ON, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( " ", tokenizer.current().getToken());
        assertEquals(Token.Type.SPACE, tokenizer.current().getType());
        tokenizer.next();

        assertTrue(tokenizer.hasNext());
        assertEquals( "TUESDAY", tokenizer.current().getToken());
        assertEquals(Token.Type.STRING, tokenizer.current().getType());
        tokenizer.next();

        assertFalse(tokenizer.hasNext());
    }

}
