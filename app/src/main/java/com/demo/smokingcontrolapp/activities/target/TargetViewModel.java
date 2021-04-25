package com.demo.smokingcontrolapp.activities.target;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.demo.smokingcontrolapp.firebase.Database;

public class TargetViewModel extends AndroidViewModel {
    Database database = new Database();

    public TargetViewModel(@NonNull Application application) {
        super(application);
    }

    public void addNewTarget(String uid, String targetName, Long startDate, Long endDate, int count, IViewModelListener iViewModelListener) {
        database.addNewTarget(uid, targetName, startDate, endDate, count, bool -> {
            iViewModelListener.addCompleteListener(true);
        });
    }

    public interface IViewModelListener {
        void addCompleteListener(Boolean bool);
    }
}
