package com.demo.smokingcontrolapp.activities.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.firebase.Database;
import com.demo.smokingcontrolapp.models.Target;

import java.util.PropertyPermission;

public class HomeViewModel extends AndroidViewModel {
    Database database = new Database();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void checkTarget(String uid, ICallBack iCallBack) {
        database.checkTargetOfUser(uid, (target, postId) -> {
            Log.d("TAG", "checkTarget: " + target);
            if (target != null) {
                iCallBack.check(true, uid, target, postId);
            } else iCallBack.check(false, uid, target, postId);
        });
    }

    public void updateTarget(String uid, Target target, String postID, int count, IUpdate iUpdate) {
        database.updateTarget(uid, postID, target, count, iUpdate::result);
    }

    public interface IUpdate {
        void result(boolean rs);
    }

    public interface ICallBack {
        void check(boolean result, String uid, Target target, String postId);
    }
}
