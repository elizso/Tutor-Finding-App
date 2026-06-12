package org.tutorg.tutor_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.tutorg.R;
import org.tutorg.chat.ChatMessaging;
import org.tutorg.data.FirebaseFetcher;
import org.tutorg.data.Subject;
import org.tutorg.data.Tutor;
import org.tutorg.data.TutorSearchTree;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Lachlan McDonald, Samuel Seymour
 * AI Tools aided in docstrings
 */

/**
 * The activity for displaying tutor information.
 * Displays the information of a selected tutor and allows for editing or initiating a chat.
 * Implements the Observer interface to receive updates from TutorSearchTree.
 */
public class TutorInfoPage extends AppCompatActivity implements Observer {
    private static final String TAG = "TutorInfoPage";
    private Tutor tutorObject;
    private boolean chat = false;

    FirebaseUser cur = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://tutorg-635a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

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
        setContentView(R.layout.activity_tutor_info_page);

        // setup page

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        tutorObject = (Tutor)intent.getParcelableExtra("tutor_object");
        Log.d(TAG, "Clicked on: " + tutorObject.getFirstName());
        setTutorInfo(tutorObject);

        Button editButton = (Button) findViewById(R.id.edit_button);

        System.out.println(cur.getUid());

        if (cur != null && !tutorObject.getEmail().equals(cur.getEmail())){
            chat = true;
            editButton.setText("Match");
        }

        // placeholder
        if (tutorObject.getEmail().equals("issac@cambridge.edu")){
            editButton.setAllowClickWhenDisabled(false);
            editButton.setEnabled(false);
        }

        setButtons();
        TutorSearchTree.getInstance().addObserver(this);

    }

    /**
     * Adds info for the tutor fetched by the intent into the relevant fields
     * @param tutor - the Tutor object fetched by the intent
     */
    private void setTutorInfo(Tutor tutor){

        // sets the fields in tutor activity to the current tutors attributes
        ArrayList<Integer> array = new ArrayList<>();
        array.add(R.drawable.user1);
        array.add(R.drawable.user2);
        array.add(R.drawable.user3);
        array.add(R.drawable.user4);
        array.add(R.drawable.user5);
        TextView firstname = (TextView)findViewById(R.id.first_name);
        TextView lastname = (TextView)findViewById(R.id.last_name);
        TextView phone = (TextView)findViewById(R.id.phone);
        TextView location = (TextView)findViewById(R.id.location);
        TextView daysAvailable = (TextView)findViewById(R.id.days_available);
        ImageView profileImageView = findViewById(R.id.profileImageView);
        profileImageView.setImageResource(array.get(tutorObject.getId() % 5));
        firstname.setText(tutor.getFirstName());
        lastname.setText(tutor.getLastName());
        phone.setText(tutor.getPhone());
        location.setText(tutor.getLocation());
        daysAvailable.setText(tutor.getDays().toString());
        String dayString = tutor.getDaysInStringFormat().toString().substring(1,tutor.getDaysInStringFormat().toString().length()-1);
        daysAvailable.setText(dayString);
        // Create layout manager and SubjectListAdapter to populate the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView subjectRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        subjectRecyclerView.setLayoutManager(layoutManager);
        // If tutorObject exists and has subjects, populate subject list adapter
        SimpleSubjectListAdapter subjectListAdapter;
        if (tutorObject != null && tutorObject.getSubjects() != null) {
            subjectListAdapter = new SimpleSubjectListAdapter(this, tutorObject.getSubjects());
        } else {
            subjectListAdapter = new SimpleSubjectListAdapter(this, new ArrayList<Subject>());
        }
        subjectRecyclerView.setAdapter(subjectListAdapter);
    }

    /**
     * Sets onClickListeners for buttons on page
     */
    private void setButtons(){

        boolean finalChat = chat;
        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (finalChat){



                    StringBuilder id = new StringBuilder();
                    id.append(cur.getUid());

                    StringBuilder tutorUid = new StringBuilder();

                    DatabaseReference findRef = database.getReference("user");

                    Query findQuery = findRef.orderByChild("email").equalTo(tutorObject.getEmail());
                    Task<DataSnapshot> findTask = findQuery.get();
                    findTask.addOnSuccessListener(result -> {
                        for (DataSnapshot user: result.getChildren()){
                            id.append(user.getKey());
                            tutorUid.append(user.getKey());
                            System.out.println(id);
                            System.out.println(user.getKey());

                            DatabaseReference userRef = database.getReference("user").child(cur.getUid());

                            DatabaseReference tutorRef = database.getReference("user").child(tutorUid.toString());


                            Long tsLong = System.currentTimeMillis();
                            String ts = tsLong.toString();

                            userRef.child("chatId").child(ts).setValue(id.toString());
                            tutorRef.child("chatId").child(ts).setValue(id.toString());

                            //set email for messages
                            database.getReference("chatId").child(id.toString()).child("email1").setValue(cur.getEmail());
                            database.getReference("chatId").child(id.toString()).child("email2").setValue(tutorObject.getEmail());
                            database.getReference("chatId").child(id.toString()).child("Messages").setValue("");

                            Intent chatIntent = new Intent(TutorInfoPage.this, ChatMessaging.class);
                            chatIntent.putExtra("id", id.toString());
                            startActivity(chatIntent);
                        }


                    });
                    findTask.addOnFailureListener(result -> {

                    });


                } else {
                    Intent tutorEditIntent = new Intent(TutorInfoPage.this, TutorEdit.class);
                    tutorEditIntent.putExtra("tutor_object", tutorObject);
                    startActivity(tutorEditIntent);
                }
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d(TAG, "TutorObject has been updated");
        int index = tutorObject.getId();
        tutorObject.copy(FirebaseFetcher.getInstance().getTutors().get(index));
        setTutorInfo(tutorObject);
    }
}
