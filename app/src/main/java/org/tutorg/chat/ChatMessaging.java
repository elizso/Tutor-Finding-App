package org.tutorg.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.tutorg.R;
import org.tutorg.databinding.ActivityChatMessagingBinding;

/**
 * ChatMessaging is the interface for users to chat. It finds all
 * the required information for MessageAdapter to display the messages.
 * @author Eliz So (u7489812)
 */
public class ChatMessaging extends AppCompatActivity {

    ActivityChatMessagingBinding binding;
    String chatID;
    DatabaseReference databaseReference;
    MessageAdapter messageAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://tutorg-635a1-default-rtdb.asia-southeast1.firebasedatabase.app");


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
        binding = ActivityChatMessagingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        chatID = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        setTitle(name);

        databaseReference = database.getReference("chatId").child(chatID).child("Messages");

        messageAdapter = new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String msg = dataSnapshot.child("message").getValue(String.class);
                    String sender = dataSnapshot.child("sender").getValue(String.class);
                    Message message = new Message(sender, msg);
                    messageAdapter.add(message);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message= binding.messageEd.getText().toString();
                if (message.trim().length() > 0){
                    sendMessage(message);
                }
                binding.messageEd.setText("");
            }
        });


    }
    private void sendMessage(String message){

        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();

        Message msg = new Message(FirebaseAuth.getInstance().getUid(), message);
        messageAdapter.add(msg);
        databaseReference.child(ts).setValue(msg);


    }
}