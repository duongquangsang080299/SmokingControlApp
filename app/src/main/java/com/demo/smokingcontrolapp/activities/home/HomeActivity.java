package com.demo.smokingcontrolapp.activities.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.activities.achievement.AchievementActivity;
import com.demo.smokingcontrolapp.activities.chat.ChatActivity;
import com.demo.smokingcontrolapp.activities.chat_list.ChatListActivity;
import com.demo.smokingcontrolapp.activities.profile.ProfileActivity;
import com.demo.smokingcontrolapp.activities.target.TargetActivity;
import com.demo.smokingcontrolapp.models.Target;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Utils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    LinearLayout cardViewProfile, cardViewChat, cardViewAchievement;
    ConstraintLayout cardViewTarget;
    HomeViewModel viewModel = new HomeViewModel(getApplication());
    Utils utils = new Utils();
//    Button btnBackToSignIn;
    TextView txvCurrentTarget, txvStartDate, txvEndDate, txvCountOfTarget, txvUsed, txvMotivation;
    private ProgressBar progressBar, progressMotivation;
    int countOfClick = 0;

    //target
    boolean isHaveTarget;
    Target target;
    String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        UserInfoApp infoApp = utils.getUserInfo(this);

        viewModel.checkTarget(infoApp.getuID(), (result, uid, target, postId) -> {
            progressMotivation.setVisibility(View.INVISIBLE);
            if (target != null) {
                isHaveTarget = true;
                this.target = target;
                this.postId = postId;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                txvCurrentTarget.setText("Target name: " + target.getTargetName());
                txvUsed.setText("Used: " + target.getCountOfCigaretteSmoked());
                txvCountOfTarget.setText("Count of target: " + target.getCountOfCigarette());
                Date startDate = new Date(target.getStartDate());
                txvStartDate.setText("Start date: " + dateFormat.format(startDate));
                Date endDate = new Date(target.getEndDate());
                txvEndDate.setText("End date: " + dateFormat.format(endDate));
                if (target.getCountOfCigarette() < target.getCountOfCigaretteSmoked()) {
                    txvMotivation.setText(R.string.false_target);
                } else {
                    txvMotivation.setText(R.string.progress_goals_are_nearly_completed);
                }
            } else {
                isHaveTarget = false;
                txvUsed.setText("");
                txvStartDate.setText("");
                txvEndDate.setText("");
                txvCurrentTarget.setText("");
                txvCountOfTarget.setText("");
                txvMotivation.setText(R.string.no_target);
            }
        });


        //go to Target screen
        cardViewTarget.setOnClickListener(v -> {
            countOfClick = 0;
            final EditText input = new EditText(HomeActivity.this);
            if (isHaveTarget){
                new AlertDialog.Builder(this)
                        .setTitle("The amount of cigarettes smoked ")
                        .setView(input)
                        .setPositiveButton("Ok", (dialog, whichButton) -> {
                            int count;
                            try {
                                count = Integer.parseInt(input.getText().toString());
                                viewModel.updateTarget(utils.getUserInfo(this).getuID(), target, postId, count, rs -> {
                                    if (rs) {
                                        Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(this, "Are you sure it is numeric data type? ", Toast.LENGTH_SHORT).show();
                                });
                            } catch (Exception i) {
                                Toast.makeText(this, "please input number", Toast.LENGTH_SHORT).show();
                            }

                            // deal with the editable
                        })
                        .setNegativeButton("Cancel", (dialog, whichButton) -> {
                            // Do nothing.
                        }).show();
            }else{
                startActivity(new Intent(this, TargetActivity.class));
            }
        });

        //go to Profile screen
        cardViewProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        //go to Chat screen
//        cardViewChat.setOnClickListener(v -> startActivity(new Intent(this, ChatActivity.class)));
        cardViewChat.setOnClickListener(v -> startActivity(new Intent(this, ChatListActivity.class)));
        //go to Achievement screen
        cardViewAchievement.setOnClickListener(v -> startActivity(new Intent(this, AchievementActivity.class)));

        //back to signin
//        btnBackToSignIn.setOnClickListener(v -> finish());
    }

    // initialization view and more...
    private void init() {
        cardViewAchievement = findViewById(R.id.cv_achievement);
        cardViewChat = findViewById(R.id.cv_chat);
        cardViewProfile = findViewById(R.id.cv_profile);
        cardViewTarget = findViewById(R.id.cv_target);

        //progress
        progressBar = findViewById(R.id.progress_bar_home);
        progressMotivation = findViewById(R.id.progress_bar_motivation);
        //btn
//        btnBackToSignIn = findViewById(R.id.btn_back_to_signin);

        //txv
        txvCountOfTarget = findViewById(R.id.txv_count_of_target);
        txvCurrentTarget = findViewById(R.id.txv_current_target);
        txvEndDate = findViewById(R.id.txv_end_date_h);
        txvStartDate = findViewById(R.id.txv_start_date_h);
        txvUsed = findViewById(R.id.txv_used);
        txvMotivation = findViewById(R.id.txv_motivation);
    }


}