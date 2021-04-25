package com.demo.smokingcontrolapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.models.Chat;
import com.demo.smokingcontrolapp.utils.Utils;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    public static final int MSG_TYPE_RIGHT = 1;
    public static final int MSG_TYPE_LEFT = 0;
    private List<Chat> chats;
    private String uid;
    public ChatAdapter(List<Chat> chats, String uid) {
        this.chats = chats;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left, parent, false);
        }
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        String message = chats.get(position).getMessage();
        holder.txvMessage.setText(message);
        holder.txvCreateTime.setText(Utils.convertLongToDate(chats.get(position).getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ChatHolder extends RecyclerView.ViewHolder {
        TextView txvMessage;
        TextView txvCreateTime;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            txvMessage = itemView.findViewById(R.id.txv_message);
            txvCreateTime = itemView.findViewById(R.id.txv_create_time);
        }
    }

    @Override
    public int getItemViewType(int position) {

        //todo: add uid here.
        if (chats.get(position).getSender().equals(uid)){
            return MSG_TYPE_RIGHT;
        }else
            return MSG_TYPE_LEFT;
    }
}
