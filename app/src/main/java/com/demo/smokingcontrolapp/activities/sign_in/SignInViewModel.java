package com.demo.smokingcontrolapp.activities.sign_in;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.activities.main.MainActivity;
import com.demo.smokingcontrolapp.activities.sign_up.SignUpViewModel;
import com.demo.smokingcontrolapp.firebase.Authentication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

class SignInViewModel extends AndroidViewModel {

    private Authentication authentication;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        authentication = new Authentication();
    }

    // login with email, pass
    public void signIn(EditText edEmail, EditText edPassword, ISignInViewModel viewModel) {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        //        validate password email

        if (TextUtils.isEmpty(email)) {
            edEmail.setError("Email is required");
            edEmail.requestFocus();
            return;
        }
        //        validate password email

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Please enter a valid email!");
            edEmail.requestFocus();
            return;
        }
        //        validate password is empty

        if (TextUtils.isEmpty(password)) {
            edPassword.setError("Email is required");
            edPassword.requestFocus();
            return;
        }
//        validate password length < 6
        if (password.length() < 6) {
            edPassword.setError("Min password length is 6 characters");
            edPassword.requestFocus();
            return;
        }
//        check admin
        if (edEmail.getText().toString().trim().equals("adm.smokingcontrol@gmail.com")) {
            onLogin(email, password, true, viewModel);
        } else {
            onLogin(email, password, false, viewModel);
        }

    }

    //    login with firebase
    public void onLogin(String email, String password, boolean isAdmin, ISignInViewModel viewModel) {
        authentication.loginUser(email, password, (bool, uID) -> viewModel.onResult(bool, uID, isAdmin));
    }

    public interface ISignInViewModel {
        void onResult(Boolean bool, String uID, boolean isAdmin);
    }
}
