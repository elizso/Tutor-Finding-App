package org.tutorg.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Student is the class that contains information pertaining to students
 * @author Lachlan McDonald (u7625813) Sharaf Zaman Samuel Seymour (u6959744)
 */

public class Subject implements Comparable<Subject>, Parcelable {

    public String level;
    public String discipline;

    @Exclude
    public String getSubject() {
        return discipline + " "+ level;
    }

    /**
     * Return true if the discipline and level fields are filled.
     * @return
     */
    @Exclude
    public boolean isValidSubject() {
        return !discipline.isEmpty() && !level.isEmpty();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Subject() {
        this.discipline = "";
        this.level = "";
    }

    public Subject(String discipline, String level) {
        this.discipline = discipline;
        this.level = level;
    }

    protected Subject(Parcel in) {
        level = in.readString();
        discipline = in.readString();
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    @Override
    public String toString() {
        return getSubject();
    }

    @Override
    public int compareTo(Subject o) {
        return this.getSubject().compareTo(o.getSubject());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(level);
        parcel.writeString(discipline);
    }
}
