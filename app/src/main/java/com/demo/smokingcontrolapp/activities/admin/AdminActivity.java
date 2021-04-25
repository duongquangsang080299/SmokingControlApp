package com.demo.smokingcontrolapp.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.activities.chat.ChatActivity;
import com.demo.smokingcontrolapp.adapters.UserListAdapter;
import com.demo.smokingcontrolapp.models.User;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements UserListAdapter.IListener {

    private final AdminViewModel viewModel = new AdminViewModel(getApplication());
    private RecyclerView rvListUser;
    private UserListAdapter adapter;
    private Toolbar toolbar;
    private Utils utils = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        // init
        init();

        // get all user
        UserInfoApp infoApp = utils.getUserInfo(this);
        viewModel.getAllUser(infoApp.getuID(), user -> {
            List<User> users = new ArrayList<>();
            users = user;
            adapter = new UserListAdapter(users, this);
            adapter.notifyDataSetChanged();
            rvListUser.setAdapter(adapter);
        });

        //back to Sign in
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    // init
    private void init() {

        //toolbar
        toolbar = findViewById(R.id.tb_admin);
        toolbar.setTitle("Admin");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_west_24);
        setSupportActionBar(toolbar);

        //view
        rvListUser = findViewById(R.id.rv_list_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListUser.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClickListener(User user) {
        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra(ChatActivity.USER_ID, user.getUID());
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}