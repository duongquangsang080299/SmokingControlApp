package com.demo.smokingcontrolapp.activities.chat_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.activities.chat.ChatActivity;
import com.demo.smokingcontrolapp.activities.search.SearchActivity;
import com.demo.smokingcontrolapp.adapters.SearchAdapter;
import com.demo.smokingcontrolapp.adapters.UserListAdapter;
import com.demo.smokingcontrolapp.models.User;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Global;
import com.demo.smokingcontrolapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity implements UserListAdapter.IListener {

    private Toolbar tbChatList;
    private RecyclerView rvChatList;
    private UserListAdapter adapter;
    private final ChatListViewModel viewModel = new ChatListViewModel(getApplication());
    private final Utils utils = new Utils();
    private Button btnDoctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        // init view
        init();

        //get all user have messenger with user.
        UserInfoApp infoApp = utils.getUserInfo(this);

        viewModel.getAllUser(infoApp.getuID(), userList -> {
            List<User> users;
            users = userList;
            adapter = new UserListAdapter(users, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatListActivity.this);
            rvChatList.setAdapter(adapter);
            rvChatList.setLayoutManager(linearLayoutManager);
        });

        //event
        evenClick();
    }

    private void evenClick() {
        tbChatList.setNavigationOnClickListener(v -> finish());

        btnDoctor.setOnClickListener(v -> {
            Intent i = new Intent(ChatListActivity.this, ChatActivity.class);
            i.putExtra(ChatActivity.USER_ID, Global.ADMIN_ID);
            startActivity(i);
        });
    }

    private void init() {
        //toolBar
        tbChatList = findViewById(R.id.tb_chat_list);
        tbChatList.setTitle("");
        tbChatList.setNavigationIcon(R.drawable.ic_baseline_west_24);
        setSupportActionBar(tbChatList);

        rvChatList = findViewById(R.id.rv_chat_list);

        btnDoctor = findViewById(R.id.btnDoctor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            startActivity(new Intent(ChatListActivity.this, SearchActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(User user) {
        Log.d("TAG", "onClickListener: " + user.getEmail());
        Intent i = new Intent(ChatListActivity.this, ChatActivity.class);
        i.putExtra(ChatActivity.USER_ID, user.getUID());
        startActivity(i);
    }
}