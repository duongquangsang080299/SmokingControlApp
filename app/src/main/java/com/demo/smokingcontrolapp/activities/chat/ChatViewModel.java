package com.demo.smokingcontrolapp.activities.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.firebase.Database;
import com.demo.smokingcontrolapp.models.Chat;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    Database database;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        database = new Database();
    }

    // sendMessage
    public void sendMessage(String message, String sender, String receiver, long createTime, ISendMessage iSendMessage){
        Chat chat = new Chat(message, sender, receiver, createTime);
        database.sendMessage(chat, iSendMessage::onResult);
    }

    // readMessage
    public void readMessage(String uid, IReadMessage iReadMessage){
        database.readMessage(uid, iReadMessage::onResult);
    }

    public void readMessageOfAdmin(String sender, String reveiver, IReadMessage iReadMessage){
        database.readMessageOfAdmin(sender, reveiver, iReadMessage::onResult);
    }

    public interface ISendMessage{
        void onResult(Boolean bool);
    }

    public interface IReadMessage{
        void onResult(List<Chat> chatList);
    }
}
