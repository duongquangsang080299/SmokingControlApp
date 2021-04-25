package com.demo.smokingcontrolapp.activities.achievement;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.firebase.Database;
import com.demo.smokingcontrolapp.models.Target;

import java.util.List;

public class AchievementViewModel extends AndroidViewModel {
    Database database = new Database();

    public AchievementViewModel(@NonNull Application application) {
        super(application);
    }

    public void getAllAchievement(String uid, IAchievementViewModel iAchievementViewModel){
        database.getAllAchievement(uid, iAchievementViewModel::getAchievementsComplete);
    }

    public interface IAchievementViewModel{
        void getAchievementsComplete(List<Target> targetList);
    }
}
