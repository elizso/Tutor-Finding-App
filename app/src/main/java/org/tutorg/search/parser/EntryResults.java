package org.tutorg.search.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the results from a successfully parsed entry.
 *  * @author Name: Samuel Macauley UID:u7486259
 */
public class EntryResults {
    String discipline;
    String subjectLevel;
    String person;

    String location;
    List<String> days;

    public EntryResults() {
        this.discipline = null;
        this.subjectLevel = null;
        this.person = null;
        this.location = null;
        this.days = null;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getSubjectLevel() {
        return subjectLevel;
    }

    public void setSubjectLevel(String subjectLevel) {
        this.subjectLevel = subjectLevel;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getDays() {
        return days;
    }


    /**
     * Adds a day to days.
     * @param day the day to add.
     */
    public void addDay(String day){
        if (days == null) days = new ArrayList<>();
        this.days.add(day);
    }
}
