package com.example.healthhub;

public class Login {
    private String userName;
    private String password;

    public Login() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public boolean blankEmail () {

        if (userName == null)
            return true;
        else
            return false;
    }

    public  boolean blankPassword() {
        if(password == null)
            return true;
        else
            return false;
    }

}
