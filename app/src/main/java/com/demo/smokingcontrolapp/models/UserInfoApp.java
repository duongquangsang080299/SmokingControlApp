package com.demo.smokingcontrolapp.models;

public class UserInfoApp {
    private  String email;
    private  String password;
    private  String uID;

    public UserInfoApp(String email, String password, String uID) {
        this.email = email;
        this.password = password;
        this.uID = uID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
