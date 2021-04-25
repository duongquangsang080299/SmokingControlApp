package com.demo.smokingcontrolapp.activities.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.activities.sign_in.SignInActivity;
import com.demo.smokingcontrolapp.adapters.TargetListAdapter;
import com.demo.smokingcontrolapp.models.Target;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Utils;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView profileHistoriesList;
    private TargetListAdapter adapter;
    private final ProfileViewModel viewModel = new ProfileViewModel(getApplication());
    private List<Target> targets = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView txvCountOfComplete, txvCountOfNotComplete, txvUserName, txvEmail, txvPhoneNumber;
    private Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //init
        init();

        // get all histories

        UserInfoApp userInfoApp = utils.getUserInfo(this);
        viewModel.getUser(userInfoApp.getuID(), user -> {
            txvPhoneNumber.setText(user.getPhoneNumber() +"");
            txvEmail.setText(user.getEmail());
            txvUserName.setText(user.getUserName());
        });

        viewModel.getAllTarget(userInfoApp.getuID(), (targetList, complete, notComplete) -> {

            targets = targetList;
            adapter = new TargetListAdapter(targets);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            profileHistoriesList.setAdapter(adapter);
            profileHistoriesList.setLayoutManager(linearLayoutManager);

            txvCountOfNotComplete.setText(notComplete + "");
            txvCountOfComplete.setText(complete + "");

            progressBar.setVisibility(View.INVISIBLE);
        });

        toolbar.setNavigationOnClickListener(v -> finish());

    }

    public void init(){
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        profileHistoriesList = findViewById(R.id.profile_histories);
        toolbar.setTitle("Profile");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_west_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //
        progressBar = findViewById(R.id.progress_bar_profile);
        txvCountOfComplete = findViewById(R.id.txv_done);
        txvCountOfNotComplete = findViewById(R.id.txv_false);
        txvEmail = findViewById(R.id.txv_email);
        txvPhoneNumber = findViewById(R.id.txv_phone_number);
        txvUserName = findViewById(R.id.txv_user_name);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Utils utils = new Utils();
            utils.setLogout(true, getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}