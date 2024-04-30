package com.socialmedia.twitter;

public class AltUserResponse {
    private String name;
    private int userID;
    private String email;

    public AltUserResponse(String name, int userID,  String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

