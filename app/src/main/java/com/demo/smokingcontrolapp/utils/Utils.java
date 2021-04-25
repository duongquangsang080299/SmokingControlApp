package com.demo.smokingcontrolapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.smokingcontrolapp.models.UserInfoApp;

public class Utils {
    private SharedPreferences sharedPreferences;


    public void setLogout(boolean isLogout, Context context){
        sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logout", isLogout);
        editor.apply();

    }

    public boolean isLogout(Context context){
        sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logout", false);
    }
    //save info of user: uid, email
    public void saveUserInfo(String email, String password, String uID, Context context) {
        sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("uID", uID);
        editor.apply();
    }

    //Remove user in local
    public void removeUserInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.remove("uID");
    }

    //    Get User in local
    public UserInfoApp getUserInfo(Context context) {
        sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        UserInfoApp user = new UserInfoApp(sharedPreferences.getString("email", ""),
                sharedPreferences.getString("password", ""),
                sharedPreferences.getString("uID", ""));
        return user;
    }


    // convert long to date: flow dateFormat
    public static String convertLongToDate(long value) {
        Date date = new Date(value);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM");
        return dateFormat.format(date);
    }

    // compare between two Date
    public static boolean compareBetweenTwoDate(Date date1, Date date2) {
        return date1.compareTo(date2) < 0;
    }
}
