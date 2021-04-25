package com.demo.smokingcontrolapp.activities.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.firebase.Database;
import com.demo.smokingcontrolapp.models.Target;
import com.demo.smokingcontrolapp.models.User;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    private final Database database = new Database();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    // get All target
    public void getAllTarget(String uid, IViewModelListener iViewModelListener){
        database.getAllTarget(uid, iViewModelListener::getValueOnComplete);
    }

    //get User profile
    public void getUser(String uid, IGetUser iGetUser){
        database.getUser(uid, iGetUser::onResult);
    }

    public interface IGetUser{
        void onResult(User user);
    }

    public interface IViewModelListener{
        void getValueOnComplete(List<Target> targetList, int complete, int notComplete);
    }
}
