package com.demo.smokingcontrolapp.activities.sign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.activities.home.HomeActivity;
import com.demo.smokingcontrolapp.utils.Utils;

public class SignUpActivity extends AppCompatActivity {
    private ProgressBar progresbar;
    private EditText userName, email, password, phoneNumber;
    private Button btNext;
    private SignUpViewModel signUpViewModel;
    private Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inflateView();
        event();
    }

    //init view
    private void inflateView() {
        signUpViewModel = new SignUpViewModel(getApplication());
        userName = (EditText) findViewById(R.id.etUserName);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        phoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btNext = (Button) findViewById(R.id.btNext);
        progresbar = findViewById(R.id.progress_bar);
    }

    //    event click
    private void event() {
        btNext.setOnClickListener(v -> {
            progresbar.setVisibility(View.VISIBLE);
            signUpViewModel.register(userName, email, password, phoneNumber, (bool, uID) -> {
                if (bool) {
                    progresbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "User has been registered successfully!", Toast.LENGTH_LONG).show();
                    utils.saveUserInfo(email.getText().toString().trim(), password.getText().toString().trim(), uID, getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    this.finish();
                } else {
                    progresbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Field to register! Try again!", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    public void hinKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void finishActivity(View view) {
        this.finish();
    }

}