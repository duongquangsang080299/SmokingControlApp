package com.demo.smokingcontrolapp.activities.achievement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.adapters.TargetListAdapter;
import com.demo.smokingcontrolapp.models.Target;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends AppCompatActivity {

    private RecyclerView achievements;
    private TargetListAdapter adapter;
    private List<Target> targets = new ArrayList<>();
    private Toolbar toolbar;
    private final AchievementViewModel viewModel = new AchievementViewModel(getApplication());
    private ProgressBar progressBar;
    private Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        // init
        init();

        //get all achievement of user
        UserInfoApp userInfoApp = utils.getUserInfo(this);

        viewModel.getAllAchievement(userInfoApp.getuID(), targetList -> {
            targets = targetList;
            adapter = new TargetListAdapter(targets);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            achievements.setAdapter(adapter);
            achievements.setLayoutManager(linearLayoutManager);
            progressBar.setVisibility(View.INVISIBLE);
        });

        // back to Home screen
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    // init
    public void init() {

        //toolbar
        achievements = findViewById(R.id.rv_target);
        toolbar = findViewById(R.id.tb_target);
        toolbar.setTitle(R.string.thanh_tich);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_west_24);
        setSupportActionBar(toolbar);

        //
        progressBar = findViewById(R.id.progress_bar_achievement);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}