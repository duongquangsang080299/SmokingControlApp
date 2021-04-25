package com.demo.smokingcontrolapp.activities.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.adapters.ChatAdapter;
import com.demo.smokingcontrolapp.models.Chat;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Global;
import com.demo.smokingcontrolapp.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbarChat;
    private ChatViewModel viewModel;
    private ImageView imgButtonSend;
    private EditText edtMessage;
    private RecyclerView rvMessages;
    private ProgressBar progressBar;
    private final Utils utils = new Utils();
    private String uid = null;
    private ChatAdapter adapter;
    private List<Chat> chats = new ArrayList<>();
    public static final String USER_ID = "UID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        uid = intent.getStringExtra(USER_ID);
        Log.d("TAG", "onCreate: " + uid);
        // init
        init();

        viewModel.readMessageOfAdmin(utils.getUserInfo(this).getuID(), uid, chatList -> {
            chats.clear();
            chats = chatList;
            adapter = new ChatAdapter(chatList, utils.getUserInfo(this).getuID());
            rvMessages.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        });

        //even click
        event();
    }

    // event click
    private void event() {
        imgButtonSend.setOnClickListener(v -> {
            String message = edtMessage.getText().toString().trim();
            if (message.equals("")) {
                Toast.makeText(this, "Message is not empty", Toast.LENGTH_SHORT).show();
            } else {
                long currentTime = System.currentTimeMillis();
                UserInfoApp user = utils.getUserInfo(this);
                viewModel.sendMessage(message, user.getuID(), uid, currentTime, bool -> {
                    if (bool) {
                        edtMessage.getText().clear();
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        toolbarChat.setNavigationOnClickListener(v -> finish());
    }

    // init
    private void init() {

        //viewModel
        viewModel = new ChatViewModel(getApplication());

        //toolbar
        toolbarChat = findViewById(R.id.tb_chat);
        toolbarChat.setNavigationIcon(R.drawable.ic_baseline_west_24);
        toolbarChat.setTitle(getResources().getString(R.string.chat));
        toolbarChat.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbarChat);

        imgButtonSend = findViewById(R.id.img_send);
        edtMessage = findViewById(R.id.edt_message);

        //RecyclerView
        rvMessages = findViewById(R.id.rv_chat);
        rvMessages.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.progress_bar_chat);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}