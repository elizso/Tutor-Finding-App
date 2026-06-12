package org.tutorg.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.tutorg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * UserAdapter displays each of the chat that a user has.
 * @author Eliz So (u7489812)
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.VH>{

    private Context context;
    private List<Chat> chatInfo;

    public UserAdapter(Context context){
        this.context = context;
        this.chatInfo = new ArrayList<>();

    }

    public void add(Chat chat){
        chatInfo.add(chat);
        notifyDataSetChanged();
    }

    public void addAll(List<Chat> chatInfo){
        chatInfo.addAll(chatInfo);
        notifyDataSetChanged();
    }
    public void clear(){
        chatInfo = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Chat chat = chatInfo.get(position);
        holder.name.setText(chat.getName());
        holder.message.setText(chat.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatMessaging.class);
                intent.putExtra("id", chat.getChatID());
                intent.putExtra("name", chat.getName());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return chatInfo.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView name, message;
        public VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chatUsername);
            message = itemView.findViewById(R.id.chatNewMessage);
        }
    }

}
