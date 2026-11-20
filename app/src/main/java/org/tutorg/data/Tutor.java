package org.tutorg.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import javax.security.auth.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Lachlan Macdonald, Samuel Seymour, Sharaf Zaman
 *
 * Central object which handles the Tutor object. We can send this class to activities
 * And we can use this class to add and remove data from firebase.
 */
public class Tutor extends User implements Parcelable {
    private String phone;
    private ArrayList<Integer> days;
    private ArrayList<User> students;
    private ArrayList<User> messageRequests;

    /**
     * This field is used not on firebase, but internally in the tree to do some things.
     */
    private int id;

    public Tutor(){
        super("Issac", "Newton", "issac@cambridge.edu", "Melbourne", new ArrayList<>());
        this.phone = "123";
        this.days = new ArrayList<>();
    }

    public Tutor(String email){
        super("","",email,"",new ArrayList<>());
        this.phone = "";
        this.days = new ArrayList<Integer>();
    }

    public Tutor(String firstName, String lastName, String email, String location, String phone, ArrayList<Integer> days,ArrayList<org.tutorg.data.Subject> subjects) {
        super(firstName,lastName,email,location,subjects);
        this.phone = phone;
        Collections.sort(days);
        //Sort the days so that they appear in order
        this.days = days;
    }

    protected Tutor(Parcel in) {
        setFirstName(in.readString());
        setLastName(in.readString());
        setEmail(in.readString());
        setLocation(in.readString());
        setSubjects((ArrayList<org.tutorg.data.Subject>) in.readArrayList(org.tutorg.data.Subject.class.getClassLoader()));
        setPhone(in.readString());
        setDays(in.readArrayList(getClass().getClassLoader()));
        setId(in.readInt());
    }

    public static final Creator<Tutor> CREATOR = new Creator<Tutor>() {
        @Override
        public Tutor createFromParcel(Parcel in) {
            return new Tutor(in);
        }

        @Override
        public Tutor[] newArray(int size) {
            return new Tutor[size];
        }
    };

    public String getPhone() {
        return phone;
    }
    public String getLocation(){return location;}

    public void setPhone(String phone) {this.phone = phone;}

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days){this.days = days;}

    public ArrayList<User> getMessageRequests() {
        return messageRequests;
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public void setMessageRequests(ArrayList<User> messageRequests) {
        this.messageRequests = messageRequests;
    }

    public void setStudents(ArrayList<User> students) {
        this.students = students;
    }

    public boolean studiesSubject(Subject subject) {
        for (org.tutorg.data.Subject d: super.getSubjects()) {
            if (d.equals(subject)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getFirstName());
        parcel.writeString(getLastName());
        parcel.writeString(getEmail());
        parcel.writeString(getLocation());
        parcel.writeList(getSubjects());
        parcel.writeString(getPhone());
        parcel.writeList(getDays());
        parcel.writeInt(getId());
    }

    /**
     * Converts a list of days in integer format to a list of days in string format.
     * @return List of days in string format.
     * @author Name: Samuel Macauley UID: u7486259
     */
    @Exclude
    public List<String> convertDaysIntToString(List<Integer> days){
        List<String> daysInStringFormat = new ArrayList<>();

        for (Integer day:days) {
            switch (day) {
                case 1 : daysInStringFormat.add("Monday");
                break;
                case 2 : daysInStringFormat.add("Tuesday");
                break;
                case 3 : daysInStringFormat.add("Wednesday");
                break;
                case 4 : daysInStringFormat.add("Thursday");
                break;
                case 5 : daysInStringFormat.add("Friday");
                break;
                case 6 : daysInStringFormat.add("Saturday");
                break;
                case 7 : daysInStringFormat.add("Sunday");
                break;
            }
        }

        return daysInStringFormat;
    }

    @Exclude
    public List<String> getDaysInStringFormat(){
        return convertDaysIntToString(days);
    }

    @Exclude
    public void setId(int id) {
        this.id = id;
    }

    @Exclude
    public Integer getId() {
        return id;
    }

    /**
     * A copy constructor for our use. Say if we want to move a tutor object from some place else.
     * @param tutor
     */
    public void copy(Tutor tutor) {
        this.firstName = tutor.firstName;
        this.lastName = tutor.lastName;
        this.email = tutor.email;
        this.location = tutor.location;
        setSubjects(tutor.getSubjects());
        this.phone = tutor.phone;
        this.days = tutor.days;
    }
    @Exclude
    public boolean isDefault(){
        if(email.equals("issac@cambridge.edu")){
            return true;
        }
        else{ return false;}
    }
}
