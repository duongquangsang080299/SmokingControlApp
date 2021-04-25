package com.demo.smokingcontrolapp.activities.admin;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.firebase.Database;
import com.demo.smokingcontrolapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class AdminViewModel extends AndroidViewModel {

    private Database database = new Database();

    public AdminViewModel(@NonNull Application application) {
        super(application);
    }

    List<String> listUid = new ArrayList<>();
    ArrayList<String> arrTemp = new ArrayList<>();

    //get user
    public void getAllUser(String uid, IGetAllUser iGetAllUser) {
        List<User> users = new ArrayList<>();
        database.getAllUserSendMess(uid, id -> {
            listUid = id;
            checkDuplicate();
            database.getUsers(arrTemp, iGetAllUser::onResult);
        });
    }

//    private void getUsers(ArrayList<String> ids, ITemp iTemp){
//        List<User> users = new ArrayList<>();
//
//
//    }

    public interface ITemp {
        void getUsers(List<User> userList);
    }

    public interface IGetAllUser {
        void onResult(List<User> userList);
    }

    private void checkDuplicate() {
        for (int i = 0; i < listUid.size(); i++) {
            if (!arrTemp.contains(listUid.get(i))) {
                arrTemp.add(listUid.get(i));
            }
        }
    }
}
