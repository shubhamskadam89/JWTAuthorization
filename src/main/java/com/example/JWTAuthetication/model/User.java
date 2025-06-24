package com.example.JWTAuthetication.model;

public class User {
    private String name;
    private int userID;
    private String emailID;

    public User( String name, int userID,String emailID) {

        this.name = name;
        this.userID = userID;
        this.emailID = emailID;
    }

    public String getName() {
        return name;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userID='" + userID + '\'' +
                ", emailID='" + emailID + '\'' +
                '}';
    }

    public User() {
        super();
    }
}
