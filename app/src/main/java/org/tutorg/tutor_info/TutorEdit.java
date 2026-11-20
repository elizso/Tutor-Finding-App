package org.tutorg.tutor_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.tutorg.R;
import org.tutorg.data.FirebaseFetcher;
import org.tutorg.data.Subject;
import org.tutorg.data.Tutor;

import java.util.ArrayList;
import java.util.Collections;

public class TutorEdit extends AppCompatActivity {
    private static final String TAG = "TutorEdit";
    private Tutor tutorObject;
    // Get input views from layout
    private TextView firstnameInput;
    private TextView lastnameInput;
    private TextView phoneInput;
    private TextView locationInput;
    private RecyclerView subjectRecyclerView;
    private TextView daysDropdown;
    private TextView viewEmail;
    private TextView disciplineInput;
    private TextView levelInput;

    // Initialise days_available variables
    private boolean[] selectedDays;
    private ArrayList<Integer> dayList = new ArrayList<>();
    private String[] dayArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private SubjectListAdapter subjectListAdapter;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_edit);
        Intent intent = getIntent();
        tutorObject = (Tutor) intent.getParcelableExtra("tutor_object");
        // Toast.makeText(getApplicationContext(), "Clicked on " + tutorObject.getFirstName(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "Clicked on: " + tutorObject.getFirstName());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get input fields
        firstnameInput = (TextView) findViewById(R.id.first_name);
        lastnameInput = (TextView) findViewById(R.id.last_name);
        phoneInput = (TextView) findViewById(R.id.phone);
        locationInput = (TextView) findViewById(R.id.location);
        subjectRecyclerView = (RecyclerView) findViewById(R.id.subjects_recycler);
        viewEmail = (TextView) findViewById(R.id.email);
        disciplineInput = (TextView) findViewById(R.id.discipline_input);
        levelInput = (TextView) findViewById(R.id.level_input);

        if (tutorObject != null) {
            setTutorInfo();
        }
        initialiseDaysDropdown();
        setButtons();
    }

    /**
     * Adds info for the tutor fetched by the intent into the relevant fields
     */
    private void setTutorInfo() {
        if (tutorObject.getFirstName() != null) {
            firstnameInput.setText(tutorObject.getFirstName());
        }
        if (tutorObject.getLastName() != null) {
            lastnameInput.setText(tutorObject.getLastName());
        }
        if (tutorObject.getPhone() != null) {
            phoneInput.setText(tutorObject.getPhone());
        }
        if (tutorObject.getLocation() != null) {
            locationInput.setText(tutorObject.getLocation());
        }
        if (tutorObject.getEmail() != null) {
            viewEmail.setText(tutorObject.getEmail());
        }

        // Create layout manager and SubjectListAdapter to populate the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        subjectRecyclerView.setLayoutManager(layoutManager);
        // If tutorObject exists and has subjects, populate subject list adapter
        if (tutorObject != null && tutorObject.getSubjects() != null) {
            subjectListAdapter = new SubjectListAdapter(this, tutorObject.getSubjects());
        } else {
            subjectListAdapter = new SubjectListAdapter(this, new ArrayList<Subject>());
        }
        subjectRecyclerView.setAdapter(subjectListAdapter);
    }

    /**
     * Sets onClickListeners for buttons on page
     */
    private void setButtons() {

        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Gets the updated tutor values within a new Tutor object and adds them to the database
             * @param view
             */
            @Override
            public void onClick(View view) {
                tutorObject.setFirstName(firstnameInput.getText().toString());
                tutorObject.setLastName((String) lastnameInput.getText().toString());
                tutorObject.setPhone(phoneInput.getText().toString());
                tutorObject.setLocation(locationInput.getText().toString());
                ArrayList<Integer> updatedDays = new ArrayList<Integer>();
                // Translate dayList (Integers in range 0-6) to our Tutor object Day format (1-7)
                for (Integer day : dayList) {
                    updatedDays.add(day + 1);
                }
                tutorObject.setDays(updatedDays);
                tutorObject.setSubjects(subjectListAdapter.subjects);

                FirebaseFetcher.getInstance().updateTutor(tutorObject);
                Toast.makeText(getApplicationContext(), "Successfully Updated Tutor Info", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        ImageButton addSubjectButton = (ImageButton) findViewById(R.id.add_subject_button);
        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (disciplineInput.getText() != "" && levelInput.getText() != "") {
                    for (String level : levelInput.getText().toString().split(",")) {
                        // We check if a subject already exists for this tutor with matching discipline and level values
                        boolean matching = false;
                        for (Subject sub : subjectListAdapter.subjects) {
                            if (sub.getDiscipline().equals(disciplineInput.getText().toString())
                                    && sub.getLevel().equals(level)) {
                                matching = true;
                            }
                        }
                        // If there is no matching subject already, we add to the list
                        if (!matching) {
                            System.out.println(subjectListAdapter.subjects);
                            subjectListAdapter.subjects.add(new Subject(disciplineInput.getText().toString(), level));
                            subjectListAdapter.notifyItemInserted(subjectListAdapter.subjects.size() - 1);
                        }
                    }
                }
                // Reset Fields
                disciplineInput.setText("");
                levelInput.setText("");
            }
        });
    }

    /**
     * Creates a multi-select drop-down for selecting days
     * Code sourced from https://www.geeksforgeeks.org/how-to-implement-multiselect-dropdown-in-android/
     */
    private void initialiseDaysDropdown() {
        // Assign variable
        daysDropdown = (TextView) findViewById(R.id.days_dropdown);

        // Initialize selected language array
        selectedDays = new boolean[dayArray.length];

        if (tutorObject != null && tutorObject.getDays() != null) {
            for (Integer dayNum : tutorObject.getDays()) {
                dayList.add(dayNum - 1);
            }
            daysDropdown.setText(tutorObject.getDaysInStringFormat().toString().substring(1, tutorObject.getDaysInStringFormat().toString().length() - 1));
            for (int j = 0; j < selectedDays.length; j++) {
                if (dayList.contains(j)) {
                    selectedDays[j] = true;
                } else {
                    selectedDays[j] = false;
                }
            }
            Log.d(TAG, "Day List: " + dayList.toString());
        }
        daysDropdown.setOnClickListener(new View.OnClickListener() {
            /**
             * Shows a multi-select dialog for selecting days of the week
             *
             * @param view The view that was clicked
             */
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TutorEdit.this);

                // Set title
                builder.setTitle("Select Days");

                // Set dialog non-cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(dayArray, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    /**
                     * Updates the selected days list based on the user's selection
                     * @param dialogInterface The dialog interface
                     * @param i The index of the selected item
                     * @param b The checked state of the item
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // Check condition
                        if (b) {
                            // When checkbox selected
                            // Add position in day list
                            dayList.add(i);
                            // Sort array list
                            Collections.sort(dayList);
                        } else {
                            // When checkbox unselected
                            // Remove position from day list
                            dayList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    /**
                     * Updates the selected days text view with the selected days
                     * @param dialogInterface The dialog interface
                     * @param i The button clicked (positive button)
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // Use for loop
                        for (int j = 0; j < dayList.size(); j++) {
                            // Concatenate array value
                            stringBuilder.append(dayArray[dayList.get(j)]);
                            // Check condition
                            if (j != dayList.size() - 1) {
                                // When j value not equal to day list size - 1
                                // Add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // Set text on textView
                        daysDropdown.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog when the Cancel button is clicked
                     * @param dialogInterface The dialog interface
                     * @param i The button clicked (negative button)
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Dismiss dialog
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    /**
                     * Clears the selected days and the text view when the Clear All button is clicked
                     * @param dialogInterface The dialog interface
                     * @param i The button clicked (neutral button)
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Use for loop
                        for (int j = 0; j < selectedDays.length; j++) {
                            // Remove all selection
                            selectedDays[j] = false;
                            // Clear day list
                            dayList.clear();
                            // Clear text view value
                            daysDropdown.setText("");
                        }
                    }
                });

                // Show dialog
                builder.show();
            }
        });
    }
}