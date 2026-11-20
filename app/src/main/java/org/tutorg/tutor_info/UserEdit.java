package org.tutorg.tutor_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.tutorg.R;
import org.tutorg.data.Subject;
import org.tutorg.data.User;

import java.util.ArrayList;

public class UserEdit extends AppCompatActivity {

    private User userObject;
    //Get input views from layout
    private TextView firstnameInput;
    private TextView lastnameInput;
    private TextView locationInput;
    private RecyclerView subjectRecyclerView;
    private TextView viewEmail;
    private TextView disciplineInput;
    private TextView levelInput;
    private SubjectListAdapter subjectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        Intent intent = getIntent();
        userObject = (User)intent.getParcelableExtra("user_object");

        //Get input fields
        firstnameInput = (TextView)findViewById(R.id.first_name);
        lastnameInput = (TextView)findViewById(R.id.last_name);
        locationInput = (TextView)findViewById(R.id.location);
        subjectRecyclerView = (RecyclerView)findViewById(R.id.subjects_recycler);
        viewEmail = (TextView)findViewById(R.id.email);
        disciplineInput = (TextView)findViewById(R.id.discipline_input);
        levelInput = (TextView)findViewById(R.id.level_input);

        setUserInfo();
        setButtons();
    }

    private void setUserInfo(){
        firstnameInput.setText(userObject.getFirstName());
        lastnameInput.setText(userObject.getLastName());
        locationInput.setText(userObject.getLocation());
        viewEmail.setText(userObject.getEmail());

        //Create layout manager and SubjectListAdapter to populate the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        subjectRecyclerView.setLayoutManager(layoutManager);
        subjectListAdapter = new SubjectListAdapter(this, userObject.getSubjects());
        subjectRecyclerView.setAdapter(subjectListAdapter);
    }

    private void setButtons(){
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Finishes current activity, going back to the previous page
             * @param view
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button editButton = (Button) findViewById(R.id.submit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Gets the updated tutor values within a new Tutor object and adds them to the database
             * @param view
             */
            @Override
            public void onClick(View view) {
                User updatedUser = new User();
                updatedUser.setFirstName((String)firstnameInput.getText());
                updatedUser.setLastName((String)lastnameInput.getText());
                updatedUser.setLocation((String)locationInput.getText());
                ArrayList<Integer> updatedDays = new ArrayList<Integer>();
                updatedUser.setSubjects(subjectListAdapter.subjects);
                //TODO: Save updated User object
            }
        });

        ImageButton addSubjectButton = (ImageButton) findViewById(R.id.add_subject_button);
        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(disciplineInput.getText() != "" && levelInput.getText() != ""){
                    for(String level: levelInput.getText().toString().split(",")){
                        //We check if a subject already exists for this tutor with matching discipline and level values
                        boolean matching = false;
                        for(Subject sub:subjectListAdapter.subjects) {
                            if (sub.getDiscipline() == disciplineInput.getText().toString() && sub.getLevel() == level) {
                                matching = true;
                            }
                        }
                        //If there is no matching subject already, we add to the list
                        if(!matching){
                            System.out.println(subjectListAdapter.subjects);
                            subjectListAdapter.subjects.add(new Subject(disciplineInput.getText().toString(), level));
                            subjectListAdapter.notifyItemInserted(subjectListAdapter.subjects.size() - 1);
                        }
                    }
                }
                //Reset Fields
                disciplineInput.setText("");
                levelInput.setText("");
            }
        });
    }
}