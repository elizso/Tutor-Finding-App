package org.tutorg.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.core.view.Change;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


/**
 * @author Eliz So
 *
 * FirebaseFetcher is a singleton which persists throughout the life of the app. It is loaded
 * when the user logs in and once they do we can use it to fetch the data from our firebase
 * instance.
 *
 * We create a singleton because it ensures that calling FirebaseFetcher multiple times doesn't
 * result in us fetching the data over and over.
 *
 */
public class FirebaseFetcher extends Observable {
    private static String TAG = "FirebaseFetcher";

    private ArrayList<Tutor> tutors;

    private final FirebaseDatabase database =
            FirebaseDatabase.getInstance("");
    private DatabaseReference tutorReference = database.getReference("tutor");

    private static FirebaseFetcher sInstance = null;

    /**
     * The type of change we are doing to our contiguous list of data elements.
     * ADDED - Means that we have added a new item to the list
     * UPDATED - Means that the item was updated in the list and not added.
     */
    public enum ChangeType {
        ADDED,
        UPDATED
    }

    public static FirebaseFetcher getInstance() {
        if (sInstance == null) {
            sInstance = new FirebaseFetcher();
            sInstance.tutors = new ArrayList<>();
        }
        return sInstance;
    }

    private void setupListeners() {
        tutorReference.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(
                            @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // This means that the tutor was added
                        Tutor tutor = getTutorFromFirebaseSnapshot(snapshot);
                        if (tutor == null) {
                            Log.w(TAG, "Something gone wrong with firebase backend");
                            return;
                        }
                        addTutorWithCheck(tutor);

                        setChanged();
                        notifyObservers(tutor);
                    }

                    @Override
                    public void onChildChanged(
                            @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (tutors == null)
                            return;

                        // This means that the tutor was added
                        Tutor tutor = getTutorFromFirebaseSnapshot(snapshot);
                        if (addTutorWithCheck(tutor) == ChangeType.ADDED) {
                            setChanged();
                            notifyObservers(tutor);
                        } else {
                            setChanged();
                            notifyObservers();
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    }

                    @Override
                    public void onChildMoved(
                            @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    /**
     * This adds the tutor but checks if it exists before adding it.
     * @param tutor
     */
    private ChangeType addTutorWithCheck(Tutor tutor) {
        if (tutors.size() <= tutor.getId()) {
            tutors.add(tutor);
            return ChangeType.ADDED;
        } else {
            Tutor oldTutor = tutors.get(tutor.getId());
            if (oldTutor.getSubjects().size() != tutor.getSubjects().size()) {
                tutor.getSubjects().removeAll(oldTutor.getSubjects());
                setChanged();
                notifyObservers(tutor);
                tutor.copy(oldTutor);
            }
            return ChangeType.UPDATED;
        }
    }

    private Tutor getTutorFromFirebaseSnapshot(DataSnapshot snapshot) {
        Tutor tutor = null;
        try {
        tutor = snapshot.getValue(Tutor.class);
        if (tutor == null) {
            return new Tutor();
        }
        tutor.setId(Integer.parseInt(snapshot.getKey()));
        } catch (DatabaseException e) {
            Log.w(TAG, "Exception due to some casting: " + e.getMessage());
            e.printStackTrace();
        }
        return tutor;
    }

    private FirebaseFetcher(){
        setupListeners();
    }

    public ArrayList<Tutor> getTutors() {
        return tutors;
    }

    public void updateTutor(Tutor updatedTutor) {
        Map<String, Object> map = new HashMap<>();
        map.put(updatedTutor.getId().toString(), updatedTutor);
        tutorReference.updateChildren(map);
    }

    public void addTutor(Tutor updatedTutor) {
        updatedTutor.setId(tutors.size());
        tutorReference.child(new Integer(tutors.size()).toString()).setValue(updatedTutor);
    }
}
