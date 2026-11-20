package org.tutorg.data;

import java.util.ArrayList;

/**
 * Student is the class that contains information pertaining to students
 * @author Lachlan McDonald (u7625813) Samuel Seymour (u6959744)
 */

public class Student extends User{

    private int yearLevel;
    private ArrayList<Tutor> tutors;
    private ArrayList<Tutor> outgoingRequests;

    public Student(){

    }

    public Student(String firstName, String lastName, String email, String location, ArrayList<Subject> subjects, int yearLevel, ArrayList<Tutor> tutors, ArrayList<Tutor> outgoingRequests){
        super(firstName,lastName,email,location,subjects);
        this.yearLevel = yearLevel;
        this.tutors = tutors;
        this.outgoingRequests = outgoingRequests;
    }
}
