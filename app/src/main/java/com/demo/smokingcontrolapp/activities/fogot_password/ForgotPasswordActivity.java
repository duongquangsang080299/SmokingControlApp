package com.demo.smokingcontrolapp.activities.fogot_password;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.smokingcontrolapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ForgotPasswordModel forgotPasswordModel;
    private Button btnBack, btnSendMail;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        inFlateView();
        event();
    }

    private void event() {
        btnBack.setOnClickListener(v -> finish());
        btnSendMail.setOnClickListener(v -> forgotPasswordModel.sendMailResetPassword(etEmail, result -> {
            if (result) {
                Toast.makeText(getApplicationContext(), "Password send to your email!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please enter email valid!", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void inFlateView() {
        forgotPasswordModel = new ForgotPasswordModel(getApplication());
        btnBack = findViewById(R.id.btn_back);
        etEmail = findViewById(R.id.etMailSend);
        btnSendMail = findViewById(R.id.btnSendMail);
    }
}