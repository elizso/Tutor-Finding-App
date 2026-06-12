package org.tutorg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.tutorg.search.parser.EntryResults;
import org.tutorg.search.parser.Parser;
import org.tutorg.search.tokenizer.Token;
import org.tutorg.search.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing class that contains unit tests for the Parser.java class.
 *
 * @author Name: Samuel Seymour UID:u6959744
 * and
 * @author Name: Samuel Macauley UID:u7486259
 */

public class ParserTest {

    /**
     * Test parser Chemistry subject and level 10
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testValidChemistryYear10() {

        Tokenizer tokenizer = new Tokenizer("CHEM YEAR10");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);

        assertEquals("CHEM", entryResults.getDiscipline());
        assertEquals("YEAR10", entryResults.getSubjectLevel());
        assertNull(entryResults.getPerson());
        assertNull(entryResults.getLocation());
        assertNull(entryResults.getDays());
    }

    /**
     * Test smallest valid entry which is two STRING separated by a SPACE.
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(timeout = 1000)
    public void testMandatoryEntry() {

        Tokenizer tokenizer = new Tokenizer("MATH YEAR11");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        assertEquals("MATH", entryResults.getDiscipline());
        assertEquals("YEAR11", entryResults.getSubjectLevel());
        assertNull(entryResults.getPerson());
        assertNull(entryResults.getLocation());
        assertNull(entryResults.getDays());
    }

    /**
     * Test mandatory entry WITH person.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testMandatoryEntryWITHPerson() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA WITH Jim");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals("Jim", entryResults.getPerson());
        assertNull(entryResults.getLocation());
        assertNull(entryResults.getDays());
    }

    /**
     * Test mandatory entry IN place.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testMandatoryEntryINPlace() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA IN Canberra");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals("Canberra", entryResults.getLocation());
        assertNull(entryResults.getPerson());
        assertNull(entryResults.getDays());
    }
    /**
     * Test mandatory entry ON day.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testMandatoryEntryONDay() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA ON Monday");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        List<String> days = new ArrayList<>();
        days.add("Monday");

        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals(days, entryResults.getDays());
        assertNull(entryResults.getLocation());
        assertNull(entryResults.getPerson());
    }

    /**
     * Test mandatory entry IN location WITH person ON day.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testOptionalEntryStartingWithLocationThenPerson() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA IN Canberra WITH Jim ON Monday");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        List<String> days = new ArrayList<>();
        days.add("Monday");

        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals("Jim", entryResults.getPerson());
        assertEquals("Canberra", entryResults.getLocation());
        assertEquals(days, entryResults.getDays());
    }

    /**
     * Test mandatory entry IN location ON day WITH person.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testOptionalEntryStartingWithLocationThenDay() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA IN Canberra ON Monday, Friday, Thursday WITH Jim");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Friday");
        days.add("Thursday");

        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals("Jim", entryResults.getPerson());
        assertEquals("Canberra", entryResults.getLocation());
        assertEquals(days, entryResults.getDays());
    }


    /**
     * Test mandatory entry ON day IN location WITH person.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testOptionalEntryStartingWithDayThenLocation() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA ON Tuesday IN Canberra WITH Dave");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        List<String> days = new ArrayList<>();
        days.add("Tuesday");
        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals(days, entryResults.getDays());
        assertEquals("Canberra", entryResults.getLocation());
        assertEquals("Dave", entryResults.getPerson());
    }

    /**
     * Test mandatory entry ON day WITH person IN location.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testOptionalEntryStartingWithDayThenPerson() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA ON Tuesday WITH Dave IN Canberra");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        List<String> days = new ArrayList<>();
        days.add("Tuesday");

        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals(days, entryResults.getDays());
        assertEquals("Canberra", entryResults.getLocation());
        assertEquals("Dave", entryResults.getPerson());
    }


    /**
     * Test mandatory entry WITH person IN location ON day.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testOptionalEntryStartingWithPersonThenLocation() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA WITH Jim IN Canberra ON Wednesday, Monday");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        List<String> days = new ArrayList<>();
        days.add("Wednesday");
        days.add("Monday");


        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals("Jim", entryResults.getPerson());
        assertEquals("Canberra", entryResults.getLocation());
        assertEquals(days, entryResults.getDays());
    }

    /**
     * Test mandatory entry WITH person ON day IN location.
     * @author Name: Samuel Macauley UID:u7486259
     */
    @Test(timeout = 1000)
    public void testOptionalEntryStartingWithPersonThenDay() {

        Tokenizer tokenizer = new Tokenizer("Math SpecA WITH Jim ON Monday, Tuesday IN Canberra");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);

        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        assertEquals("Math", entryResults.getDiscipline());
        assertEquals("SpecA", entryResults.getSubjectLevel());
        assertEquals("Jim", entryResults.getPerson());
        assertEquals("Canberra", entryResults.getLocation());
        assertEquals(days, entryResults.getDays());
    }


    /**
     * Test parser invalid entry single discipline
     @author Name: Samuel Seymour, Samuel Macauley
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 1000)
    public void testParserSingleSubjectError() {
        Tokenizer tokenizer = new Tokenizer("MATH");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid entry single subject level
     @author Name: Samuel Seymour
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 1000)
    public void testParserSingleLevelError() {
        Tokenizer tokenizer = new Tokenizer("Year10");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid first space in expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 1000)
    public void testParserFirstSpaceError() {
        Tokenizer tokenizer = new Tokenizer(" MATH YEAR9");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid space last in expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserLastSpaceError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 ");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid space end of long expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserSpaceEndOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON TUESDAY IN CANBERRA ");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid space middle of long expression after WITH
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserSpaceAfterWithOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH  SAM ON TUESDAY IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid space middle of long expression after ON
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserSpaceAfterOnOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON  TUESDAY IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid space middle of long expression after IN
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserSpaceAfterInOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON TUESDAY IN  CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid entry double space middle of expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 1000)
    public void testParserDoubleSpaceError() {
        Tokenizer tokenizer = new Tokenizer("MATH  YEAR9");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma middle of expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaErrorMiddle() {
        Tokenizer tokenizer = new Tokenizer("MATH, YEAR9");
        EntryResults entryResults = new EntryResults();

        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma end of expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaErrorEnd() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9,");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma end of long expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaEndOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON TUESDAY IN CANBERRA,");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma middle of long expression after WITH
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaAfterWITHOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH, SAM ON TUESDAY IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma middle of long expression after tutor
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaAfterTutorOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM, ON TUESDAY IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma middle of long expression after ON
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaAfterONOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON, TUESDAY IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma middle of long expression after Day but no extra day present
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaAfterDayOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON TUESDAY, IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma middle of long expression after Second Day but no Third day present
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaAfterSecondDayOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON TUESDAY, WEDNESDAY, IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid double comma middle of long expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserDoubleCommaAfterDayOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON TUESDAY,, WEDNESDAY IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid double comma middle of long expression with space
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserDoubleCommaWithSpaceOfLongStatementError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM ON TUESDAY, , WEDNESDAY IN CANBERRA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma start of expression
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaErrorStart() {
        Tokenizer tokenizer = new Tokenizer(",MATH YEAR9");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma after location
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaAfterPlaceError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 IN CANBERRA, ON TUESDAY");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid comma after preferred tutor
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserCommaAfterTutorError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM, ON TUESDAY");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser two ON's invalid entry
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserDoubleONError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 ON TUESDAY ON MONDAY");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser two WITH's invalid entry
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserDoubleWITHError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 WITH SAM WITH MICHAEL");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser two IN's invalid entry
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testParserDoubleINError() {
        Tokenizer tokenizer = new Tokenizer("MATH YEAR9 IN CANBERRA IN BRISBANE");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

    /**
     * Test parser invalid ordering, must have Discipline then Level
     * @author Name: Samuel Seymour UID:u6959744
     */
    @Test(expected = Parser.IllegalProductionException.class , timeout = 10000)
    public void testInvalidWrongOrderEntryWITHPerson() {

        Tokenizer tokenizer = new Tokenizer("WITH Jim Math SpecA");
        EntryResults entryResults = new EntryResults();
        Parser parser = new Parser(tokenizer, entryResults);
    }

}
