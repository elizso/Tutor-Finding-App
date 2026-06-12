package org.tutorg.chat;

import static android.view.Gravity.END;
import static android.view.Gravity.START;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.tutorg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageAdapter displays each of the message in correct colour and orientation.
 * @author Eliz So (u7489812) and Sam Macauley
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageVH>{

    private Context context;
    private List<Message> messages;

    public MessageAdapter(Context context){
        this.context = context;
        this.messages = new ArrayList<>();

    }

    public void add(Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    public void addAll(List<Message> messages){
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }
    public void clear(){
        messages = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MessageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageVH holder, int position) {
        Message message = messages.get(position);
        holder.msg.setText(message.getMessage());
        if (message.getSender() != null && message.getSender().equals(FirebaseAuth.getInstance().getUid())){
            holder.main.setBackgroundResource(R.drawable.chat_bubble_teal);
            holder.msg.setTextColor(context.getResources().getColor(R.color.white));
            holder.mainParent.setGravity(END);
        } else {
            holder.main.setBackgroundResource(R.drawable.chat_bubble_grey);
            holder.msg.setTextColor(context.getResources().getColor(R.color.black));
            holder.mainParent.setGravity(START);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageVH extends RecyclerView.ViewHolder{
        private TextView msg;
        private LinearLayout main;
        private LinearLayout mainParent;
        public MessageVH(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.messageLayout);
            mainParent = itemView.findViewById(R.id.messageLayoutParent);
        }
    }

}
