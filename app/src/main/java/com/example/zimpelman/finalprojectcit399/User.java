package com.example.zimpelman.finalprojectcit399;

/**
 * Created by Christopher on 4/25/2017.
 */

public class User {
    private int userId;
    private String username;
    private String password;

    public User(int userId, String username, String password){
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public  User(String username, String password){
        userId = -1;
        this.username = username;
        this.password = password;
    }

    public User(){
        userId = -1;
        username = "";
        password = "";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
