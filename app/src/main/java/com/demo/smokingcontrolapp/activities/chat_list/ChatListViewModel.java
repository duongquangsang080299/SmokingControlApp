package com.demo.smokingcontrolapp.activities.chat_list;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.activities.admin.AdminViewModel;
import com.demo.smokingcontrolapp.firebase.Database;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewModel extends AndroidViewModel {
    private Database database = new Database();
    List<String> listUid = new ArrayList<>();
    ArrayList<String> arrTemp = new ArrayList<>();

    public ChatListViewModel(@NonNull Application application) {
        super(application);
    }

    public void getAllUser(String uid, AdminViewModel.IGetAllUser result){
        database.getAllUserSendMess(uid, uid1 -> {
            listUid = uid1;
            checkDuplicate();
            Log.d("TAG", "getAllUser: " + arrTemp);
            database.getUsers(arrTemp, result::onResult);
        });
    }

    private void checkDuplicate() {
        for (int i = 0; i < listUid.size(); i++) {
            if (!arrTemp.contains(listUid.get(i))) {
                arrTemp.add(listUid.get(i));
            }
        }
    }
}
