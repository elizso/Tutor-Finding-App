package org.tutorg.data;

import com.google.firebase.database.Exclude;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * User is the superclass of Tutors and Students
 * @author Lachlan Mc
 */

public class User implements Parcelable {

    protected String firstName;

    protected String lastName;

    protected String email;

    protected String location;

    private ArrayList<Subject> subjects = new ArrayList<>();

    public User(){}

    /**
     * Constructs a User object with the provided information.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email of the user.
     * @param location  The location of the user.
     * @param subjects  The list of subjects associated with the user.
     */
    public User(String firstName, String lastName, String email, String location, ArrayList<Subject> subjects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.subjects = subjects;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        location = in.readString();
        subjects = in.createTypedArrayList(Subject.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the full name of the user.
     *
     * @return The full name of the user.
     */
    @Exclude
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the location of the user.
     *
     * @return The location of the user.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the location of the user.
     *
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the list of subjects associated with the user in alphabetical order.
     *
     * @return The list of subjects associated with the user.
     */
    public ArrayList<Subject> getSubjects() {
        Collections.sort(subjects);
        return subjects;
    }

    /**
     * Sets the list of subjects associated with the user.
     *
     * @param subjects The list of subjects to set.
     */
    public void setSubjects(ArrayList<Subject> subjects) {
        if (subjects == null) {
            return;
        }
        this.subjects = subjects;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
        parcel.writeString(location);
        parcel.writeTypedList(subjects);
    }
}
