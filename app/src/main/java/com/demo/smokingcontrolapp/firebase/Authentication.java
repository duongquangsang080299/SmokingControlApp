package com.demo.smokingcontrolapp.firebase;

import com.demo.smokingcontrolapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Authentication {

    private FirebaseAuth mAuth;
    private Database database;

    public Authentication() {
        mAuth = FirebaseAuth.getInstance();
        database = new Database();
    }

    // create new account with email and pass
    public void registerUser(String t_email, String t_userName, String t_password, String t_phoneNumber, IFirebaseAuthentication onResult) {
        mAuth.createUserWithEmailAndPassword(t_email, t_password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        database.createNewUser(t_userName, t_email, t_phoneNumber);
                        onResult.onResult(task.isSuccessful(), mAuth.getUid());
                    }
                });
    }

    // login with mail and pass
    public void loginUser(String email, String password, IFirebaseAuthentication authentication) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                task ->
                authentication.onResult(task.isSuccessful(), mAuth.getUid()));
    }

    
    public interface IFirebaseAuthentication {
        void onResult(Boolean bool, String uID);
    }


}
