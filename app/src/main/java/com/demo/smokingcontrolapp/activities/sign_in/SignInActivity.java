package com.demo.smokingcontrolapp.activities.sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.activities.admin.AdminActivity;
import com.demo.smokingcontrolapp.activities.fogot_password.ForgotPasswordActivity;
import com.demo.smokingcontrolapp.activities.home.HomeActivity;
import com.demo.smokingcontrolapp.activities.main.MainActivity;
import com.demo.smokingcontrolapp.activities.sign_up.SignUpActivity;
import com.demo.smokingcontrolapp.activities.sign_up.SignUpViewModel;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Utils;

public class SignInActivity extends AppCompatActivity {
    private SignInViewModel signInViewModel;
    private EditText edEmail, edPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initFlateView();
        signInViewModel = new SignInViewModel(getApplication());
        if (!utils.isLogout(getApplicationContext())){
            checkLogin();
        }
        event();
    }


    //    chek login
    private void checkLogin() {
        progressBar.setVisibility(View.VISIBLE);
        UserInfoApp infoApp = utils.getUserInfo(this);
        if (!TextUtils.isEmpty(infoApp.getEmail()) && !TextUtils.isEmpty(infoApp.getPassword())) {
            boolean isAdmin = infoApp.getEmail().equals("adm.smokingcontrol@gmail.com") ? true : false;
            signInViewModel.onLogin(infoApp.getEmail(), infoApp.getPassword(), isAdmin, (bool, uID, admin) -> {
                if (bool) {
                    checkPermissionLogin(bool, admin, uID);
                }
            });
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    //    Check permission
    private void checkPermissionLogin(boolean bool, boolean isAdmin, String uID) {
        if (bool) {
            if (isAdmin) {
                Toast.makeText(this, "Admin has been registered successfully!", Toast.LENGTH_LONG).show();
                utils.setLogout(false, getApplicationContext());
                startActivity(new Intent(this, AdminActivity.class));
                finish();
            } else {
                Toast.makeText(this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                utils.setLogout(false, getApplicationContext());
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Field to register! Try again!", Toast.LENGTH_LONG).show();
        }
    }

    // even click
    private void event() {
        btnLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            signInViewModel.signIn(edEmail, edPassword, (bool, uID, isAdmin) -> {
                utils.saveUserInfo(edEmail.getText().toString().trim(), edPassword.getText().toString().trim(), uID, this);
                checkPermissionLogin(bool, isAdmin, uID);
            });
        });
    }

    //init view
    private void initFlateView() {
        utils = new Utils();
        edEmail = findViewById(R.id.etUsername);
        edPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btLogin);
        progressBar = findViewById(R.id.progress_bar);
    }

    //    hint key board on focus
    public void hinKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // go to create new account screen
    public void goToSignUp(View view) {
        this.startActivity(new Intent(this, SignUpActivity.class));
    }

    // go to forgotPass screen
    public void goToForgotPassword(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    // ho to Home screen
    public void goToActivityMain(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}