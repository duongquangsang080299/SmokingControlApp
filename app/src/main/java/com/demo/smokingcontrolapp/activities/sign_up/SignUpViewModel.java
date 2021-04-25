package com.demo.smokingcontrolapp.activities.sign_up;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.activities.home.HomeActivity;
import com.demo.smokingcontrolapp.firebase.Authentication;
import com.demo.smokingcontrolapp.firebase.Database;
import com.demo.smokingcontrolapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpViewModel  extends AndroidViewModel {
    private final Authentication authentication;

    public SignUpViewModel( Application application) {
        super(application);
        authentication = new Authentication();

    }

    // register new account
    public void register(EditText userName, EditText email, EditText password, EditText phoneNumber, ISignUpViewModel viewModel) {
        String t_userName = userName.getText().toString().trim();
        String t_email = email.getText().toString().trim();
        String t_password = password.getText().toString().trim();
        String t_phoneNumber = phoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(t_userName)) {
            userName.setError("User name is required");
            userName.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(t_email)) {
            email.setError("User name is required");
            email.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(t_password)) {
            password.setError("User name is required");
            password.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(t_phoneNumber)) {
            phoneNumber.setError("User name is required");
            phoneNumber.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(t_email).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }
        // create new account with email and pass
        authentication.registerUser(t_email, t_userName, t_password, t_phoneNumber, viewModel::onResult);
    }

    public interface ISignUpViewModel{
        void onResult(Boolean bool, String uId);
    }
}
