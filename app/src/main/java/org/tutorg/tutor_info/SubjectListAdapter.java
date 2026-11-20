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

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {
    ArrayList<Subject> subjects;

    /**
     * Adapter class for the subject list in the TutorEdit activity.
     */
    public SubjectListAdapter(TutorEdit tutorEdit, ArrayList<Subject> subjects){
        super();
        this.subjects = subjects;
    }

    /**
     * Constructor for the SubjectListAdapter.
     *
     * @param tutorEdit The TutorEdit activity
     * @param subjects  The list of subjects
     */
    public SubjectListAdapter(UserEdit userEdit, ArrayList<Subject> subjects){
        super();
        this.subjects = subjects;
    }

    /**
     * Constructor for the SubjectListAdapter.
     *
     * @param userEdit The UserEdit activity
     * @param subjects The list of subjects
     */
    @NonNull
    @Override
    public SubjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list_item, parent, false);
        return new SubjectListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectListAdapter.ViewHolder holder, int position) {
        holder.getDisciplineView().setText(subjects.get(position).getDiscipline());
        holder.getLevelView().setText(subjects.get(position).getLevel());
        holder.subject = subjects.get(position);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subjects.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
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
        private final TextView disciplineView;
        private final TextView levelView;
        private final ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            disciplineView = (TextView) itemView.findViewById(R.id.subject);
            levelView = (TextView) itemView.findViewById(R.id.level);
            deleteButton = (ImageButton) itemView.findViewById(R.id.remove_subject);

        }

        public TextView getDisciplineView() {
            return disciplineView;
        }

        public TextView getLevelView() {
            return levelView;
        }

        public ImageButton getDeleteButton() {
            return deleteButton;
        }
    }
}
