package com.demo.smokingcontrolapp.activities.fogot_password;

import android.app.Application;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.firebase.Database;

public class ForgotPasswordModel extends AndroidViewModel {
    private Database database;
    public ForgotPasswordModel(@NonNull Application application) {
        super(application);
        database = new Database();
    }

    public void sendMailResetPassword(EditText email, ISendMailCallBack callBack) {
        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        database.sendMailRestPass(email.getText().toString().trim(), callBack::onResult);
    }
    public  interface  ISendMailCallBack{
        void onResult(boolean result);
    }
}
