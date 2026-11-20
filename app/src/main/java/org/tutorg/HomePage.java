package org.tutorg;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genius.multiprogressbar.MultiProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.tutorg.chat.UserChat;
import org.tutorg.data.FirebaseFetcher;
import org.tutorg.data.Tutor;
import org.tutorg.data.TutorSearchTree;
import org.tutorg.data.User;
import org.tutorg.databinding.ActivityHomePageBinding;
import org.tutorg.search.parser.EntryResults;
import org.tutorg.search.parser.Parser;
import org.tutorg.search.tokenizer.Tokenizer;
import org.tutorg.tutor_info.TutorEdit;
import org.tutorg.ui.login.LoginActivity;

import java.util.List;

/**
 * Home Page for Application, containing search bar and progress bar for finding tutors
 * @author Eliz So, Samuel Seymour (u6959744), Samuel Macauley
 */

public class HomePage extends AppCompatActivity {
    private static final String TAG = "HomePage";
    private RecyclerView mRecyclerView;
    private ActivityHomePageBinding binding;
    private SearchResultsAdapter mSearchAdapter;
    private EditText mTutorTextSearch;

    //User and tutor (if exists) objects associated with current user, to be passed as parcelable to change details page
    FirebaseUser cur = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://tutorg-635a1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private Tutor currentTutor;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TutorSearchTree.getInstance();

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initialise recycler view for search results
        mRecyclerView = (RecyclerView) findViewById(R.id.usersListRecyclerView);
        mSearchAdapter = new SearchResultsAdapter();

        //Gets tutor object associated with current user

        mTutorTextSearch = findViewById(R.id.textSearch);
        // Search box entry listener.
        mTutorTextSearch.addTextChangedListener(
                new TextWatcher() {

                    final MultiProgressBar progressBar = findViewById(R.id.progressBar);
                    TextView progressText = findViewById(R.id.progressText);

                    @Override
                    public void beforeTextChanged(
                            CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    /**
                     * After text is changed in the search box.
                     *
                     * @author name: Samuel Macauley UID: u7486259 and name: Samuel Seymour UID: u6959744
                     * @param editable the search results entry.
                     */
                    @Override
                    public void afterTextChanged(Editable editable) {
                        // Set the desired progress value between 0 and 100

                        String searchEntry = editable.toString();

                        // Tokenize search entry.
                        Tokenizer tokenizer = new Tokenizer(searchEntry);
                        EntryResults entryResults = new EntryResults();

                        // Parse the tokenized search entry.
                        try {
                            // Remove partially invalid exclamation point in textSearch if it exists.
                            mTutorTextSearch.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

                            new Parser(tokenizer, entryResults);

                            String location = entryResults.getLocation();

                            String person = entryResults.getPerson();

                            List<String> days = entryResults.getDays();

                            // Set the desired progress value between 0 and 10
                            int progress =
                                    getNewProgress(
                                            location, person,
                                            days) / 10;
                            progressStepperHelper(progressBar, true, progress);

                            if (location == null) {
                                progressText.setText("Now Try IN Your Location");
                            } else if (days == null) {
                                progressText.setText("Now Try ON a Particular Day");
                            } else if (person == null) {
                                progressText.setText("Now Try WITH a Particular Person");
                            } else {
                                progressText.setText("Select A Tutor or Keep Searching");
                            }

                        } catch (Parser.IllegalProductionException e) {

                            String discipline = entryResults.getDiscipline();
                            String subjectLevel = entryResults.getSubjectLevel();

                            // If there is no partial valid search.
                            if (discipline == null && subjectLevel == null) {
                                // Display "no search results"
                                progressStepperHelper(progressBar, true, 1);
                                progressText.setText("Type in a Subject and Year Level");

                                return;
                            } else if (subjectLevel == null) {
                                progressStepperHelper(progressBar, true, 2);
                                progressText.setText("Start With Your Subject and Year Level :)");
                            }

                            // Display exclamation mark in EntryResults.
                            mTutorTextSearch.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.baseline_error_outline_24,0);
                        }
                        // Find tutors based on entryResults and display.
                        mSearchAdapter.searchChanged(entryResults);
                    }
                });

        // We create a grid layout, with the "spanCount" which I believe to be is how many
        // columns per row (i.e on the screen).
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(new SpacingDecoration(getApplicationContext(), 5));

        mRecyclerView.setAdapter(mSearchAdapter);
    }

    /**
     * When navigating back to the home page, update the current tutor object
     */
    @Override
    protected void onResume(){
        super.onResume();

    }

    /**
     * Method to show items in app bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle menu button action
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_button) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomePage.this, LoginActivity.class));
        }
        if (id == R.id.chat_button) {
            startActivity(new Intent(HomePage.this, UserChat.class));
        } else if (id == R.id.add_listing_button) {
            Intent tutorEditIntent = new Intent(HomePage.this, TutorEdit.class);
            setCurrentTutor();
            if (currentTutor == null){
                currentTutor = new Tutor(cur.getEmail());
                FirebaseFetcher.getInstance().addTutor(currentTutor);
            }
            tutorEditIntent.putExtra("tutor_object",currentTutor);
            startActivity(tutorEditIntent);
        }
        return super.onOptionsItemSelected(item);
    }

     /**
      * this is a dummy method to create a node in firebase, so we can check on the receiving
      * side that the things are working..
      */
    private void createDummyNode() {
        // DatabaseReference ref
        //         = FirebaseFetcher.getInstance().database.getReference("tutor");
        // ArrayList<Integer> days = new ArrayList<>();
        // days.add(1);
        // days.add(2);

        // HashMap<String, Object> tutorNode = new HashMap<>();
        // ArrayList<Subject> subjs = new ArrayList<>();
        // subjs.add(new Subject("Gamma", "1"));
        // tutorNode.put("2500", new Tutor("Big", "Professor",
        //         "myemail@gmail.com", "Canberra", "919292939",
        //         days, subjs));

        // ref.updateChildren(tutorNode);
    }

    /**
     * Determines the level of progress to fill on the progress bar based on the number of fields parsed
     * @param location
     * @param person
     * @param days
     * @return the percentage of the progress bar to be filled
     */
    public int getNewProgress(String location, String person, List<String> days) {
        boolean locationNotNull = (location != null);
        boolean personNotNull = (person != null);
        boolean daysNotNull = (days != null);

        int countNotNull = 0;
        if (locationNotNull)
            countNotNull = countNotNull + 1;
        if (personNotNull)
            countNotNull = countNotNull + 1;
        if (daysNotNull && !days.isEmpty())
            countNotNull = countNotNull + 1;

        return ((countNotNull+1) * 100) / 4;
    }

    /**
     * Helps the progress bar step forward and backwards with stepCount, and clears the progress bar
     * @param progressBar
     * @param clear
     * @param stepCount
     */
   private void progressStepperHelper(MultiProgressBar progressBar, boolean clear, int stepCount) {
        if (clear) {
            progressBar.clear();
        }

        for (int i = 0; i < stepCount; ++i) {
            progressBar.next();
        }

       for (int i = progressBar.getCurrentStep(); i > stepCount; --i) {
           progressBar.next();
       }
   }

    /**
     * Gets the tutor object matching the email of the current user if it exists. If not, currentTutor remains null
     */
    private void setCurrentTutor() {
        FirebaseFetcher.getInstance().getTutors().forEach(tutor -> {
            if (tutor.getEmail().equals(cur.getEmail())) {
                Log.d(TAG, "Found the current tutor: " + tutor.getFirstName());
                currentTutor = tutor;
            }
        });
    }
}
