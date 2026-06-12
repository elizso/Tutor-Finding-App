package org.tutorg.tutor_info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.tutorg.R;
import org.tutorg.data.Subject;

import java.util.ArrayList;

public class SimpleSubjectListAdapter extends RecyclerView.Adapter<SimpleSubjectListAdapter.ViewHolder> {
    ArrayList<Subject> subjects;

    /**
     * Adapter class for the subject list in the TutorInfoPage activity.
     */
    public SimpleSubjectListAdapter(TutorInfoPage tutorInfoPage, ArrayList<Subject> subjects){
        super();
        this.subjects = subjects;
    }


    @NonNull
    @Override
    public SimpleSubjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list_item_simple, parent, false);
        return new SimpleSubjectListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleSubjectListAdapter.ViewHolder holder, int position) {
        holder.getSubjectView().setText(subjects.get(position).getSubject());
        holder.subject = subjects.get(position);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    /**
     * ViewHolder class for the SubjectListAdapter.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        // we make them private because we want them to be accessed from the getters
        private Subject subject;
        private final TextView subjectView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectView = (TextView) itemView.findViewById(R.id.subject);

        }

        public TextView getSubjectView() {
            return subjectView;
        }

    }
}
