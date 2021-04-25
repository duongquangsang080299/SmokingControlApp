package com.demo.smokingcontrolapp.activities.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.smokingcontrolapp.firebase.Database;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private Database database = new Database();
    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void searchWithString(String content, ISearchCallBack result){
        database.searchEmailByText(content, result::searchResult);
    }

    public void getUidFromEmail(String email, IGetUid uid){
        database.getUidFromEmail(email, uid::getUid);
    }

    public interface ISearchCallBack{
        void searchResult(List<String> emails);
    }

    public interface IGetUid{
        void getUid(String uid);
    }
}
