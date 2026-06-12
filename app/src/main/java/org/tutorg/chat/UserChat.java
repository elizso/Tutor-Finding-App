package org.tutorg.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.tutorg.R;
import org.tutorg.databinding.ActivityUserChatBinding;

import java.util.ArrayList;


/**
 * UserChat gets all the Chat information for the UserAdapter to use.
 * @author Eliz So (u7489812)
 */
public class UserChat extends AppCompatActivity {

    ActivityUserChatBinding binding;
    UserAdapter userAdapter;
    private ArrayList<Chat> chatInfo = new ArrayList<>();
    private DatabaseReference ref;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://tutorg-635a1-default-rtdb.asia-southeast1.firebasedatabase.app");


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
        binding = ActivityUserChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAdapter = new UserAdapter(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding.recycleView.setAdapter(userAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
        if (curUser == null) return;
        String email = curUser.getEmail();
        if (email == null) return;

        Query query = database.getReference("user").child(curUser.getUid()).child("chatId");
        System.out.println("in user's chatId");
        Task<DataSnapshot> dataTask = query.get();

        dataTask.addOnSuccessListener(result -> {
            DataSnapshot data = dataTask.getResult();

            for (DataSnapshot chat : data.getChildren()){

                String chatID = chat.getValue(String.class);

                Query chatQuery = database.getReference("chatId").child(chatID);
                System.out.println("in chatId to get message");
                Task<DataSnapshot> chatDataTask = chatQuery.get();

                chatDataTask.addOnSuccessListener(chatResult -> {
                    DataSnapshot chatData = chatDataTask.getResult();

                    String email1 = chatData.child("email1").getValue(String.class);
                    String email2 = chatData.child("email2").getValue(String.class);

                    String name = email1;

                    if (email1 == null || email1.equals(curUser.getEmail()))
                        name = email2;

                    ref = chat.getRef();

                    DatabaseReference lastRef = database.getReference("chatId").child(chatID).child("Messages");
                    System.out.println(lastRef.getRef());
                    Query lastMessage = lastRef.limitToLast(1);
                    Task<DataSnapshot> messageTask = lastMessage.get();
                    System.out.println("last child");

                    String finalName = name;

                    messageTask.addOnSuccessListener(messageResult -> {
                        for (DataSnapshot snapshot : messageTask.getResult().getChildren()){
                            String message = snapshot.child("message").getValue(String.class);
                            System.out.println(snapshot.getKey());
                            System.out.println(messageTask.getResult().getChildrenCount());
                            System.out.println(message);
                            chatInfo.add(new Chat(finalName, chatID, message));
                            userAdapter.add(new Chat(finalName, chatID, message));

                        }
                    });
                    messageTask.addOnFailureListener(messageResult -> {
                        chatInfo.add(new Chat(finalName, chatID));
                        userAdapter.add(new Chat(finalName, chatID));
                    });
//                    lastMessage.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                            if (task.isSuccessful()) {
//                                System.out.println("success");
//
//                            } else {
//
//                            }
//
//                            System.out.println("done with onComplete");
//                        }
//
//
//                    });

                    System.out.println("done with chatDataTask");

                });






            }
            System.out.println("out");








            //setupListener();



        });


    }

    private void setupListener(){

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String name = snapshot.child("name").getValue(String.class);
                String chatID = snapshot.child("chatID").getValue(String.class);


                DatabaseReference lastRef = database.getReference("chatID");
                if (chatID == null) throw new RuntimeException("chatID is null");
                Query lastMessage = lastRef.child(chatID).limitToLast(1);

                lastMessage.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Chat newChatInfo = new Chat(name, chatID);
                        if (task.isSuccessful()) {
                            for (DataSnapshot snapshot : task.getResult().getChildren()){
                                String message = snapshot.getValue(String.class);
                                newChatInfo.setMessage(message);
                                chatInfo.add(new Chat(name, chatID, message));
                            }
                        }
                        chatInfo.add(newChatInfo);
                        userAdapter.add(newChatInfo);
                    }
                });


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String chatID = snapshot.child("chatID").getValue(String.class);


                for (Chat chat: chatInfo){

                    chatInfo.remove(chat);

                    if (chatID == null) throw new RuntimeException("chatID is null");
                    if (!chatID.equals(chat.getChatID())) continue;

                    DatabaseReference lastRef = database.getReference("chatID");

                    Query lastMessage = lastRef.child(chatID).limitToLast(1);

                    lastMessage.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DataSnapshot snapshot : task.getResult().getChildren()){
                                    String message = snapshot.getValue(String.class);
                                    chat.setMessage(message);
                                }
                            }
                            userAdapter.clear();
                            chatInfo.add(chat);
                            userAdapter.addAll(chatInfo);

                        }
                    });

                    break;

                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}