package org.tutorg;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.tutorg.data.Subject;
import org.tutorg.data.Tutor;
import org.tutorg.data.TutorSearchTree;
import org.tutorg.search.parser.EntryResults;
import org.tutorg.tutor_info.TutorInfoPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Sharaf Zaman
 */
public class SearchResultsAdapter extends
        RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> implements Observer  {

    private static final String TAG = "SearchResultsAdapter";

    private ArrayList<Tutor> tutors;
    private Subject mCurrentSearchSubject = new Subject();
    private EntryResults mSavedSearchEntry;

    SearchResultsAdapter() {
        super();
        tutors = new ArrayList<>();

        TutorSearchTree.getInstance().addObserver(this);
    }

    public int getShortestTutorLocation() {

        int shortestTutorLocation = 100;

        for (Tutor tutor : tutors) {

            int locationStringLength = tutor.getLocation().length();

            if (locationStringLength < shortestTutorLocation) {
                    shortestTutorLocation = locationStringLength;
                }
            }
        return shortestTutorLocation;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextPhoneAttribute().setText(tutors.get(position).getPhone());
        holder.getTextViewNameAttribute().setText(tutors.get(position).getFullName());
        holder.getTextViewLocationAttribute().setText(tutors.get(position).getLocation());

        if (mCurrentSearchSubject.isValidSubject()) {
            holder.getTextViewSubjectAttribute().setText(mCurrentSearchSubject.getSubject());
        } else {
            if (!tutors.get(position).getSubjects().isEmpty()) {
                holder.getTextViewSubjectAttribute()
                        .setText(tutors.get(position).getSubjects().get(0).getSubject());
            } else {
                holder.getTextViewSubjectAttribute().setText("No subjects yet!");
            }
        }

        holder.tutor = tutors.get(position);
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    /**
     * Finds the tutors given the entryResults.
     * @param entryResults contains what to search the tree for.
     * @author Name: Samuel Macauley UID: u7486259
     */
    synchronized public void searchChanged(EntryResults entryResults) {
        mSavedSearchEntry = entryResults;
        mCurrentSearchSubject = new Subject(entryResults.getDiscipline(), entryResults.getSubjectLevel());
        // Find tutor node given the Discipline and SubjectLevel.
        tutors =  TutorSearchTree.getInstance()
                .getMatchingTutors(mCurrentSearchSubject);
        // Clone tutors reference.
        ArrayList<Tutor> newTutors = (ArrayList<Tutor>) tutors.clone();

        String person = entryResults.getPerson();
        String location = entryResults.getLocation();
        List<String> days = entryResults.getDays();

        ArrayList<Tutor> tutorsToRemove = new ArrayList<>();

        // Find tutors given the optional entries.
        for (Tutor tutor: tutors){
            // If tutor's name isn't the same as entryResults person.
            if (person != null && !tutor.getFirstName().equals(person)){
                tutorsToRemove.add(tutor);
            }

            // If tutor's location isn't the same as entryResults location.
            if (location != null && !tutor.getLocation().equals(location)){
                tutorsToRemove.add(tutor);
            }

            // If tutor's days does not include any days from entryResults days.
            if (days != null && Collections.disjoint( tutor.getDaysInStringFormat(), days)){
                tutorsToRemove.add(tutor);
            }
        }
        newTutors.removeAll(tutorsToRemove);
        // Need to set tutors as newTutors for notifyDataSetChanged().
        tutors = newTutors;
        notifyDataSetChanged();
    }

    @Override
    synchronized public void update(Observable observable, Object o) {
        if (mSavedSearchEntry != null) {
            searchChanged(mSavedSearchEntry);
        } else {
            tutors = TutorSearchTree.getInstance()
                    .getMatchingTutors(new Subject("Literature", "7"));
            notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        // we make them private because we want them to be accessed from the getters
        private Tutor tutor;
        private final TextView textPhoneAttribute;
        private final TextView textViewNameAttribute;
        private final TextView textViewLocationAttribute;
        private final TextView textViewSubjectAttribute;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPhoneAttribute = itemView.findViewById(R.id.textPhoneAttribute);
            textViewNameAttribute = itemView.findViewById(R.id.textNameAttribute);
            textViewLocationAttribute = itemView.findViewById(R.id.textLocationAttribute);
            textViewSubjectAttribute = itemView.findViewById(R.id.textSubjectAttribute);

            // Set OnClickListener on the itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(itemView.getContext(), "Clicked on " + tutor.getFirstName()+": "+tutor.getPhone(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Clicked on: " + tutor.getFirstName());

                    Intent intent = new Intent(itemView.getContext(), TutorInfoPage.class);
                    //Edit to send whole tutor object
                    intent.putExtra("tutor_object", tutor);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public TextView getTextPhoneAttribute() {
            return textPhoneAttribute;
        }

        public TextView getTextViewNameAttribute() {
            return textViewNameAttribute;
        }

        public TextView getTextViewLocationAttribute() {
            return textViewLocationAttribute;
        }

        public TextView getTextViewSubjectAttribute() {
            return textViewSubjectAttribute;
        }


        public Tutor getTutorObject() {return tutor;}
    }
}
